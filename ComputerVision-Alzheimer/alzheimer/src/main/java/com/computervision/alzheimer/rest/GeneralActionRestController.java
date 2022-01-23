package com.computervision.alzheimer.rest;

import com.computervision.alzheimer.entity.GeneralAction;
import com.computervision.alzheimer.repository.GeneralActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GeneralActionRestController {
    private final GeneralActionRepository generalActionRepository;

    @Autowired
    public GeneralActionRestController(GeneralActionRepository generalActionRepository) {
        this.generalActionRepository = generalActionRepository;
    }

    @GetMapping("/generalActions/between/type")
    public List<String> getTypeByDateBetween(@RequestParam Timestamp start, @RequestParam Timestamp end){
        List<GeneralAction> generalActionsList = generalActionRepository.getByDateBetween(start,end);
        List<String> typeList = generalActionsList
                .stream()
                .map(GeneralAction::getType)
                .collect(Collectors.toList());
        return typeList;
    }

    @GetMapping("/generalActions/between/id")
    public List<Integer> getIdByDateBetween(@RequestParam Timestamp start, @RequestParam Timestamp end){
        List<GeneralAction> generalActionsList = generalActionRepository.getByDateBetween(start,end);
        List<Integer> idList = generalActionsList
                .stream()
                .map(GeneralAction::getId)
                .collect(Collectors.toList());
        return idList;
    }

    @GetMapping("/generalActions")
    public String getIdByDateBetween(@RequestParam String activity){
        GeneralAction generalAction = generalActionRepository.getBySearchedActivityName(activity);
        return generalAction.getType();
    }
}
