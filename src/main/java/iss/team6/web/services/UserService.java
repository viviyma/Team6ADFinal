package iss.team6.web.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import iss.team6.web.helpers.UserStatistics;
import iss.team6.web.models.User;

public interface UserService {
    
    void createUser(User user);
    
    User findUserByUserName(String userName);

    void updateUser(User user);

    void deleteUser(User user);

    boolean authenticate(String username, String password);

    HashMap<String, UserStatistics> getWeeklyStatistics(User user);  
    
    UserStatistics getUserStatistics(User user);

    UserStatistics getDailyUserStatistics(User user, Date date);
    
    public ArrayList<String> findAllUserName();
    
    
}
