package com.computervision.alzheimer.rest;

import com.computervision.alzheimer.entity.SearchedActivity;
import com.computervision.alzheimer.repository.SearchedActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchedActivityRestController {

    private final SearchedActivityRepository searchedActivityRepository;

    @Autowired
    public SearchedActivityRestController(SearchedActivityRepository searchedActivityRepository) {
        this.searchedActivityRepository = searchedActivityRepository;
    }

    @GetMapping("/searchedActivities/activityname/all")
    public List<String> getAllActivityName(){
        return searchedActivityRepository.getAllActivityName();
    }

    @GetMapping("/searchedActivities/activityname")
    public List<String> getActivityNameByType(@RequestParam String type){
        return searchedActivityRepository.getActivityNameByType(type);
    }

    @GetMapping("/searchedActivities/findid")
    public Integer getSearchedActivityIdByName(@RequestParam String name){
        return searchedActivityRepository.getSearchedActivityIdByName(name);
    }

    @GetMapping("/searchedActivities")
    public List<SearchedActivity> getSearchActivities(){
        return searchedActivityRepository.findAll();
    }

    @GetMapping("/searchedActivities/{id}")
    public SearchedActivity getSearchActivities(@PathVariable Integer id){
        return searchedActivityRepository.findById(id).get();
    }

    @PostMapping("/searchedActivities")
    public SearchedActivity save(@RequestBody SearchedActivity object){
        return searchedActivityRepository.save(object);
    }

    @PutMapping("/searchedActivities")
    public SearchedActivity update(@RequestBody SearchedActivity object){
        return searchedActivityRepository.save(object);
    }

    @DeleteMapping("/searchedActivities/{id}")
    public SearchedActivity delete(@PathVariable Integer id){
        SearchedActivity searchedActivity = searchedActivityRepository.findById(id).get();
        searchedActivityRepository.deleteById(id);
        return searchedActivity;
    }
}
