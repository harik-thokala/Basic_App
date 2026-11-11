package com.example.ride.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ride.model.Rider;

public interface RiderRepository extends JpaRepository<Rider, Long>{

}
