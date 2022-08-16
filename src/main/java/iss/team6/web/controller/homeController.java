// package iss.team6.web.controller;

// import java.util.Set;

// import javax.servlet.http.HttpSession;
// import javax.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

// import iss.team6.web.helpers.userSession;
// import iss.team6.web.models.LoginDTO;
// import iss.team6.web.models.NewsDTO;
// import iss.team6.web.models.User;
// import iss.team6.web.services.ScraperService;
// import iss.team6.web.services.UserService;

// @Controller
// @RequestMapping("/")
// public class homeController {
	
// 	@Autowired
//     UserService uService;
	
// 	@Autowired
//     ScraperService sService;

// 	@RequestMapping("/home")
// 	public String chartPage(Model model, HttpSession session) {
// 		userSession user= (userSession) session.getAttribute("profile");
// 		String username=user.getUser().getUsername();
// 		model.addAttribute("username", username);
		
// 	return "homeView";
// 	}
	
// 	@RequestMapping("/about")
// 	public String about(Model model) {
// 		//model.addAttribute("name","Home Page");
// 		return "about";
// 	}
	
// 	@RequestMapping("/locate")
// 	public String locatebin(Model model) {
// 		//model.addAttribute("name","Home Page");
// 		return "mapView";
// 	}
	
// 	@RequestMapping("/profile")
// 	public String profile(@ModelAttribute("loginDto") @Valid LoginDTO loginDto, BindingResult
// 		results, Model model,HttpSession session) {
// 		User u = (User) session.getAttribute("profile");
// 		model.addAttribute("username", u.getName());
// 		model.addAttribute("points", uService.getUserStatistics(u).getPoints());
// 		model.addAttribute("email", "");
// 		return "userView";
// 	}
	
// 	@RequestMapping("/trashify")
// 	public String trashify(Model model) {
// 		return "trashifyView";
// 	}
	
// 	@PostMapping("/getImage")
//     public String saveProduct(@RequestParam("file") MultipartFile file, Model model) {
// 		//TODO send to ml
// 		//ml send back name
// 		//createactivity(), add points
// 		//return to view
//     	return "trashifyView";
//     }
	
//    @RequestMapping("/news")
//     public String news(Model model) {    
//         Set<NewsDTO> responseDTOS = sService.getNews();
//         model.addAttribute("newsList",responseDTOS);
//         return "newsView";
//     }
	
	
// }
