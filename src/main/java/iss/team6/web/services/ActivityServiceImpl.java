package iss.team6.web.services;

import java.util.ArrayList;
import java.sql.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import iss.team6.web.models.Activity;
import iss.team6.web.models.User;
import iss.team6.web.repositories.ActivityRepository;
import iss.team6.web.repositories.UserRepository;

@Service
public class ActivityServiceImpl implements ActivityService{

    @Resource
    private ActivityRepository aRepo;

    @Resource
    private UserRepository uRepo;

    @Override
    public void createActivity(Activity activity) {
        aRepo.saveAndFlush(activity);
    }

    @Override
    public ArrayList<Activity> findActivitiesByUser(User user) {  
        return aRepo.findActivitiesByUserName(user.getName());
    }

    @Override
    public ArrayList<Activity> findActivitiesByUserAndDate(User user, Date date) {
        return aRepo.findActivitiesByUserAndDate(user.getName(), date);
    }

    @Override
    public void updateActivity(Activity activity) {
        aRepo.saveAndFlush(activity);
    }

    @Override
    public void deleteActivity(Activity a) {
        a.setUser(null);
        aRepo.saveAndFlush(a);
        aRepo.delete(a);
    }


    
}
