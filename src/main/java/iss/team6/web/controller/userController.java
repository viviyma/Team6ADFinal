
 package iss.team6.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iss.team6.web.models.User;
import iss.team6.web.services.UserService;

  
  @Controller
  @RequestMapping("/") 
  public class userController {
	  
	  @Autowired
	  UserService uservice;
  
	  @RequestMapping(value = "/create")
		public String createUser(Model model) {
		  
		        model.addAttribute("user", new User());
		     
			return "createUserView";
		}
	  
		
	 @PostMapping(value="/saveUser") 
	 public String saveEmployee(@ModelAttribute("user") User user) {
		  
		  uservice.createUser(user);
		  
		  return "redirect:/"; 
		  }
	
  }
 