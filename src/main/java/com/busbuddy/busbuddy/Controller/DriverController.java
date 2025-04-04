package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Driver;
import com.busbuddy.busbuddy.Repository.DriverRepo;
import com.busbuddy.busbuddy.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/driver")
@CrossOrigin
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    DriverRepo driverRepo;

    @PostMapping("/add")
    public ResponseEntity<String> createDriver(@RequestBody Driver driver, @RequestParam String companyId) {
        if (companyId == null || companyId.isEmpty()) {
            return ResponseEntity.status(400).body("Company ID is required");
        }

        if (driver.getBusId() == null || driver.getBusId().isEmpty()) {
            return ResponseEntity.status(400).body("Bus ID is required");
        }

        // Set company ID to driver
        driver.setCompanyId(companyId);

        // Save driver
        String driverId = driverService.createDriver(
                driver.getDriverName(),
                driver.getDriverEmail(),
                driver.getDriverPhone(),
                driver.getDriverPassword(),
                companyId,
                driver.getBusId()
        );

        return ResponseEntity.ok("Driver Created Successfully: " + driverId);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Driver>> getDriversByCompanyId(@PathVariable String companyId) {
        List<Driver> drivers = driverService.getDriverByCompany(companyId);
        return ResponseEntity.ok(drivers);
    }

    // Driver login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Driver driver) {
        Optional<Driver> driverOptional = driverRepo.findByDriverEmail(driver.getDriverEmail());
        if (driverOptional.isPresent()) {
            Driver dbDriver = driverOptional.get();
            if (dbDriver.getDriverPassword().equals(driver.getDriverPassword())) {
                return ResponseEntity.ok("{\"companyId\":\"" + dbDriver.getCompanyId() +
                        "\", \"companyName\":\"" + dbDriver.getCompanyName() +
                        "\", \"driverName\":\"" + dbDriver.getDriverName() +
                        "\", \"busId\":\"" + dbDriver.getBusId() + "\"}");
            } else {
                return ResponseEntity.status(401).body("Invalid email or password");
            }
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // Update driver
    @PutMapping("/update/{driverId}")
    public ResponseEntity<String> updateDriver(@PathVariable String driverId, @RequestBody Driver updatedDriver) {
        Optional<Driver> existingDriver = driverRepo.findById(driverId);
        if (existingDriver.isPresent()) {
            Driver driver = existingDriver.get();
            driver.setDriverName(updatedDriver.getDriverName());
            driver.setDriverEmail(updatedDriver.getDriverEmail());
            driver.setDriverPhone(updatedDriver.getDriverPhone());
            driver.setDriverPassword(updatedDriver.getDriverPassword());
            driverRepo.save(driver);
            return ResponseEntity.ok("Driver updated successfully");
        } else {
            return ResponseEntity.status(404).body("Driver not found");
        }
    }

    // Delete a driver
    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable String driverId) {
        if (driverRepo.existsById(driverId)) {
            driverRepo.deleteById(driverId);
            return ResponseEntity.ok("Driver deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Driver not found");
        }
    }
}