package com.example.ride.controller;


import com.example.ride.service.RideService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/request")
    public String requestRide(
            @RequestParam Long riderId,
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "5.0") double radius) {
        
        return rideService.requestRide(riderId, lat, lon, radius);
    }
}