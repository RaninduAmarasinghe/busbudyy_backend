package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Company;
import com.busbuddy.busbuddy.Repository.CompanyRepo;
import com.busbuddy.busbuddy.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepo companyRepo;

    // Add a new company
    @PostMapping("/add")
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        String companyId = companyService.createCompany(company);
        return ResponseEntity.ok("Company created successfully with ID: " + companyId);
    }

    // Get company by ID
    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String companyId) {
        Optional<Company> company = companyRepo.findById(companyId);
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all companies
    @GetMapping("/all")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyRepo.findAll());
    }

    // Delete company by ID
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable String companyId) {
        companyRepo.deleteById(companyId);
        return ResponseEntity.ok("Company deleted successfully");
    }

    // Update company by ID
    @PutMapping("/update/{companyId}")
    public ResponseEntity<String> updateCompany(@PathVariable String companyId, @RequestBody Company updatedCompany) {
        Optional<Company> existingCompany = companyRepo.findById(companyId);
        if (existingCompany.isPresent()) {
            Company company = existingCompany.get();
            company.setCompanyName(updatedCompany.getCompanyName());
            company.setCompanyAddress(updatedCompany.getCompanyAddress());
            company.setCompanyEmail(updatedCompany.getCompanyEmail());
            company.setCompanyPhone(updatedCompany.getCompanyPhone());
            company.setCompanyPassword(updatedCompany.getCompanyPassword());
            companyRepo.save(company);
            return ResponseEntity.ok("Company updated successfully");
        } else {
            return ResponseEntity.status(404).body("Company not found");
        }
    }

    // Company login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Company company) {
        Optional<Company> companyOptional = companyRepo.findByCompanyEmail(company.getCompanyEmail());
        if (companyOptional.isPresent() && companyOptional.get().getCompanyPassword().equals(company.getCompanyPassword())) {
            return ResponseEntity.ok(companyOptional.get().getCompanyId());
        } else {
            return ResponseEntity.status(401).body("Incorrect email or password");
        }
    }
}
