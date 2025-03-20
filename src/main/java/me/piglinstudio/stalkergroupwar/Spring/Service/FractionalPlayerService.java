package me.piglinstudio.stalkergroupwar.Spring.Service;

import me.piglinstudio.stalkergroupwar.Spring.Models.Fraction;
import me.piglinstudio.stalkergroupwar.Spring.Models.FractionalPlayer;
import me.piglinstudio.stalkergroupwar.Spring.Repository.FractionalPlayerRepo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FractionalPlayerService {
    @Autowired
    private FractionalPlayerRepo fractionalPlayerRepo;
    @Autowired
    private FractionService fractionService;

    public void changeFraction(Player player, int fractionId) {
        FractionalPlayer fractionalPlayer = getFractionalPlayer(player);
        fractionalPlayer.setFractionId(fractionId);
    }

    public void changeRank(Player player, String rank) {
        FractionalPlayer fractionalPlayer = getFractionalPlayer(player);
        fractionalPlayer.setRank(rank);
    }

    public void createFractionalPlayer(Player player) {

        if (getFractionalPlayer(player) == null) {
            UUID playerUUID = player.getUniqueId();
            FractionalPlayer fractionalPlayer = new FractionalPlayer(playerUUID, "default");
            fractionalPlayerRepo.save(fractionalPlayer);
        }
    }

    public FractionalPlayer getFractionalPlayer(Player player) {
        UUID playerUUID = player.getUniqueId();
        Optional<FractionalPlayer> OptionalFractionalPlayer = fractionalPlayerRepo.findByUUID(playerUUID);
        return OptionalFractionalPlayer.get();
    }

    public FractionalPlayer getToId(int id) {
        if (fractionalPlayerRepo.findById(id) != null) {
            Optional<FractionalPlayer> fractionalPlayer = fractionalPlayerRepo.findById(id);
            return fractionalPlayer.get();
        }
        return null;
    }

    public Fraction getFraction(FractionalPlayer fractionalPlayer) {
        int fractionId = fractionalPlayer.getFractionId();

        Fraction fraction = fractionService.getFractionToId(fractionId);
        if (fraction != null) {
            return fraction;
        }
        return null;
    }

    public Player getPlayer(FractionalPlayer fractionalPlayer){
        UUID playerUUID = fractionalPlayer.getUuid();
        Player player = Bukkit.getPlayer(playerUUID);
        return player;
    }
}
