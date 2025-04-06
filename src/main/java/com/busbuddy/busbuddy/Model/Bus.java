package com.busbuddy.busbuddy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Bus {



    @Id
    private String busId;
    private String busNumber;
private List<Route> routes;
private String companyId;
    private String driverId;
    @DBRef
    private Driver driver;
    private String status; // Example: "Running" or "Stopped"
    private Map<String, Double> location;
}
