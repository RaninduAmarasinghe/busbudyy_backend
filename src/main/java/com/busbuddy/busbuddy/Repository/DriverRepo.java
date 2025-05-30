package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepo extends MongoRepository<Driver, String> {
    Optional<Driver> findByDriverEmail(String driverEmail);
    List<Driver> findByCompanyId(String companyId);
    List<Driver> findByDriverIdContainingIgnoreCaseOrDriverNameContainingIgnoreCase(String driverId, String driverName);
}
