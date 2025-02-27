package com.busbuddy.busbuddy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private Integer routeNumber;
    private String startPoint;
    private String endPoint;
    private List<String> departureTimes;
    private List<String> arrivalTimes;
}
