package com.busbuddy.busbuddy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    private String driverId;
    private String driverName;
private String driverEmail;
private String driverPhone;
private String driverPassword;
private String companyId;
private String companyName;
private String busId;

}
