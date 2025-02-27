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

    @PostMapping("/add")
    public String createDriver(@RequestBody Driver driver) {
        return driverService.createDriver(
                driver.getDriverName(),
                driver.getDriverEmail(),
                driver.getDriverPhone(),
                driver.getDriverPassword(),
                driver.getCompanyId()
        );
    }


    @GetMapping("company/{companyId}")
    public ResponseEntity<List<Driver>> getDriversByCompanyId(@PathVariable String companyId) {
        List<Driver> drivers = driverService.getDriverByCompany(companyId);
        return ResponseEntity.ok(drivers);
    }


    @Autowired
    DriverRepo driverRepo;

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