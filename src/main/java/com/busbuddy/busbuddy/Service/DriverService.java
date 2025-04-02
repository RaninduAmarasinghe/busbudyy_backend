
package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Bus;
import com.busbuddy.busbuddy.Model.Driver;
import com.busbuddy.busbuddy.Repository.BusRepo;
import com.busbuddy.busbuddy.Repository.CompanyRepo;
import com.busbuddy.busbuddy.Repository.DriverRepo;
import com.busbuddy.busbuddy.Repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DriverService {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private CompanyRepo companyRepo;

    // Generate a custom ID in the range 1000 - 9999
    private String generateCustomId() {
        Random random = new Random();
        int customId = 1000 + random.nextInt(9000);  // Random 4-digit ID
        return String.valueOf(customId);
    }

    public String createDriver(String name, String email, String phone, String password, String companyId, String busNumber) {
        String customId = generateCustomId();

        // Ensure the ID is unique
        while (driverRepo.existsById(customId)) {
            customId = generateCustomId();
        }

       // Fetch company name
        String companyName = companyRepo.findById(companyId)
                .map(company -> company.getCompanyName())
                .orElse("Unknown Company");

        // Fetch bus by busNumber
        Bus bus = busRepo.findById(busNumber).orElse(null);
        if (bus != null) {
            busRepo.save(bus); // Save bus details (if additional properties needed)
        }//

        Driver driver = new Driver(customId, name, email, phone, password, companyId, companyName, busNumber);
        Driver savedDriver = driverRepo.save(driver);
        return savedDriver.getDriverId();
    }
    public List<Driver> getDriverByCompany(String companyId) {
        return driverRepo.findByCompanyId(companyId);
    }

}

