package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Bus;
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
    private BusService busService;  // Use service instead of direct repo access

    @PostMapping("/add")
    public ResponseEntity<String> addBus(@RequestBody Bus bus,
                                         @RequestParam String companyId) {
        // Delegate to service for ID generation and saving
        busService.createBus(bus, companyId);
        return ResponseEntity.ok("success"); // 👈 Return plain 'success'
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Bus>> getBusByCompany(@PathVariable String companyId) {
        List<Bus> buses = busService.getBusesByCompany(companyId);
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/driver/{driverId}")  // Fixed endpoint path
    public ResponseEntity<List<Bus>> getBusesByDriver(@PathVariable String driverId) {
        List<Bus> buses = busService.getBusesByDriver(driverId);
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/details/{busId}")
    public ResponseEntity<Bus> getBusDetails(@PathVariable String busId) {
        Bus bus = busService.getBusById(busId);
        return bus != null ? ResponseEntity.ok(bus) : ResponseEntity.notFound().build();
    }
}