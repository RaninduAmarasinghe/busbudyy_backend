package com.busbuddy.busbuddy.websocket;

import com.busbuddy.busbuddy.Model.AlertMessage;
import com.busbuddy.busbuddy.Repository.AlertRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@CrossOrigin
public class AlertWebSocketController {

    @Autowired
    private AlertRepo alertRepo;

    @MessageMapping("/alert")
    @SendTo("/topic/alerts")
    public AlertMessage handleAlert(AlertMessage alert) {
        if (alert.getType() == null || alert.getType().isEmpty()) {
            alert.setType("Unknown");
        }

        // Save and return the saved object which contains the MongoDB-generated ID
        AlertMessage saved = alertRepo.save(alert);

        // Debug log to verify the ID
        System.out.printf("✅ Alert saved with ID: %s, Type: %s, Sender: %s%n",
                saved.getId(), saved.getType(), saved.getSenderName());

        return saved;
    }
    @GetMapping("/company/{companyId}")
    public List<AlertMessage> getCompanyAlerts(@PathVariable String companyId) {
        return alertRepo.findByCompanyIdOrderByIdDesc(companyId);
    }
}