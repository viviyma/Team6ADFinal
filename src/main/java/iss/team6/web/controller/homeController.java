package iss.team6.web.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import iss.team6.web.models.LoginDTO;
import iss.team6.web.models.NewsDTO;
import iss.team6.web.services.ScraperService;
import iss.team6.web.services.UserService;

@Controller
@RequestMapping("/")
public class homeController {
	
	@Autowired
    UserService uService;
	
	@Autowired
    ScraperService sService;

	@RequestMapping("/home")
	public String home(Model model) {
		//model.addAttribute("name","Home Page");
		
		
	return "homeView";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		//model.addAttribute("name","Home Page");
		
		
	return "about";
	}
	
	@RequestMapping("/locate")
	public String locatebin(Model model) {
		//model.addAttribute("name","Home Page");
		
		
	return "mapView";
		
	}
	@RequestMapping("/profile")
	public String profile(@ModelAttribute("loginDto") @Valid LoginDTO loginDto, BindingResult
			  results, Model model,HttpSession session) {
					//System.out.println(session.getAttribute("profile").toString()); 
					System.out.println(uService.findUserByUserName("yeemon").toString()); 
					
					/*
					 * //model.addAttribute("userprofile",
					 * uService.findUserByUserName(loginDto.getUsername())); //
					 * System.out.print(uService.findUserByUserName(loginDto.getUsername()).getEmail
					 * ());
					 */
		
	return "userView";
	}
	
	@RequestMapping("/trashify")
	public String trashify(Model model) {
		
		
	return "trashifyView";
	}
	
	@PostMapping("/getImage")
    public String saveProduct(@RequestParam("file") MultipartFile file, Model model)
    {
    	//productService.saveProductToDB(file, name, desc, price);
    	return "trashifyView";
    }
	
   @RequestMapping("/news")
    public String news(Model model) {    
        Set<NewsDTO> responseDTOS = sService.getNews();
        model.addAttribute("newsList",responseDTOS);
        return "newsView";
    }
	
	
}
