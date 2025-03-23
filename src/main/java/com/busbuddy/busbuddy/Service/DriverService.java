
package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Driver;
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

    // Generate a custom ID in the range 1000 - 9999
    private String generateCustomId() {
        Random random = new Random();
        int customId = 1000 + random.nextInt(9000);  // Random 4-digit ID
        return String.valueOf(customId);
    }

    public String createDriver(String name, String email, String phone, String password, String companyId) {
        String customId = generateCustomId();

        // Ensure the ID is unique
        while (driverRepo.existsById(customId)) {
            customId = generateCustomId();
        }

        // Assuming companyName is fetched from somewhere (e.g., database or hardcoded)
        String companyName = "Company XYZ";  // Replace this with actual logic if needed

        Driver driver = new Driver(customId, name, email, phone, password, companyId, companyName);
        Driver savedDriver = driverRepo.save(driver);
        return savedDriver.getDriverId();
    }

    public List<Driver> getDriverByCompany(String companyId) {
        return driverRepo.findByCompanyId(companyId);
    }
}

