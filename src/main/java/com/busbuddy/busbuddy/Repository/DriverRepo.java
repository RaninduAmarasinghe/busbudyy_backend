package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverRepo extends MongoRepository<Driver, String> {
}
