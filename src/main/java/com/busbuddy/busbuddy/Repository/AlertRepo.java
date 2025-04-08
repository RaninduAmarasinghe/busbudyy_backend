package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.AlertMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertRepo extends MongoRepository<AlertMessage, String> {
    List<AlertMessage> findByCompanyIdOrderByIdDesc(String companyId);
}