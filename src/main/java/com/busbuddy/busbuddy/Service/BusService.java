package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Bus;
import com.busbuddy.busbuddy.Repository.BusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BusService {

    @Autowired
    private BusRepo busRepo;

    // Add ID generation logic
    public String generateBusId() {
        Random random = new Random();
        int id = 1000 + random.nextInt(9000);  // 4-digit random number
        return "B" + id;  // Format: B1234
    }

    public List<Bus> getBusesByCompany(String companyId) {
        return busRepo.findByCompanyId(companyId);
    }

    public List<Bus> getBusesByDriver(String driverId) {
        return busRepo.findByDriverId(driverId);
    }

    // Fixed method using proper ID handling
    public Bus getBusById(String busId) {
        Optional<Bus> bus = busRepo.findById(busId);  // Use MongoDB's _id field
        return bus.orElse(null);
    }

    // Add this method for creating buses with generated IDs
    public Bus createBus(Bus bus, String companyId) {
        bus.setBusId(generateBusId());
        bus.setCompanyId(companyId);
        return busRepo.save(bus);
    }
}