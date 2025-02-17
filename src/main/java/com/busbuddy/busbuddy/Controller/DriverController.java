package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Driver;
import com.busbuddy.busbuddy.Repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/driver")
@CrossOrigin
public class DriverController {

    @Autowired
    DriverRepo driverRepo;

    // Add Driver
    @PostMapping("/add")
    public String addDriver(@RequestBody Driver driver) {
        driverRepo.save(driver);
        return "Driver added";
    }

    // Driver Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Driver driver) {
        // Use the `driver` parameter instead of the undefined `loginDetails`
        Optional<Driver> driverOptional = driverRepo.findByDriverEmail(driver.getDriverEmail());

        // Check if the driver exists and if the password matches
        if (driverOptional.isPresent() && driverOptional.get().getDriverPassword().equals(driver.getDriverPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}