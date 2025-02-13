package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Bus;
import com.busbuddy.busbuddy.Repository.BusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus")
@CrossOrigin
public class BusController {

    @Autowired

    BusRepo busRepo;
    @PostMapping("/add")

    public String addBus(@RequestBody Bus bus) {
        busRepo.save(bus);
        return "success";
    }

}
