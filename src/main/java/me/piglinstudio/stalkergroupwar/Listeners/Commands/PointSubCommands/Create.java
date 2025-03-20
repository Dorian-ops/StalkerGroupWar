package me.piglinstudio.stalkergroupwar.Listeners.Commands.PointSubCommands;

import me.piglinstudio.stalkergroupwar.Spring.Models.Point;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionService;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionalPlayerService;
import me.piglinstudio.stalkergroupwar.Spring.Service.PointService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Create {
    @Autowired
    private PointService pointService;

    public void execute(String[] args, CommandSender commandSender) {

        if (!commandSender.isOp()){
            return;
        }

        if (args.length < 3) {
            return;
        }

        String pointName = args[1];
        String regionName = args[2];
        int fractionId = Integer.parseInt(args[3]);

        Point pointGetter = pointService.getPointByName(pointName);

        if (pointGetter != null) {
            return;
        }

        if (!(commandSender instanceof Player)) {
            return;
        }
        Player player = ((Player) commandSender).getPlayer();

        Boolean isCreate = pointService.createPoint(pointName, regionName, fractionId, player);

        if (isCreate) {
            player.sendMessage("Точка успшено создана! " + pointName);
        } else {
            player.sendMessage("Не удалось создать точку! " + pointName);
        }

    }
}