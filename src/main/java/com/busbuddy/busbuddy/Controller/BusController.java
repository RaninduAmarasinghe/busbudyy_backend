package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Bus;
import com.busbuddy.busbuddy.Repository.BusRepo;
import com.busbuddy.busbuddy.Service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus")
@CrossOrigin
public class BusController {

    @Autowired

    BusRepo busRepo;


    @PostMapping("/add")

    public String addBus(@RequestBody Bus bus) {
        busRepo.save(bus);
        return "success";
    }

 @Autowired
 BusService busService;
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Bus>>getBusByCompany(@PathVariable String companyId) {
        List<Bus> buses = busService.getBusesByCompany(companyId);
        return ResponseEntity.ok(buses);
    }

}
