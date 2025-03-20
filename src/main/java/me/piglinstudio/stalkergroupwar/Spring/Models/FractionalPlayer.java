package me.piglinstudio.stalkergroupwar.Spring.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class FractionalPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private UUID uuid;
    private int fractionId;
    private String rank;

    public FractionalPlayer(UUID uuid, String rank) {
        this.uuid = uuid;
        this.rank = rank;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getFractionId() {
        return fractionId;
    }

    public void setFractionId(int fractionId) {
        this.fractionId = fractionId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }
}
