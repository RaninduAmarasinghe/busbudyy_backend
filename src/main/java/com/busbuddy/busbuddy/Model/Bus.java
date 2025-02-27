package com.busbuddy.busbuddy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Bus {



    @Id
    private String busNumber;
private List<Route> routes;
private String companyId;
}
