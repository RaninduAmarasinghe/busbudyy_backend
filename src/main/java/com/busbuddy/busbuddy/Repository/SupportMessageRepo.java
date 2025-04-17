package com.busbuddy.busbuddy.Repository;

import com.busbuddy.busbuddy.Model.SupportMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupportMessageRepo extends MongoRepository<SupportMessage, String> {
}