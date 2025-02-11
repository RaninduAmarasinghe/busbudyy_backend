package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Driver;
import com.busbuddy.busbuddy.Repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@CrossOrigin
public class DriverController {

    @Autowired
    DriverRepo driverRepo;
@PostMapping("/add")
    public String adddriver(@RequestBody  Driver driver) {
driverRepo.save(driver);
return "Driver added";
    }
}
