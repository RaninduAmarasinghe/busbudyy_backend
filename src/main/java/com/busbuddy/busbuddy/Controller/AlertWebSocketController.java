package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.AlertMessage;
import com.busbuddy.busbuddy.Repository.AlertRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@CrossOrigin
public class AlertWebSocketController {

    @Autowired
    private AlertRepo alertRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Handles passenger alerts (e.g., missing items, complaints)
    @MessageMapping("/alert")
    public void handlePassengerAlert(AlertMessage alert) {
        if (alert.getType() == null || alert.getType().isEmpty()) {
            alert.setType("Unknown");
        }

        AlertMessage saved = alertRepo.save(alert);

        String busTopic = "/topic/alerts/" + saved.getBusId();
        String companyTopic = "/topic/messages/" + saved.getCompanyId();

        System.out.printf(
                "\uD83D\uDCE8 Passenger Alert:\n → Bus: %s\n → Company: %s\n ▶ Type: %s | From: %s | Message: %s%n",
                busTopic, companyTopic, saved.getType(), saved.getSenderName(), saved.getMessage()
        );

        messagingTemplate.convertAndSend(busTopic, saved);     // Notify driver
        messagingTemplate.convertAndSend(companyTopic, saved); // Notify company
    }

    // Handles driver-initiated messages to the company
    @MessageMapping("/driver-message")
    public void handleDriverMessage(AlertMessage alert) {
        if (alert.getType() == null || alert.getType().isEmpty()) {
            alert.setType("DriverMessage");
        }

        AlertMessage saved = alertRepo.save(alert);

        String companyTopic = "/topic/messages/" + saved.getCompanyId();

        System.out.printf(
                "\uD83D\uDCE8 Driver Message:\n → Company: %s\n ▶ From: %s | Message: %s%n",
                companyTopic, saved.getSenderName(), saved.getMessage()
        );

        messagingTemplate.convertAndSend(companyTopic, saved); // Only notify the company
    }

    @GetMapping("/company/{companyId}")
    public List<AlertMessage> getCompanyAlerts(@PathVariable String companyId) {
        return alertRepo.findByCompanyIdOrderByIdDesc(companyId);
    }
}