package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BusRepo extends MongoRepository<Bus,String> {
List<Bus> findByCompanyId(String companyId);
    List<Bus> findByDriverId(String driverId);
}
