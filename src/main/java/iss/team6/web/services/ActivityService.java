package iss.team6.web.services;

import java.util.ArrayList;
import java.sql.Date;

import iss.team6.web.models.Activity;
import iss.team6.web.models.User;

public interface ActivityService {

    void createActivity(Activity activity);

    ArrayList<Activity> findActivitiesByUser(User user);

    ArrayList<Activity> findActivitiesByUserAndDate(User user, Date date);

    void updateActivity(Activity activity);

    void deleteActivity(Activity activity);

}
