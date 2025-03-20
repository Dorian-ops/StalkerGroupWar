package me.piglinstudio.stalkergroupwar.Listeners;

import me.piglinstudio.stalkergroupwar.Listeners.Commands.PointSubCommands.Capture;
import me.piglinstudio.stalkergroupwar.Listeners.Commands.PointSubCommands.Create;
import me.piglinstudio.stalkergroupwar.Listeners.Commands.PointSubCommands.Delete;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionService;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionalPlayerService;
import me.piglinstudio.stalkergroupwar.Spring.Service.PointService;
import me.piglinstudio.stalkergroupwar.StalkerGroupWar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] args) {
        String commandName = command.getName();
        switch (commandName) {
            case "fraction":
                return fractionSubCommand(commandSender, args);
            case "point":
                return pointSubCommand(commandSender, args);
        }
        return false;
    }

    private boolean fractionSubCommand(CommandSender commandSender, String[] args) {
        String subCommand = args[0];
        switch (subCommand) {
            case "info":
                return true;
        }
        return false;
    }

    private boolean pointSubCommand(CommandSender commandSender, String[] args) {
        String subCommand = args[0];
        switch (subCommand) {
            case "capture":
                new Capture().execute(args, commandSender);
                return true;
            case "create":
                new Create().execute(args, commandSender);
                return true;
            case "delete":
                new Delete().execute(args, commandSender);
                return true;
        }
        return false;
    }
}
