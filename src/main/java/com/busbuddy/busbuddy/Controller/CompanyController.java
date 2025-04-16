package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Company;
import com.busbuddy.busbuddy.Model.Driver;
import com.busbuddy.busbuddy.Repository.CompanyRepo;
import com.busbuddy.busbuddy.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/companies")
@CrossOrigin
public class CompanyController {

    //adding company
    @Autowired
    private CompanyService companyService;

    // Adding company
    @PostMapping("/add")
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        String companyId = companyService.createCompany(company);

        return ResponseEntity.ok("Company created successfully with ID: " + companyId);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String companyId) {
        Optional<Company> company = companyRepo.findById(companyId);
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Autowired
    private CompanyRepo companyRepo;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Company company) {
        Optional<Company> companyOptional = companyRepo.findByCompanyEmail(company.getCompanyEmail());

       if (companyOptional.isPresent() && companyOptional.get().getCompanyPassword().equals(company.getCompanyPassword())) {
           return ResponseEntity.ok(companyOptional.get().getCompanyId());
       }else {
           return ResponseEntity.status(401).body("Incorrect email or password");
       }
    }

}