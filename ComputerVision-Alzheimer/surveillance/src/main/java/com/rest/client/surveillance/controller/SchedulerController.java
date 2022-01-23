package com.rest.client.surveillance.controller;

import com.rest.client.surveillance.model.Notification;
import com.rest.client.surveillance.model.NotificationMessage;
import com.rest.client.surveillance.model.Scheduler;
import com.rest.client.surveillance.model.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class SchedulerController {

    @GetMapping("/schedule")
    public String schedule(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method and default Headers.
        Scheduler scheduler = restTemplate.getForObject("http://localhost:8080/api/schedulers/1", Scheduler.class);
        Notification[] notifications = restTemplate.getForObject("http://localhost:8080/api/notifications", Notification[].class);
        if (notifications != null && notifications.length==3) {
            NotificationMessage notificationMessage = new NotificationMessage(notifications[0].getPatientMessage(), notifications[1].getPatientMessage(), notifications[2].getPatientMessage(),notifications[0].getCaretakerMessage(), notifications[1].getCaretakerMessage(), notifications[2].getCaretakerMessage());
            model.addAttribute("notificationMessage", notificationMessage);
        }
        User user = restTemplate.getForObject("http://localhost:8080/api/users/1", User.class);
        model.addAttribute("scheduler", scheduler);
        model.addAttribute("user", user);
        return "schedule";
    }

    @PostMapping("/update")
    public String update(Scheduler scheduler,NotificationMessage notificationMessage,User user, Model model) throws ParseException {
        scheduler.setId(1);
//        scheduler.setStoredDataHours(48);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        scheduler.setSleepingFrom(new Time(formatter.parse(scheduler.getSleepingFrom()).getTime()).toString());
        scheduler.setSleepingTo(new Time(formatter.parse(scheduler.getSleepingTo()).getTime()).toString());
        System.out.println(scheduler);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        RestTemplate restTemplate = new RestTemplate();
        // Data attached to the request.
        HttpEntity<Scheduler> requestBodyScheduler = new HttpEntity<>(scheduler, headers);
        // Send request with PUT method.
        restTemplate.exchange("http://localhost:8080/api/schedulers", HttpMethod.PUT, requestBodyScheduler, Void.class);

        Notification notificationStopEat = restTemplate.getForObject("http://localhost:8080/api/notifications/1", Notification.class);
        Notification notificationForgotEat = restTemplate.getForObject("http://localhost:8080/api/notifications/2", Notification.class);
        Notification notificationForgotDrink = restTemplate.getForObject("http://localhost:8080/api/notifications/3", Notification.class);
        notificationStopEat.setPatientMessage(notificationMessage.getEatingTooOftenMessage());
        notificationForgotEat.setPatientMessage(notificationMessage.getForgotToEatMessage());
        notificationForgotDrink.setPatientMessage(notificationMessage.getForgotToDrinkMessage());

        notificationStopEat.setCaretakerMessage(notificationMessage.getEatingTooOftenMessageForCaretaker());
        notificationForgotEat.setCaretakerMessage(notificationMessage.getForgotToEatMessageForCaretaker());
        notificationForgotDrink.setCaretakerMessage(notificationMessage.getForgotToDrinkMessageForCaretaker());

        HttpEntity<Notification> requestBodyNotificationStopEat = new HttpEntity<>(notificationStopEat, headers);
        HttpEntity<Notification> requestBodyNotificationForgotEat = new HttpEntity<>(notificationForgotEat, headers);
        HttpEntity<Notification> requestBodyNotificationForgotDrink = new HttpEntity<>(notificationForgotDrink, headers);
        restTemplate.exchange("http://localhost:8080/api/notifications", HttpMethod.PUT, requestBodyNotificationStopEat, Void.class);
        restTemplate.exchange("http://localhost:8080/api/notifications", HttpMethod.PUT, requestBodyNotificationForgotEat, Void.class);
        restTemplate.exchange("http://localhost:8080/api/notifications", HttpMethod.PUT, requestBodyNotificationForgotDrink, Void.class);

        user.setId(1);
        user.setRole("caretaker");
        HttpEntity<User> requestBodyUser = new HttpEntity<>(user, headers);
        restTemplate.exchange("http://localhost:8080/api/users", HttpMethod.PUT, requestBodyUser, Void.class);

        return "redirect:/schedule";
    }

}
