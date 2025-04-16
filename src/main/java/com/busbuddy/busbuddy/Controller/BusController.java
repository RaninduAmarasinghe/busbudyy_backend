package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Bus;
import com.busbuddy.busbuddy.Model.Location; // Import the Location class
import com.busbuddy.busbuddy.Model.Route;
import com.busbuddy.busbuddy.Service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // Update bus
    @PutMapping("/update/{busId}")
    public ResponseEntity<String> updateBus(@PathVariable String busId, @RequestBody Bus updatedBus) {
        boolean updated = busService.updateBus(busId, updatedBus);
        return updated ? ResponseEntity.ok("Bus updated successfully") :
                ResponseEntity.status(404).body("Bus not found");
    }

    // Delete bus
    @DeleteMapping("/delete/{busId}")
    public ResponseEntity<String> deleteBus(@PathVariable String busId) {
        boolean deleted = busService.deleteBus(busId);
        return deleted ? ResponseEntity.ok("Bus deleted successfully") :
                ResponseEntity.status(404).body("Bus not found");
    }

    // Get buses Using company ID
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Bus>> getBusByCompany(@PathVariable String companyId) {
        return ResponseEntity.ok(busService.getBusesByCompany(companyId));
    }

    // Get buses Using driver ID
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Bus>> getBusesByDriver(@PathVariable String driverId) {
        return ResponseEntity.ok(busService.getBusesByDriver(driverId));
    }

    // Get full bus details Using Bus ID
    @GetMapping("/details/{busId}")
    public ResponseEntity<Bus> getBusDetails(@PathVariable String busId) {
        Bus bus = busService.getBusById(busId);
        return bus != null ? ResponseEntity.ok(bus) : ResponseEntity.notFound().build();
    }

    // Start a trip
    @PostMapping("/startTrip/{busId}")
    public ResponseEntity<String> startTrip(@PathVariable String busId) {
        boolean updated = busService.updateBusStatus(busId, "Running");
        return updated ? ResponseEntity.ok("Trip started") :
                ResponseEntity.status(404).body("Bus not found");
    }

    // Stop a trip
    @PostMapping("/stopTrip/{busId}")
    public ResponseEntity<String> stopTrip(@PathVariable String busId) {
        boolean updated = busService.updateBusStatus(busId, "Stopped");
        return updated ? ResponseEntity.ok("Trip stopped") :
                ResponseEntity.status(404).body("Bus not found");
    }

    // Update location of the bus
    @PostMapping("/update-location/{busId}")
    public ResponseEntity<String> updateLocation(@PathVariable String busId,
                                                 @RequestBody Location location) {
        boolean success = busService.updateBusLocation(busId, location);
        return success ? ResponseEntity.ok("Location updated") :
                ResponseEntity.status(404).body("Bus not found");
    }

    // Get all active buses
    @GetMapping("/active")
    public ResponseEntity<List<Bus>> getActiveBuses() {
        return ResponseEntity.ok(busService.getActiveBuses());
    }


    @GetMapping("/schedules")
    public ResponseEntity<List<Map<String, Object>>> getBusSchedules() {
        List<Bus> buses = busService.getAllBuses(); // add method in service if needed

        List<Map<String, Object>> scheduleList = new ArrayList<>();

        for (Bus bus : buses) {
            if (bus.getRoutes() != null) {
                for (Route route : bus.getRoutes()) {
                    Map<String, Object> schedule = new HashMap<>();
                    schedule.put("busNumber", bus.getBusNumber());
                    schedule.put("routeNumber", route.getRouteNumber());
                    schedule.put("startPoint", route.getStartPoint());
                    schedule.put("endPoint", route.getEndPoint());
                    schedule.put("departureTime", route.getDepartureTimes().isEmpty() ? "N/A" : route.getDepartureTimes().get(0));
                    schedule.put("arrivalTime", route.getArrivalTimes().isEmpty() ? "N/A" : route.getArrivalTimes().get(0));
                    scheduleList.add(schedule);
                }
            }
        }

        return ResponseEntity.ok(scheduleList);
    }

}
