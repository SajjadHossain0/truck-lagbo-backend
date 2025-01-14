package com.truck_lagbo_backend.Drivers.Services;

import com.truck_lagbo_backend.Authentication.Entities.User;
import com.truck_lagbo_backend.Authentication.Repositories.UserRepo;
import com.truck_lagbo_backend.Drivers.Entities.Driver;
import com.truck_lagbo_backend.Drivers.Repositories.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepo driverRepo;
    private final UserRepo userRepo;

    public DriverService(DriverRepo driverRepo, UserRepo userRepo) {
        this.driverRepo = driverRepo;
        this.userRepo = userRepo;
    }

    public Driver registerDriver(Long userId ,Driver driver) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!user.getRole().equals("USER")) {
            throw new RuntimeException("User Role Not Found");
        }
        user.setRole("DRIVER");
        userRepo.save(user);
        driver.setUser(user);
        return driverRepo.save(driver);
    }

    public List<Driver> searchDrivers(String truckType, String capacity, String location, String price) {
        return driverRepo.searchDrivers(truckType, capacity, location, price);
    }
}
