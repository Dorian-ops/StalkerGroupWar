package me.piglinstudio.stalkergroupwar.Spring.Repository;

import me.piglinstudio.stalkergroupwar.Spring.Models.Fraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FractionRepo extends JpaRepository<Fraction, Integer> {
    @Override
    Optional<Fraction> findById(Integer integer);

    @Override
    List<Fraction> findAll();
    Optional<Fraction> findByName(String fractionName);
}
