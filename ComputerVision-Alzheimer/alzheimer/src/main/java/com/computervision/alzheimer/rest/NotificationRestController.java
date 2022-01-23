package com.computervision.alzheimer.rest;

import com.computervision.alzheimer.entity.Notification;
import com.computervision.alzheimer.repository.NotificationRepository;
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
public class NotificationRestController {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationRestController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/notifications")
    public List<Notification> getNotifications(){
        return notificationRepository.findAll();
    }

    @GetMapping("/notifications/{id}")
    public Notification getNotifications(@PathVariable Integer id){
        return notificationRepository.findById(id).get();
    }

    @PostMapping("/notifications")
    public Notification save(@RequestBody Notification object){
        return notificationRepository.save(object);
    }

    @PutMapping("/notifications")
    public Notification update(@RequestBody Notification object){
        return notificationRepository.save(object);
    }

    @DeleteMapping("/notifications/{id}")
    public Notification delete(@PathVariable Integer id){
        Notification notification = notificationRepository.findById(id).get();
        notificationRepository.deleteById(id);
        return notification;
    }
}
