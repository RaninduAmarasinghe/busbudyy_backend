package com.busbuddy.busbuddy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alerts")
public class AlertMessage {
    @Id
    private String id;

    private String busId;
    private String companyId;
    private String senderName;
    private String contactNumber;
    private String message;
    private String type;
}