package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Admin findByAdminName(String adminName);
}
