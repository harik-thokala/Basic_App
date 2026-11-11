package com.example.ride.service;



import com.example.ride.dsa.QuadTree;
import com.example.ride.model.Driver;
import com.example.ride.repository.DriverRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpatialService {

    private final DriverRepository driverRepository;
    private final QuadTree quadTree;

    public SpatialService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
        this.quadTree = new QuadTree();
    }

    // This annotation tells Spring to run this method automatically when the app starts
    @PostConstruct
    public void initTree() {
        List<Driver> availableDrivers = driverRepository.findAll();
        for (Driver driver : availableDrivers) {
            if (driver.getIsAvailable() && driver.getLatitude() != null) {
                quadTree.insert(new QuadTree.Point(
                        driver.getLongitude(), 
                        driver.getLatitude(), 
                        driver.getId()
                ));
            }
        }
        System.out.println(">>> QuadTree initialized with " + availableDrivers.size() + " drivers.");
    }

    // Add a new driver location to both DB and Tree
    public void updateDriverLocation(Driver driver) {
        driverRepository.save(driver);
        if (driver.getIsAvailable()) {
            quadTree.insert(new QuadTree.Point(
                    driver.getLongitude(), 
                    driver.getLatitude(), 
                    driver.getId()
            ));
        }
    }

    // Find nearby drivers using the QuadTree
    public List<Long> findNearbyDrivers(double lat, double lon, double radius) {
        return quadTree.findNearby(lon, lat, radius);
    }
}