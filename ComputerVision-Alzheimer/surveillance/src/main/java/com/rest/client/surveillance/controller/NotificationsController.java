package com.rest.client.surveillance.controller;

import com.rest.client.surveillance.model.NotificationPagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class NotificationsController {
    @GetMapping("/notifications")
    public String notifications(Model model, @RequestParam(defaultValue = "0") Integer page) {
        RestTemplate restTemplate = new RestTemplate();
        // Send request with GET method and default Headers.
        NotificationPagination notificationPagination = restTemplate.getForObject("http://localhost:8080/api/notificationTrackers/pagination?page="+page, NotificationPagination.class);
        model.addAttribute("notificationPagination", notificationPagination);
        return "notifications";
    }
}
