package iss.team6.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import iss.team6.web.MLUtils.trashutil;
import iss.team6.web.helpers.TrashType;
import iss.team6.web.models.Activity;
import iss.team6.web.models.LoginDTO;
import iss.team6.web.models.NewsDTO;
import iss.team6.web.models.User;
import iss.team6.web.services.ActivityService;
import iss.team6.web.services.ScraperService;
import iss.team6.web.services.UserService;

@Controller
@RequestMapping(path="/")
public class CommonController {
    	
	@Autowired
    UserService uService;
	
	@Autowired
    ScraperService sService;

    @Autowired
    ActivityService aService;

    @RequestMapping
	public String Default(Model model) {		
		return "default";		
	}

	@RequestMapping("/home")
	public String home(Model model, HttpSession session) {
        if (session.getAttribute("profile")==null) return "redirect:/login";
        else {
            User user = (User) session.getAttribute("profile");
            model.addAttribute("username", user.getName());
            return "homeView";
        }
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		return "about";
	}
	
	@RequestMapping("/locate")
	public String locatebin(HttpSession session, Model model) {
		if (session.getAttribute("profile") != null) return "mapView";
		else return "redirect:/login";
	}
	
    @RequestMapping("/profile")
    public String profile(Model model, HttpSession session) {
        if (session.getAttribute("profile")==null) return "redirect:/login";
        else {
            User u = (User) session.getAttribute("profile");
            model.addAttribute("username", u.getName());
            model.addAttribute("points", uService.getUserStatistics(u).getPoints());
            model.addAttribute("email", u.getEmail());
            model.addAttribute("occupation", u.getOccupation());
            return "userView";
        }

    }
	
	@RequestMapping("/trashify")
	public String trashify(Model model, HttpSession session) {
        if (session.getAttribute("profile")==null) return "redirect:/login";
		else return "trashifyView";
	}
	
	@PostMapping("/getImage")
    public String saveProduct(HttpSession session, @RequestParam("myfile") MultipartFile inputFile, Model model) throws IOException {
		if (session.getAttribute("profile")!=null) {
            //Importing trashUtils 
            trashutil trash = new trashutil();

            //Getting the filename extension from 'MultiPartFile'
            String inputFileExtension = FileNameUtils.getExtension(inputFile.getOriginalFilename());

            //Creating blank 'File' inside 'resources' folder
            File penisfile = new File("src/main/resources/targetFile."+inputFileExtension);

            //writes 'MultiPartFile' to 'File'
            try (OutputStream os = new FileOutputStream(penisfile)) {
                os.write(inputFile.getBytes());
            }

            //Streams 'File' into trashUtils.predict
            try {
                InputStream inputStream = new FileInputStream(penisfile);
                String fileType = (String) trash.predict(inputStream);
                if (fileType!=null) {
                    System.out.println(fileType); //For debugging
                    model.addAttribute("result",fileType);
                    Activity activity = new Activity("Added new "+fileType+" picture", 20, 
                                                        internalConvert(fileType),
                                                        (User) session.getAttribute("profile"));
                    aService.createActivity(activity);
                    System.out.println(fileType+" penis is long"); //For debugging
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "result";
        }
        else return "redirect:/login";
    }
	
    @RequestMapping("/rewards")
	public String rewards(Model model,HttpSession session) {
        if (session.getAttribute("profile")==null) return "redirect:/login";
		else {
            User u = (User) session.getAttribute("profile");
            model.addAttribute("points", uService.getUserStatistics(u).getPoints());
            model.addAttribute("glass", uService.getUserStatistics(u).getGlassTypeCount());
            model.addAttribute("plastic", uService.getUserStatistics(u).getPlasticTypeCount());
            model.addAttribute("paper", uService.getUserStatistics(u).getPaperTypeCount());
            model.addAttribute("metal", uService.getUserStatistics(u).getMetalTypeCount());
            return "rewardsView";
        }
	}
	
   @RequestMapping("/news")
    public String news(Model model, HttpSession session) {   
        if (session.getAttribute("profile")==null) return "redirect:/login";
        else {
            Set<NewsDTO> responseDTOS = sService.getNews();
            model.addAttribute("newsList",responseDTOS);
            return "newsView";
        } 
    }
	
	@GetMapping(value = "/login") 
	public String loginView(Model model) {
		model.addAttribute("loginDto", new LoginDTO());
		return "loginView";
	}

	@RequestMapping(value = "/authenticate") 
	public String login(@ModelAttribute("loginDto") @Valid LoginDTO loginDto, BindingResult results, Model model,HttpSession session) { 
		if (results.hasErrors()) {
			model.addAttribute("errorMsg", "Wrong credentials!!"); 
			return "loginView"; 
		}
	
		else if (uService.authenticate(loginDto.getUsername(), loginDto.getPassword())){ 
			session.setAttribute("profile", uService.findUserByUserName(loginDto.getUsername()));
			return "redirect:/home"; 
		}
		return "loginView";
	}
	 
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}

    @RequestMapping(value = "/create")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "createUserView";
	}

	@PostMapping(value="/saveUser") 
	public String saveEmployee(@ModelAttribute("user") User user) {
		uService.createUser(user);
		return "redirect:/"; 
	}

    private TrashType internalConvert(String fileType) {
        TrashType trashType;
        switch (fileType) {
            case "paper":
                trashType = TrashType.PAPER;
                break;
            case "metal":
                trashType = TrashType.METAL;
                break;
            case "plastic":
                trashType = TrashType.PLASTIC;
                break;
            case "glass":
                trashType = TrashType.GLASS;
                break;
            case "cardboard":
                trashType = TrashType.PAPER;
                break;
            case "trash":
                trashType = TrashType.PAPER;
                break;
            default:
                trashType = null;
                break;
        }
        return trashType;
    }
	 
}
