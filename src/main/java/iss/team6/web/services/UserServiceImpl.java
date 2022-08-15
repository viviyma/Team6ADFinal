package iss.team6.web.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import iss.team6.web.helpers.UserStatistics;
import iss.team6.web.models.Activity;
import iss.team6.web.models.User;
import iss.team6.web.repositories.ActivityRepository;
import iss.team6.web.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserRepository uRepo;

    @Resource
    ActivityRepository aRepo;
    
    @Override
    public void createUser(User user) {
        uRepo.saveAndFlush(user);    
    }

    @Override
    public User findUserByUserName(String userName) {
        return uRepo.findByName(userName);
    }

    @Override
    public void updateUser(User user) {
        uRepo.saveAndFlush(user);
    }

    @Override
    public void deleteUser(User user) {
        ArrayList<Activity> activities = aRepo.findActivitiesByUserName(user.getName());
        for (Activity a : activities) {
            a.setUser(null);
            aRepo.saveAndFlush(a);
            aRepo.delete(a);
        }
        uRepo.delete(user);
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (uRepo.findByName(username)==null) return false; 
        else {
            if (uRepo.findByName(username).getPassword().equals(password)) return true;
            else return false;
        }
        
    }

    @Override
    public HashMap<String, UserStatistics> getWeeklyStatistics(User user) {
        // TODO JUnit Test this method
        
        //Finding first day of the week based on current date
        Locale locale = new Locale("EN", "SG");
        int weekOfYear = LocalDate
                            .now()
                            .get(WeekFields.of(locale).weekOfYear());
        LocalDate firstDayofWeek = LocalDate
                            .now()
                            .with(WeekFields.of(locale).getFirstDayOfWeek())
                            .with(WeekFields.of(locale).weekOfWeekBasedYear(), weekOfYear);
        
        //Populating hashmap of weekly statistics
        HashMap<String, UserStatistics> weeklyStats = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate currentDay = firstDayofWeek.plusDays(i);
            UserStatistics currentDayStats = getDailyUserStatistics(user, Date.valueOf(currentDay));
            weeklyStats.put(currentDay.toString(), currentDayStats);
        }
        return weeklyStats;
    }

    @Override
    public UserStatistics getUserStatistics(User user) {
        UserStatistics userStats = new UserStatistics();
        
        userStats.setUserName(user.getName());
        ArrayList<Activity> activities = aRepo.findActivitiesByUserName(user.getName());

        Integer pointSum = 0;
        Integer metalCount = 0;
        Integer glassCount = 0;
        Integer paperCount = 0;
        Integer plasticCount = 0;

        for (Activity act : activities) {
            switch (act.getTrashType()) {
                case METAL:
                    metalCount++;
                    break;
                
                case GLASS:
                    glassCount++;
                    break;

                case PAPER:
                    paperCount++;
                    break;

                case PLASTIC:
                    plasticCount++;
                    break;
                    
                default:
                    break;
            }
            pointSum += act.getPoints();
        }
        
        userStats.setPoints(pointSum);
        userStats.setGlassTypeCount(glassCount);
        userStats.setMetalTypeCount(metalCount);
        userStats.setPaperTypeCount(paperCount);
        userStats.setPlasticTypeCount(plasticCount);

        return userStats;
    }

    @Override
    public UserStatistics getDailyUserStatistics(User user, Date date) {
        // TODO JUnit Test this method
        UserStatistics userStats = new UserStatistics();
        
        userStats.setUserName(user.getName());
        ArrayList<Activity> activities = aRepo.findActivitiesByUserAndDate(user.getName(), date);

        Integer pointSum = 0;
        Integer metalCount = 0;
        Integer glassCount = 0;
        Integer paperCount = 0;
        Integer plasticCount = 0;

        for (Activity act : activities) {
            switch (act.getTrashType()) {
                case METAL:
                    metalCount++;
                    break;
                
                case GLASS:
                    glassCount++;
                    break;

                case PAPER:
                    paperCount++;
                    break;

                case PLASTIC:
                    plasticCount++;
                    break;
                    
                default:
                    break;
            }
            pointSum += act.getPoints();
        }
        
        userStats.setPoints(pointSum);
        userStats.setGlassTypeCount(glassCount);
        userStats.setMetalTypeCount(metalCount);
        userStats.setPaperTypeCount(paperCount);
        userStats.setPlasticTypeCount(plasticCount);

        return userStats;
    }

	@Override
	public ArrayList<String> findAllUserName() {
		return (ArrayList<String>) uRepo.findAllUserName();
		
	}
    
    
}
