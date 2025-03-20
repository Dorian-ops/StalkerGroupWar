package me.piglinstudio.stalkergroupwar.Listeners;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeaveEvent;
import me.piglinstudio.stalkergroupwar.Listeners.Commands.FractionalPlayerSubCommand.SelectFraction;
import me.piglinstudio.stalkergroupwar.Spring.Models.Fraction;
import me.piglinstudio.stalkergroupwar.Spring.Models.FractionalPlayer;
import me.piglinstudio.stalkergroupwar.Spring.Models.Point;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionService;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionalPlayerService;
import me.piglinstudio.stalkergroupwar.Spring.Service.PointService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListener implements Listener {

    @Autowired
    private FractionService fractionService;
    @Autowired
    private FractionalPlayerService fractionalPlayerService;
    @Autowired
    private PointService pointService;

    @EventHandler
    public void pointEnter(RegionEnteredEvent event) {
        ProtectedRegion protectedRegion = event.getRegion();
        Point point = pointService.getPointByRegionName(protectedRegion.getId());

        Player player = event.getPlayer();
        FractionalPlayer fractionalPlayer = fractionalPlayerService.getFractionalPlayer(player);

        if (fractionalPlayer == null) {
            return;
        }

        if (point == null) {
            return;
        }

        if (!point.isCapture()) {
            return;
        }

        if (fractionalPlayer.getFractionId() == point.getFractionOwner() ||
                fractionalPlayer.getFractionId() == point.getFractionCapture()) {
            point.addPlayerCount(fractionalPlayer.getId());
        }
    }

    @EventHandler
    public void pointLeave(RegionLeaveEvent event) {
        ProtectedRegion protectedRegion = event.getRegion();
        Point point = pointService.getPointByRegionName(protectedRegion.getId());

        Player player = event.getPlayer();
        FractionalPlayer fractionalPlayer = fractionalPlayerService.getFractionalPlayer(player);

        if (fractionalPlayer == null) {
            return;
        }

        if (point == null) {
            return;
        }

        point.removePlayerCount(fractionalPlayer.getId());
    }

    @EventHandler
    public void createFractionalPlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (fractionalPlayerService.getFractionalPlayer(player) == null) {
            fractionalPlayerService.createFractionalPlayer(player);
            new SelectFraction().execute(player);
        }
    }

    @EventHandler
    public void getFractionSelectedPlayer(InventoryClickEvent event) {

        Player player = event.getWhoClicked().getKiller();

        if (event.getView().getTitle() == "Выбор Фрации: " + player.getName()) {
            Fraction fraction = null;
            switch (event.getRawSlot()) {
                case 20:
                    fraction = fractionService.getByName("Bandits");
                    break;
                case 22:
                    fraction = fractionService.getByName("Dolg");
                    break;
                case 24:
                    fraction = fractionService.getByName("Freedom");
                    break;
                case 30:
                    fraction = fractionService.getByName("Military");
                    break;
                case 32:
                    fraction = fractionService.getByName("Monolit");
                    break;
                case 40:
                    fraction = fractionService.getByName("Singles");
                    break;
            }
            fractionService.addPlayerToFraction(player, fraction);
        }
    }

}
