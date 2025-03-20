package me.piglinstudio.stalkergroupwar;

import me.piglinstudio.stalkergroupwar.Listeners.CommandListener;
import me.piglinstudio.stalkergroupwar.Listeners.EventListener;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionService;
import me.piglinstudio.stalkergroupwar.Spring.Service.FractionalPlayerService;
import me.piglinstudio.stalkergroupwar.Spring.Service.PointService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.util.logging.Logger;
public class StalkerGroupWar extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = Bukkit.getServer().getPluginManager().getPlugin("StalkerGroupWar");

        getCommand("fraction").setExecutor(new  CommandListener());
        getCommand("point").setExecutor(new  CommandListener());

        getServer().getPluginManager().registerEvents(new EventListener(), this);

        displayAuthorName();
    }

    private static void displayAuthorName() {
        System.out.println(
                "\n" +
                        "██████╗░░█████╗░██████╗░██╗░█████╗░███╗░░██╗\n" +
                        "██╔══██╗██╔══██╗██╔══██╗██║██╔══██╗████╗░██║\n" +
                        "██║░░██║██║░░██║██████╔╝██║███████║██╔██╗██║\n" +
                        "██║░░██║██║░░██║██╔══██╗██║██╔══██║██║╚████║\n" +
                        "██████╔╝╚█████╔╝██║░░██║██║██║░░██║██║░╚███║\n" +
                        "╚═════╝░░╚════╝░╚═╝░░╚═╝╚═╝╚═╝░░╚═╝╚═╝░░╚══╝");
        System.out.println("https://discord.gg/wyRpJErbZ7 My discord!");
    }

    @Override
    public void onDisable() {

    }
}
