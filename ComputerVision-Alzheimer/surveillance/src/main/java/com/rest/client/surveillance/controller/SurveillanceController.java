package com.rest.client.surveillance.controller;

import com.rest.client.surveillance.model.Notification;
import com.rest.client.surveillance.model.NotificationMessage;
import com.rest.client.surveillance.model.Scheduler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class SurveillanceController {

    @GetMapping("/")
    public String redirect(Model model) {
        return "redirect:/surveillance";
    }

    @GetMapping("/surveillance")
    public String surveillance(Model model) {
        return "surveillance";
    }

}
