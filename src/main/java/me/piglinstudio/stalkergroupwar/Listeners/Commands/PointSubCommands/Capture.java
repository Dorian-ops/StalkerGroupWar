package me.piglinstudio.stalkergroupwar.Listeners.Commands.PointSubCommands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Regions;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.piglinstudio.stalkergroupwar.Spring.Models.Fraction;
import me.piglinstudio.stalkergroupwar.Spring.Models.FractionalPlayer;
import me.piglinstudio.stalkergroupwar.Spring.Models.Point;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionService;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionalPlayerService;
import me.piglinstudio.stalkergroupwar.Spring.Service.PointService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;

@Component
public class Capture {

    @Autowired
    private PointService pointService;
    @Autowired
    private FractionalPlayerService fractionalPlayerService;
    @Autowired
    private FractionService fractionService;

    public void execute(String[] args, CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Эта команда может быть выполнена только игроками.");
            return;
        }

        Player player = (Player) commandSender;
        Location loc = BukkitAdapter.adapt(player.getLocation());

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);

        FractionalPlayer fractionalPlayer = fractionalPlayerService.getFractionalPlayer(player);
        Fraction fraction = fractionService.getFractionToId(fractionalPlayer.getFractionId());

        if(!canExecuteCommand()){
            return;
        }

        if (fraction.isParticipatesInTheCapture()) {
            player.sendMessage("Ваша фракция уже участвует в захвате!");
            return;
        }

        if (set.getRegions().isEmpty()) {
            player.sendMessage("Нет подходящих регионов для захвата.");
            return;
        }

        for (ProtectedRegion protectedRegion : set.getRegions()) {
            String regionId = protectedRegion.getId();
            Point point = pointService.getPointByRegionName(regionId);

            if (point == null) {
                return;
            }

            Fraction fractionPointOwner = fractionService.getFractionToId(point.getFractionOwner());

            if (!(fractionPointOwner.getId() != fraction.getId() &&
                    !fractionPointOwner.isParticipatesInTheCapture())) {
                player.sendMessage("Точка принадлежит вашей команде! Либо владелец точки воюет за другую точку!");
                return;
            }

            if (fractionService.getToFriendlyFraction(fraction.getId(), fractionPointOwner.getId())) {
                return;
            }

            pointService.startCapture(1200, player, point);
        }
    }

    private static boolean canExecuteCommand() {
        LocalTime now = LocalTime.now();

        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(22, 0);

        if (now.isBefore(start) || now.isAfter(end)) {
            return false;
        }

        int hour = now.getHour();
        int minute = now.getMinute();

        if (minute == 0 || minute > 15) {
            return false;
        }

        return hour % 2 == 0;
    }
}
