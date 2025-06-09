package com.example.carselling.repository;

import com.example.carselling.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE " +
            "(:fromYear IS NULL OR c.year >= :fromYear) AND " +
            "(:toYear IS NULL OR c.year <= :toYear) AND " +
            "(:minPrice IS NULL OR c.coast >= :minPrice) AND " +
            "(:maxPrice IS NULL OR c.coast <= :maxPrice) AND " +
            "(:brand IS NULL OR LOWER(c.brand) LIKE LOWER(concat('%', cast(:brand as string), '%'))) AND " +
            "(:model IS NULL OR LOWER(c.model) LIKE LOWER(concat('%', cast(:model as string), '%'))) AND " +
            "(:color IS NULL OR LOWER(c.color) LIKE LOWER(concat('%', cast(:color as string), '%')))")
    List<Car> searchCars(
            @Param("fromYear") Short fromYear,
            @Param("toYear") Short toYear,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("color") String color
    );
}
