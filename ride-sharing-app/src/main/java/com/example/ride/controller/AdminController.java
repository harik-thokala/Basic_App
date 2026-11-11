package com.example.ride.controller;



import com.example.ride.model.Driver;
import com.example.ride.model.Rider;
import com.example.ride.repository.DriverRepository;
import com.example.ride.repository.RiderRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final DriverRepository driverRepo;
    private final RiderRepository riderRepo;

    public AdminController(DriverRepository driverRepo, RiderRepository riderRepo) {
        this.driverRepo = driverRepo;
        this.riderRepo = riderRepo;
    }

    @PostMapping("/driver")
    public Driver createDriver(@RequestBody Driver driver) {
        return driverRepo.save(driver);
    }

    @PostMapping("/rider")
    public Rider createRider(@RequestBody Rider rider) {
        return riderRepo.save(rider);
    }
}