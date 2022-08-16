package iss.team6.web.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import iss.team6.web.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    User findByName(String name); 
    
    @Query("SELECT DISTINCT u.name FROM User u")
    ArrayList<String> findAllUserName();
    
}
