package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Company;
import com.busbuddy.busbuddy.Repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyRepo companyRepo;

    @PostMapping("/add")
    public String addCompany(@RequestBody Company company) {
        companyRepo.save(company);
        return "Company added successfully";
    }
}