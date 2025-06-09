package com.example.carselling.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "booked_car")
public class BookedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created_time;

    private LocalDateTime planed_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public BookedCar() {
    }

    public BookedCar(Long id, LocalDateTime created_time, LocalDateTime planned_time, Car car, User user) {
        this.id = id;
        this.created_time = created_time;
        this.planed_time = planned_time;
        this.car = car;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(LocalDateTime created_time) {
        this.created_time = created_time;
    }

    public LocalDateTime getPlaned_time() {
        return planed_time;
    }

    public void setPlaned_time(LocalDateTime planed_time) {
        this.planed_time = planed_time;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
