package me.piglinstudio.stalkergroupwar.Spring.Repository;

import me.piglinstudio.stalkergroupwar.Spring.Models.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepo extends JpaRepository<Point, Integer> {
    @Override
    List<Point> findAll();

    @Override
    Optional<Point> findById(Integer integer);
    Optional<Point> findByRegionName(String regionName);
    Optional<Point> findByName(String pointName);

}
