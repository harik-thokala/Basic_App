package com.example.ride.service;



import com.example.ride.model.Driver;
import com.example.ride.model.Rider;
import com.example.ride.repository.DriverRepository;
import com.example.ride.repository.RiderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    private final SpatialService spatialService;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;

    public RideService(SpatialService spatialService, DriverRepository driverRepository, RiderRepository riderRepository) {
        this.spatialService = spatialService;
        this.driverRepository = driverRepository;
        this.riderRepository = riderRepository;
    }

    public String requestRide(Long riderId, double lat, double lon, double radius) {
        // 1. Check if Rider exists
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new RuntimeException("Rider not found"));

        // 2. Find nearby driver IDs using the QuadTree (O(log n) instead of O(n))
        List<Long> nearbyDriverIds = spatialService.findNearbyDrivers(lat, lon, radius);

        if (nearbyDriverIds.isEmpty()) {
            return "No drivers found within " + radius + " km.";
        }

        // 3. Assign the first available driver from the list
        for (Long driverId : nearbyDriverIds) {
            Optional<Driver> driverOpt = driverRepository.findById(driverId);
            if (driverOpt.isPresent() && driverOpt.get().getIsAvailable()) {
                Driver driver = driverOpt.get();
                driver.setIsAvailable(false); // Mark driver as busy
                driverRepository.save(driver);
                
                return "Ride confirmed! " + driver.getName() + " is on the way to " + rider.getName();
            }
        }

        return "Drivers are nearby but currently busy.";
    }
}
