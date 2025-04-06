package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BusRepo extends MongoRepository<Bus, String> {
    List<Bus> findByCompanyId(String companyId);
    List<Bus> findByDriverId(String driverId);
    Bus findByBusId(String busId);
    List<Bus> findByBusId(String busId, Sort sort);
    List<Bus> findByStatus(String status); // Add this to support /bus/active


}