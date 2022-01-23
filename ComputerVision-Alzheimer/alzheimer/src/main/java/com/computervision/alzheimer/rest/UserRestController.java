package com.computervision.alzheimer.rest;

import com.computervision.alzheimer.entity.User;
import com.computervision.alzheimer.repository.UserRepository;
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
public class UserRestController {
    private final UserRepository userRepository;

    @Autowired
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getSchedulers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getSchedulers(@PathVariable Integer id){
        return userRepository.findById(id).get();
    }

    @PostMapping("/users")
    public User save(@RequestBody User object){
        return userRepository.save(object);
    }

    @PutMapping("/users")
    public User update(@RequestBody User object){
        return userRepository.save(object);
    }

    @DeleteMapping("/users/{id}")
    public User delete(@PathVariable Integer id){
        User scheduler = userRepository.findById(id).get();
        userRepository.deleteById(id);
        return scheduler;
    }
}
