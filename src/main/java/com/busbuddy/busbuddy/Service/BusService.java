package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Bus;
import com.busbuddy.busbuddy.Repository.BusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // Ensures that this class is recognized as a Spring service
public class BusService {

    @Autowired
    private BusRepo busRepo;

    public List<Bus> getBusesByCompany(String companyId) {
        return busRepo.findByCompanyId(companyId);
    }
}
