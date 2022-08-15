package iss.team6.web.repositories;

import java.util.ArrayList;
import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iss.team6.web.models.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer>{

    @Query("SELECT a FROM Activity a WHERE a.user.name = :username")
    ArrayList<Activity> findActivitiesByUserName(@Param("username") String name);

    @Query("SELECT a FROM Activity a WHERE a.user.name = :username AND a.dateTime = :date")
    ArrayList<Activity> findActivitiesByUserAndDate(@Param("username") String name, @Param("date") Date date); 
}
