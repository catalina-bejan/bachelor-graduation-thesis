package com.computervision.alzheimer.rest;

import com.computervision.alzheimer.entity.Video;
import com.computervision.alzheimer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class VideoRestController {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoRestController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @GetMapping("/videos")
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
        Page<Video> videoPage = videoRepository.findAll(paging);
        Map<String,Object> response = new HashMap<>();
        response.put("videos",videoPage.getContent());
        response.put("totalElements",videoPage.getTotalElements());
        response.put("totalPages",videoPage.getTotalPages());
        response.put("pageNumber",videoPage.getNumber());
        response.put("pageSize",videoPage.getSize());
        return response;
    }

    @GetMapping("/videos/{id}")
    public Video getVideoById(@PathVariable Integer id){
        return videoRepository.findById(id).get();
    }

    @GetMapping("/videos/lastaction")
    public Timestamp getLastActionTimeOfType(@RequestParam String type){
        List<Video> videoList = videoRepository.getLastActionTimeOfType(type);
        return this.getTimestamp(videoList,0);
    }

    @GetMapping("/videos/lastaction/{id}")
    public Timestamp getLastActionTimeOfGeneralId(@PathVariable Integer id){
        List<Video> videoList = videoRepository.getLastActionTimeOfGeneralId(id);
        return this.getTimestamp(videoList,0);
    }

    //last is last date before amount of seconds between checks in python script for notifications
    //returns last date of action before period of time already checked
    @GetMapping("/videos/lastpreviousaction")
    public Timestamp getLastPreviousActionTimeOfType(@RequestParam String type, Timestamp last){
        List<Video> videoList = videoRepository.getLastPreviousActionTimeOfType(type,last);
        return this.getTimestamp(videoList,0);
    }

    @GetMapping("/videos/lastpreviousaction/{id}")
    public Timestamp getLastPreviousActionTimeOfGeneralId(@PathVariable Integer id, @RequestParam Timestamp last){
        List<Video> videoList = videoRepository.getLastPreviousActionTimeOfGeneralId(id,last);
        return this.getTimestamp(videoList,0);
    }

    //test to get time from query, I have to get time from db
    @GetMapping("/videos/fromtime")
    public Time getTime(@RequestParam String time){
        return Time.valueOf(time);
    }

    @PostMapping("/videos")
    public Video save(@RequestBody Video object){
        return videoRepository.save(object);
    }

    @PutMapping("/videos")
    public Video update(@RequestBody Video object){
        return videoRepository.save(object);
    }

    @DeleteMapping("/videos/{id}")
    public Video delete(@PathVariable Integer id){
        Video video = videoRepository.findById(id).get();
        videoRepository.deleteById(id);
        return video;
    }

    @DeleteMapping("/videos/old/delete")
    public void deleteOldVideos(@RequestParam Timestamp last){
        List<Video> videosList = videoRepository.getListOfOlderData(last);
        List<Integer> idsList = videosList
                .stream()
                .map(Video::getId)
                .collect(Collectors.toList());
        videoRepository.deleteAllByIdInBatch(idsList);
    }

    public Timestamp getTimestamp(List<Video >videoList, Integer index){
        List<Timestamp> timestampList = videoList
                .stream()
                .map(Video::getTimestamp)
                .collect(Collectors.toList());
        if(!timestampList.isEmpty())
            return timestampList.get(index);
        else
            return null;
    }

}
