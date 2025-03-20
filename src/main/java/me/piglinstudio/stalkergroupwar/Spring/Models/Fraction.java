package me.piglinstudio.stalkergroupwar.Spring.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Fraction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fractionName;
    private int fractionOwner;
    private int influence;
    private boolean ParticipatesInTheCapture;
    private List<Integer> playersInFraction;
    private List<Integer> pointsInFraction;
    private List<Integer> fractionFriendly;

    public Fraction(String fractionName) {
        this.fractionName = fractionName;
        ParticipatesInTheCapture = false;
    }

    public String getFractionName() {
        return fractionName;
    }

    public int getInfluence() {
        return influence;
    }

    public void setInfluence(int influence) {
        this.influence = influence;
    }

    public List<Integer> getPlayersInFraction() {
        return playersInFraction;
    }

    public void setPlayersInFraction(List<Integer> playersInFraction) {
        this.playersInFraction = playersInFraction;
    }

    public List<Integer> getPointsInFraction() {
        return pointsInFraction;
    }

    public void setPointsInFraction(List<Integer> pointsInFraction) {
        this.pointsInFraction = pointsInFraction;
    }

    public boolean isParticipatesInTheCapture() {
        return ParticipatesInTheCapture;
    }

    public void setParticipatesInTheCapture(boolean participatesInTheCapture) {
        ParticipatesInTheCapture = participatesInTheCapture;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getFractionFriendly() {
        return fractionFriendly;
    }

    public void setFractionFriendly(List<Integer> fractionFriendly) {
        this.fractionFriendly = fractionFriendly;
    }

    public int getFractionOwner() {
        return fractionOwner;
    }

    public void setFractionOwner(int fractionOwner) {
        this.fractionOwner = fractionOwner;
    }
}
