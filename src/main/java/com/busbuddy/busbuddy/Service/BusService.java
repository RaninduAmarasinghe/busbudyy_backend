package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Bus;
import com.busbuddy.busbuddy.Model.Location;
import com.busbuddy.busbuddy.Repository.BusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BusService {

    @Autowired
    private BusRepo busRepo;

    // Generate a random 4-digit Bus ID (e.g., B1234)
    public String generateBusId() {
        Random random = new Random();
        int id = 1000 + random.nextInt(9000);
        return "B" + id;
    }

    // Create a new bus and assign it to a company
    public Bus createBus(Bus bus, String companyId) {
        bus.setBusId(generateBusId());
        bus.setCompanyId(companyId);
        return busRepo.save(bus);
    }

    // Retrieve all buses belonging to a company
    public List<Bus> getBusesByCompany(String companyId) {
        return busRepo.findByCompanyId(companyId);
    }

    // Retrieve all buses assigned to a specific driver
    public List<Bus> getBusesByDriver(String driverId) {
        return busRepo.findByDriverId(driverId);
    }

    // Retrieve a single bus by its ID
    public Bus getBusById(String busId) {
        Optional<Bus> bus = busRepo.findById(busId);
        return bus.orElse(null);
    }

    // Update the status of a bus (e.g., "Running", "Stopped")
    public boolean updateBusStatus(String busId, String status) {
        Optional<Bus> busOpt = busRepo.findById(busId);
        if (busOpt.isPresent()) {
            Bus bus = busOpt.get();
            bus.setStatus(status); // Ensure 'status' field exists in the Bus model
            busRepo.save(bus);
            return true;
        }
        return false;
    }

    // Retrieve all active buses (status = "Running" and has valid route info)
    public List<Bus> getActiveBuses() {
        List<Bus> runningBuses = busRepo.findByStatus("Running");
        return runningBuses.stream()
                .filter(bus -> bus.getRoutes() != null && !bus.getRoutes().isEmpty())
                .collect(Collectors.toList());
    }

    // Update the location of a bus (latitude & longitude)
    public boolean updateBusLocation(String busId, Location location) {
        Optional<Bus> busOpt = busRepo.findById(busId);
        if (busOpt.isPresent()) {
            Bus bus = busOpt.get();
            bus.setLocation(location); // Set the updated location
            busRepo.save(bus);  // Save the updated bus document in the database
            System.out.println("Updated bus location: " + bus.getLocation());
            return true;
        }
        return false;
    }
}