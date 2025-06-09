package com.example.carselling.repository;

import com.example.carselling.model.BookedCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface BookedCarRepository extends JpaRepository<BookedCar, Long> {
    List<BookedCar> findByUserId(Long userId);
    int countByUserId(Long userId);
    @Query("SELECT b.car.id FROM BookedCar b WHERE b.user.id = :userId")
    List<Long> findCarIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM BookedCar b JOIN FETCH b.user JOIN FETCH b.car")
    List<BookedCar> findAllWithUsersAndCars();
}
