package com.truck_lagbo_backend.Drivers.Controllers;

import com.truck_lagbo_backend.Authentication.Entities.User;
import com.truck_lagbo_backend.Authentication.Repositories.UserRepo;
import com.truck_lagbo_backend.Drivers.Entities.Driver;
import com.truck_lagbo_backend.Drivers.Repositories.DriverRepo;
import com.truck_lagbo_backend.Drivers.Services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/driver")
@CrossOrigin
public class DriversController {
    @Autowired
    private DriverService driverService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DriverRepo driverRepo;

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverRepo.findAll();
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return driverRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register/{userId}")
    public ResponseEntity<?> registerUserAsDriver(@PathVariable Long userId,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("number") String number,
                                                  @RequestParam("photo") MultipartFile photo,
                                                  @RequestParam("vehicaltype") List<String> vehicaltype,
                                                  @RequestParam("weight") String weight,
                                                  @RequestParam("size") String size,
                                                  @RequestParam("registrationnumber") String registrationnumber,
                                                  @RequestParam("servicearea") List<String> servicearea,
                                                  @RequestParam("price") String price) {
        try {
            // Convert MultipartFile photo to byte array
            byte[] photoBytes = photo.isEmpty() ? null : photo.getBytes();

            // Create a new Driver object
            Driver driver = new Driver();
            driver.setName(name);
            driver.setNumber(number);
            driver.setPhoto(photoBytes);
            driver.setVehicaltype(vehicaltype);
            driver.setWeight(weight);
            driver.setSize(size);
            driver.setRegistrationnumber(registrationnumber);
            driver.setServicearea(servicearea);
            driver.setPrice(price);
            driver.setActive(false);

            // Register the driver via service
            Driver registeredDriver = driverService.registerDriver(userId, driver);
            return ResponseEntity.ok(registeredDriver);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing the photo file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDrivers(
            @RequestParam(value = "truckType", required = false) String truckType,
            @RequestParam(value = "capacity", required = false) String capacity,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "price", required = false) String price) {
        try {
            List<Driver> drivers = driverService.searchDrivers(truckType, capacity, location, price);
            return ResponseEntity.ok(drivers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
