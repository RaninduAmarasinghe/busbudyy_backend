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

    @MessageMapping("/alert")
    public void handleAlert(AlertMessage alert) {
        if (alert.getType() == null || alert.getType().isEmpty()) {
            alert.setType("Unknown");
        }

        // Save to MongoDB
        AlertMessage saved = alertRepo.save(alert);

        // WebSocket topic per bus (for driver notification)
        String busTopic = "/topic/alerts/" + saved.getBusId();

        // WebSocket topic per company (for admin dashboard)
        String companyTopic = "/topic/messages/" + saved.getCompanyId();

        // Debug logs
        System.out.printf(
                "📨 Broadcasting alert:\n → BusTopic: %s\n → CompanyTopic: %s\n ▶ Type: %s | From: %s | Message: %s%n",
                busTopic, companyTopic, saved.getType(), saved.getSenderName(), saved.getMessage()
        );

        // Send to both bus and company WebSocket channels
        messagingTemplate.convertAndSend(busTopic, saved);     // for driver
        messagingTemplate.convertAndSend(companyTopic, saved); // for dashboard
    }

    @GetMapping("/company/{companyId}")
    public List<AlertMessage> getCompanyAlerts(@PathVariable String companyId) {
        return alertRepo.findByCompanyIdOrderByIdDesc(companyId);
    }
}