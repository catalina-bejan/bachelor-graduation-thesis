package com.computervision.alzheimer.rest;

import com.computervision.alzheimer.entity.NotificationTracker;
import com.computervision.alzheimer.repository.NotificationTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NotificationTrackerRestController {
    private final NotificationTrackerRepository notificationTrackerRepository;

    @Autowired
    public NotificationTrackerRestController(NotificationTrackerRepository notificationTrackerRepository) {
        this.notificationTrackerRepository = notificationTrackerRepository;
    }

    @GetMapping("/notificationTrackers/pagination")
    public Map<String,Object> getVideos(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "5") Integer size,
                                        @RequestParam(defaultValue = "timestamp") String sort,
                                        @RequestParam(defaultValue = "desc") String order){
        Pageable paging;
        // Set sort
        Sort pageSort = Sort.by(sort);
        if (order.equals("desc")) {
            pageSort = pageSort.descending();
        }

        // Set page
        paging = PageRequest.of(page, size, pageSort);
        Page<NotificationTracker> notificationPage = notificationTrackerRepository.findAll(paging);
        Map<String,Object> response = new HashMap<>();
        response.put("notifications",notificationPage.getContent());
        response.put("totalElements",notificationPage.getTotalElements());
        response.put("totalPages",notificationPage.getTotalPages());
        response.put("pageNumber",notificationPage.getNumber());
        response.put("pageSize",notificationPage.getSize());
        return response;
    }

    @GetMapping("/notificationTrackers")
    public List<NotificationTracker> getNotificationTrackers(){
        return notificationTrackerRepository.findAll();
    }

    @GetMapping("/notificationTrackers/{id}")
    public NotificationTracker getNotificationTrackers(@PathVariable Integer id){
        return notificationTrackerRepository.findById(id).get();
    }

    @GetMapping("/notificationTrackers/last/{id}")
    public Timestamp getLastTimestampByNotification(@PathVariable Integer id){
        return notificationTrackerRepository.getLastNotificationTimeByNotification(id);
    }

    @PostMapping("/notificationTrackers")
    public NotificationTracker save(@RequestBody NotificationTracker object){
        return notificationTrackerRepository.save(object);
    }

    @PutMapping("/notificationTrackers")
    public NotificationTracker update(@RequestBody NotificationTracker object){
        return notificationTrackerRepository.save(object);
    }

    @DeleteMapping("/notificationTrackers/{id}")
    public NotificationTracker delete(@PathVariable Integer id){
        NotificationTracker notificationTracker = notificationTrackerRepository.findById(id).get();
        notificationTrackerRepository.deleteById(id);
        return notificationTracker;
    }

//    @Transactional
    @DeleteMapping("/notificationTrackers/old/delete")
    public void deleteOldNotifications(@RequestParam Timestamp last){
        List<NotificationTracker> notificationTrackersList = notificationTrackerRepository.getListOfOlderData(last);
        List<Integer> idsList = notificationTrackersList
                .stream()
                .map(NotificationTracker::getId)
                .collect(Collectors.toList());
        notificationTrackerRepository.deleteAllByIdInBatch(idsList);
    }
}
