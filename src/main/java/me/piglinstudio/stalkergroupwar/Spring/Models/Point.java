package me.piglinstudio.stalkergroupwar.Spring.Models;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.piglinstudio.stalkergroupwar.StalkerGroupWar;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.List;

@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String pointName;
    private List<Integer> playerCount;
    private BossBar bossBar;
    private int fractionOwner;
    private int fractionCapture;
    private boolean isCapture;
    private String regionName;
    private int givesInfluence;

    public boolean isCapture() {
        return isCapture;
    }

    public void setCapture(boolean capture) {
        isCapture = capture;
    }

    public Point(String pointName, String regionName, int idFractionOwner) {
        this.pointName = pointName;
        this.regionName = regionName;
        this.isCapture = false;
        this.fractionOwner = idFractionOwner;
        this.bossBar = Bukkit.createBossBar("Захват", BarColor.WHITE, BarStyle.SOLID);
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getGivesInfluence() {
        return givesInfluence;
    }

    public void setGivesInfluence(int givesInfluence) {
        this.givesInfluence = givesInfluence;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getPointName() {
        return pointName;
    }

    public void addPlayerCount(int fractionalPlayerId) {
        playerCount.add(fractionalPlayerId);
    }

    public void removePlayerCount(int fractionalPlayerId) {
        playerCount.remove(fractionalPlayerId);
    }

    public List<Integer> getPlayerCount() {
        return playerCount;
    }

    public int getFractionOwner() {
        return fractionOwner;
    }

    public void setFractionOwner(int fractionOwner) {
        this.fractionOwner = fractionOwner;
    }

    public int getFractionCapture() {
        return fractionCapture;
    }

    public void setFractionCapture(int fractionCapture) {
        this.fractionCapture = fractionCapture;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
    }

}
