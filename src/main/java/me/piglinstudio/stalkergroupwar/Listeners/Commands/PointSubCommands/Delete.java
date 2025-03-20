package me.piglinstudio.stalkergroupwar.Listeners.Commands.PointSubCommands;

import me.piglinstudio.stalkergroupwar.Spring.Models.Point;
import me.piglinstudio.stalkergroupwar.Spring.Service.PointService;
import org.bukkit.command.CommandSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Delete {
    @Autowired
    private PointService pointService;

    public void execute(String[] args, CommandSender commandSender) {

        if (!commandSender.isOp()){
            return;
        }

        if (args.length < 1) {
            return;
        }

        String pointName = args[1];
        Point point = pointService.getPointByName(pointName);

        if(point == null){
            return;
        }

        Boolean isDelete = pointService.deletePoint(pointName);
        if(isDelete){
            commandSender.sendMessage("Вы успешно удалили точку! " + pointName);
        } else {
            commandSender.sendMessage("Произошла ошибка при удалении! " + pointName);
        }

    }
}
