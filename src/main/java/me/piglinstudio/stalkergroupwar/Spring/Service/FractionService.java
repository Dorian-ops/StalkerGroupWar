package me.piglinstudio.stalkergroupwar.Spring.Service;

import me.piglinstudio.stalkergroupwar.Spring.Models.Fraction;
import me.piglinstudio.stalkergroupwar.Spring.Models.FractionalPlayer;
import me.piglinstudio.stalkergroupwar.Spring.Repository.FractionRepo;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FractionService {

    @Autowired
    private FractionRepo fractionRepo;
    @Autowired
    private FractionalPlayerService fractionalPlayerService;

    public void mainMethod() {
        createFraction("Freedom");
        createFraction("Dolg");
        createFraction("Monolit");
        createFraction("Military");
        createFraction("Bandits");
        createFraction("Singles");
    }

    public boolean deleteFraction(int fractionId) {
        if (fractionRepo.findById(fractionId).isPresent()) {
            fractionRepo.deleteById(fractionId);
            return true;
        }
        return false;
    }

    public boolean createFraction(String fractionName) {
        Fraction fraction = new Fraction(fractionName);
        if (fractionRepo.findByName(fractionName).isEmpty()) {
            fractionRepo.save(fraction);
            return true;
        }
        return false;
    }

    public Fraction getFractionToId(int id) {
        Optional<Fraction> fraction = fractionRepo.findById(id);
        return fraction.get();
    }

    public boolean getToFriendlyFraction(int fractionId, int fractionCheckId) {
        Fraction fraction = getFractionToId(fractionId);
        List<Integer> fractionsFriendly = fraction.getFractionFriendly();
        return fractionsFriendly.contains(fractionCheckId);
    }

    public boolean addPlayerToFraction(Player player, Fraction fraction) {

        FractionalPlayer fractionalPlayer = fractionalPlayerService.getFractionalPlayer(player);
        List<Integer> idPlayersList = fraction.getPlayersInFraction();

        if (!idPlayersList.contains(fractionalPlayer.getId())) {

            idPlayersList.add(fractionalPlayer.getId());
            fraction.setPlayersInFraction(idPlayersList);

            return true;
        }

        return false;
    }

    public boolean setOwnerFraction(Player player, Fraction fraction){
       int fractionalPlayerId = fraction.getFractionOwner();
       return false;
    }

    public Fraction getByName(String fractionName){
        Optional<Fraction> fraction = fractionRepo.findByName(fractionName);
        return fraction.get();
    }
}
