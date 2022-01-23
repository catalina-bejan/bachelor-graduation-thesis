package com.computervision.alzheimer.rest;

import com.computervision.alzheimer.entity.Scheduler;
import com.computervision.alzheimer.repository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SchedulerRestController {
    private final SchedulerRepository schedulerRepository;

    @Autowired
    public SchedulerRestController(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @GetMapping("/schedulers")
    public List<Scheduler> getSchedulers(){
        return schedulerRepository.findAll();
    }

    @GetMapping("/schedulers/{id}")
    public Scheduler getSchedulers(@PathVariable Integer id){
        return schedulerRepository.findById(id).get();
    }

    @PostMapping("/schedulers")
    public Scheduler save(@RequestBody Scheduler object){
        return schedulerRepository.save(object);
    }

    @PutMapping("/schedulers")
    public Scheduler update(@RequestBody Scheduler object){
        return schedulerRepository.save(object);
    }

    @DeleteMapping("/schedulers/{id}")
    public Scheduler delete(@PathVariable Integer id){
        Scheduler scheduler = schedulerRepository.findById(id).get();
        schedulerRepository.deleteById(id);
        return scheduler;
    }

}
