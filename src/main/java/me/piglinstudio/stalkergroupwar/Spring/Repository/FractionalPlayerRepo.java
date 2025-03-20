package me.piglinstudio.stalkergroupwar.Spring.Repository;

import me.piglinstudio.stalkergroupwar.Spring.Models.FractionalPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FractionalPlayerRepo extends JpaRepository<FractionalPlayer, Integer> {
    @Override
    Optional<FractionalPlayer> findById(Integer integer);

    @Override
    List<FractionalPlayer> findAll();

    Optional<FractionalPlayer> findByUUID(UUID uuid);

}
