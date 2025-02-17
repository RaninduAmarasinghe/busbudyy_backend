package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Company;
import com.busbuddy.busbuddy.Model.Driver;
import com.busbuddy.busbuddy.Repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Company company) {
        Optional<Company> companyOptional = companyRepo.findByCompanyEmail(company.getCompanyEmail());

       if (companyOptional.isPresent() && companyOptional.get().getCompanyPassword().equals(company.getCompanyPassword())) {
           return ResponseEntity.ok("Company logged in successfully");
       }else {
           return ResponseEntity.status(401).body("Incorrect email or password");
       }
    }

}