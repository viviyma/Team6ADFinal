package iss.team6.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import iss.team6.web.helpers.userSession;
import iss.team6.web.models.LoginDTO;
import iss.team6.web.services.UserService;



@Controller
@RequestMapping("/")
public class loginController {
	
    @Autowired
    UserService uService;
	
	@RequestMapping
	public String Default(Model model) {				
		return "default";		
	}

	@GetMapping(value = "/login") 
	public String loginView(Model model) {
		model.addAttribute("loginDto", new LoginDTO());
		return "loginView";
	}

	@RequestMapping(value = "/authenticate") 
	  public String login(@ModelAttribute("loginDto") @Valid LoginDTO loginDto, BindingResult
	  results, Model model,HttpSession session) { 
		  if(results.hasErrors()) {
	  
			  model.addAttribute("errorMsg", "Wrong credential!!"); return "loginView"; }
	  
		  else if (uService.authenticate(loginDto.getUsername(),
				  loginDto.getPassword())){ userSession u = new userSession(loginDto);
				  session.setAttribute("profile", u ); 
				  return "redirect:/home"; }
	  
		  return "forward:/home";
	 }
	 
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}
	 
	

	
}
