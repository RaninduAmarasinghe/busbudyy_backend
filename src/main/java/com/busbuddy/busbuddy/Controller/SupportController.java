package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.SupportMessage;
import com.busbuddy.busbuddy.Repository.SupportMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/support")
@CrossOrigin
public class SupportController {

    @Autowired
    private SupportMessageRepo supportMessageRepo;

    @PostMapping("/send")
    public ResponseEntity<String> receiveSupportMessage(@RequestBody SupportMessage message) {
        message.setTimestamp(Instant.now().toString());
        supportMessageRepo.save(message);
        return ResponseEntity.ok("Support message received successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<SupportMessage>> getAllMessages() {
        return ResponseEntity.ok(supportMessageRepo.findAll());
    }
}