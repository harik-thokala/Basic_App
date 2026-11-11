package com.example.ride.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ride.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {

}
