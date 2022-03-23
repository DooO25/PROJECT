package pro.s2k.camp.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping(value ={"/main.do" , "/mainOk.do","/main", "/"}, method = RequestMethod.GET)
	   public String home(Locale locale, Model model) {
	      return "main";
	   }
}