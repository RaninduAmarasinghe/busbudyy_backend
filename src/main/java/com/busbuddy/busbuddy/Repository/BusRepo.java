package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusRepo extends MongoRepository<Bus,String> {
}
