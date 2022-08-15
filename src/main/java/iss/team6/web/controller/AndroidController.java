package iss.team6.web.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iss.team6.web.helpers.ActivityInput;
import iss.team6.web.helpers.UserStatistics;
import iss.team6.web.models.Activity;
import iss.team6.web.models.User;
import iss.team6.web.services.ActivityService;
import iss.team6.web.services.UserService;

@RestController
@RequestMapping(path = "/api")
public class AndroidController {
    
    @Autowired
    UserService uService;

    @Autowired
    ActivityService aService;
    
    @GetMapping(value = "/login")
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        if (uService.authenticate(username, password)) return new ResponseEntity<>("Login Successful", HttpStatus.OK);
        else return new ResponseEntity<String>("Login Unsuccessful", HttpStatus.BAD_REQUEST);
	}

    @PostMapping(value = "/createuser")
    public User createUser(@RequestParam String username, @RequestParam String password) {
        User u = new User(username, password);
        uService.createUser(u);
        return u;
    }

    @PutMapping(value = "/{username}/change/{parameter}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username, @PathVariable("parameter") String parameter, @RequestParam String input) {
        if (uService.findUserByUserName(username) == null) return new ResponseEntity<Object>("User doesnt exist", HttpStatus.BAD_REQUEST);
        else {
            //Consider switch-case statement
            if (parameter.equalsIgnoreCase("email")) {
                User u = uService.findUserByUserName(username);
                u.setEmail(input);
                uService.updateUser(u);
                return new ResponseEntity<Object>(u, HttpStatus.OK);
            }
            if (parameter.equalsIgnoreCase("occupation")) {
                User u = uService.findUserByUserName(username);
                u.setOccupation(input);
                uService.updateUser(u);
                return new ResponseEntity<Object>(u, HttpStatus.OK);
            }
            return new ResponseEntity<>("Incorrect parameter name", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/alluserstats")
    public UserStatistics getAllUserStats(@RequestParam String username) {
        return uService.getUserStatistics(uService.findUserByUserName(username));
    }

    @GetMapping(value = "/dailyuserstats")
    public UserStatistics getDailyUserStats(@RequestParam String username) {
        //Can change date field as an input o get daily Stats for any day
        return uService.getDailyUserStatistics(uService.findUserByUserName(username), Date.valueOf(LocalDate.now()));
    }

    @GetMapping(value = "/weeklyuserstats")
    public HashMap<String, UserStatistics> getWeeklyUserStats(@RequestParam String username) {
        return uService.getWeeklyStatistics(uService.findUserByUserName(username));
    }

    @PostMapping(value = "/createactivity")
    public ResponseEntity<String> createNewActivity(@RequestParam String username, @RequestBody ActivityInput activityInput){
        if (uService.findUserByUserName(username) == null) return new ResponseEntity<String>("User doesnt exist", HttpStatus.BAD_REQUEST);
        else {
            aService.createActivity(new Activity(activityInput.getDescription(), 
                                                activityInput.getPoints(), 
                                                activityInput.getTrashType(), 
                                                uService.findUserByUserName(username)));
            return new ResponseEntity<String>("Activity Successfully Created", HttpStatus.OK);
        }
        
    }

    //TODO methods relating to groups

}
