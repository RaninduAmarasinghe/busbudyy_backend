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
    private BusService busService;

    // Add a new bus
    @PostMapping("/add")
    public ResponseEntity<String> addBus(@RequestBody Bus bus, @RequestParam String companyId) {
        busService.createBus(bus, companyId);
        return ResponseEntity.ok("Bus added successfully");
    }

    // Get buses by company ID
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Bus>> getBusByCompany(@PathVariable String companyId) {
        List<Bus> buses = busService.getBusesByCompany(companyId);
        return ResponseEntity.ok(buses);
    }

    // Get buses by driver ID
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Bus>> getBusesByDriver(@PathVariable String driverId) {
        List<Bus> buses = busService.getBusesByDriver(driverId);
        return ResponseEntity.ok(buses);
    }

    // Get full bus details by bus ID
    @GetMapping("/details/{busId}")
    public ResponseEntity<Bus> getBusDetails(@PathVariable String busId) {
        Bus bus = busService.getBusById(busId);
        return bus != null ? ResponseEntity.ok(bus) : ResponseEntity.notFound().build();
    }

    // Start a trip (set status = "Running")
    @PostMapping("/startTrip/{busId}")
    public ResponseEntity<String> startTrip(@PathVariable String busId) {
        boolean updated = busService.updateBusStatus(busId, "Running");
        return updated
                ? ResponseEntity.ok("Trip started")
                : ResponseEntity.status(404).body("Bus not found");
    }

    // Stop a trip (set status = "Stopped")
    @PostMapping("/stopTrip/{busId}")
    public ResponseEntity<String> stopTrip(@PathVariable String busId) {
        boolean updated = busService.updateBusStatus(busId, "Stopped");
        return updated
                ? ResponseEntity.ok("Trip stopped")
                : ResponseEntity.status(404).body("Bus not found");
    }

    // Get all active buses (status = "Running" and must have routes)
    @GetMapping("/active")
    public ResponseEntity<List<Bus>> getActiveBuses() {
        List<Bus> activeBuses = busService.getActiveBuses();
        return ResponseEntity.ok(activeBuses);
    }
}