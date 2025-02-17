package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DriverRepo extends MongoRepository<Driver, String> {
    Optional<Driver> findByDriverEmail(String driverEmail);
}
