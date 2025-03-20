package me.piglinstudio.stalkergroupwar.Spring.Service;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.piglinstudio.stalkergroupwar.Spring.Models.FractionalPlayer;
import me.piglinstudio.stalkergroupwar.Spring.Models.Point;
import me.piglinstudio.stalkergroupwar.Spring.Repository.PointRepo;
import me.piglinstudio.stalkergroupwar.StalkerGroupWar;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PointService {
    @Autowired
    private PointRepo pointRepo;
    @Autowired
    private FractionalPlayerService fractionalPlayerService;

    public Boolean createPoint(String pointName, String regionName, int fractionId, Player player) {

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        WorldEditPlugin wePlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");

        com.sk89q.worldedit.entity.Player Player = wePlugin.wrapPlayer(player);
        com.sk89q.worldedit.world.World weWorld = Player.getWorld();

        RegionManager regionManager = container.get(weWorld);
        Map<String, ProtectedRegion> regions = regionManager.getRegions();

        if (!regions.containsKey(regionName)) {
            return false;
        }

        if (getPointByRegionName(regionName) != null) {
            return false;
        }

        Point point = new Point(pointName, regionName, fractionId);

        if (pointRepo.findByName(pointName).isEmpty()) {
            pointRepo.save(point);
            return true;
        }

        return false;
    }

    public Point getPointByRegionName(String regionName) {
        Optional<Point> point = pointRepo.findByRegionName(regionName);
        return point.get();
    }

    public Point getPointByName(String pointName) {
        Optional<Point> point = pointRepo.findByName(pointName);
        return point.get();
    }

    public Boolean deletePoint(String pointName) {
        Point point = getPointByName(pointName);

        if (point != null) {
            pointRepo.delete(point);
            return true;
        }

        return false;
    }

    public void startCapture(int timer, Player player, Point point) {
        if (!point.isCapture()) {
            String message = "Игрок" + player.getName() + "начал захват точки name:" + point.getPointName()
                    + ". Предположительная локация" + player.getLocation();
            Bukkit.broadcastMessage(message);
            point.setCapture(true);
            new BukkitRunnable() {
                private int timeLeft = timer;

                @Override
                public void run() {
                    if (timeLeft > 0) {
                        timeLeft--;
                        displayBossBar(point, timeLeft, timer);
                    } else {
                        getWinner(point);
                        this.cancel();
                    }
                }
            }.runTaskTimer(new StalkerGroupWar().plugin, 0, 20);
        }
    }

    private void displayBossBar(Point point, int timeLeft, int timer) {
        List<Integer> playersId = point.getPlayerCount();
        float progress = (float) timeLeft / timer;

        BossBar bossBar = point.getBossBar();
        bossBar.setProgress(progress);

        for (int i = 0; i < playersId.size(); i++) {
            int id = playersId.get(i);
            FractionalPlayer fractionalPlayer = fractionalPlayerService.getToId(id);
            Player player = Bukkit.getPlayer(fractionalPlayer.getUuid());
            bossBar.addPlayer(player);
        }
    }

    private void getWinner(Point point) {
        int fractionCapture = point.getFractionCapture();
        int fractionOwner = point.getFractionOwner();

        List<Integer> playersId = point.getPlayerCount();

        List<FractionalPlayer> fractionalPlayersOwner = new ArrayList<>();
        List<FractionalPlayer> fractionalPlayersCapture = new ArrayList<>();

        if (playersId.isEmpty()) {
            return;
        }

        for (int i = 0; i < playersId.size(); i++) {
            int id = playersId.get(i);
            FractionalPlayer fractionalPlayer = fractionalPlayerService.getToId(id);

            if (fractionalPlayer.getFractionId() == fractionOwner) {
                fractionalPlayersOwner.add(fractionalPlayer);
            } else if (fractionalPlayer.getFractionId() == fractionCapture) {
                fractionalPlayersCapture.add(fractionalPlayer);
            }
        }


        if (fractionalPlayersOwner.size() > fractionalPlayersCapture.size()) {
            point.setFractionOwner(fractionOwner);
            Bukkit.broadcastMessage("Точка: " + point.getPointName() + " получила статус защищёной!");

            for (int i = 0; i < fractionalPlayersOwner.size(); i++) {
                FractionalPlayer fractionalPlayer = fractionalPlayersOwner.get(i);
                Player player = fractionalPlayerService.getPlayer(fractionalPlayer);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + player.getName() + " stalker_two_heart_of_minecraft:onehundredrub");
            }

        } else if (fractionalPlayersOwner.size() < fractionalPlayersCapture.size()) {
            point.setFractionOwner(fractionCapture);
            Bukkit.broadcastMessage("Точка: " + point.getPointName() + " получила статус захваченой!");

            for (int i = 0; i < fractionalPlayersCapture.size(); i++) {
                FractionalPlayer fractionalPlayer = fractionalPlayersCapture.get(i);
                Player player = fractionalPlayerService.getPlayer(fractionalPlayer);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + player.getName() + " stalker_two_heart_of_minecraft:onehundredrub");
            }

        } else if (fractionalPlayersOwner.size() == fractionalPlayersCapture.size()) {
            Bukkit.broadcastMessage("Точка: " + point.getPointName() + " получила статус ничьи!");
        }


        BossBar bossBar = point.getBossBar();
        bossBar.removeAll();

    }
}
