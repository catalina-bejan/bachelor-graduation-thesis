package com.rest.client.surveillance.controller;

import com.rest.client.surveillance.model.Video;
import com.rest.client.surveillance.model.VideoPagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class DetectionsController {

    @GetMapping("/detections")
    public String detections(Model model, @RequestParam(defaultValue = "0") Integer page) {
        RestTemplate restTemplate = new RestTemplate();
        // Send request with GET method and default Headers.
        VideoPagination videoPagination = restTemplate.getForObject("http://localhost:8080/api/videos?page="+page, VideoPagination.class);
        model.addAttribute("videoPagination", videoPagination);
        return "detections";
    }
}
