package com.chicken.review.login.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chicken.review.login.service.ReviewService;
import com.chicken.review.login.service.UserService;
import com.chicken.review.login.vo.UserVO;






@Controller
public class UserController {
	
	//private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
    private ReviewService reviewService;
	@Autowired
    private UserService userService;
	
	@PostMapping("/kakaoLogin")
	@ResponseBody
	public int kakaoLogin(UserVO user) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	HttpServletRequest request = attr.getRequest();
    	HttpServletResponse response = attr.getResponse();
    	
		System.out.println("TEST");
		System.out.println(user.getEmail());
		System.out.println(user.getId());
		System.out.println(user.getNickname());
		
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(user.getId(), "pass");
		
		userAuthentication.setDetails(user);
		userService.updateUserJoin(user);
		
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
		HttpSession session= request.getSession(false);
		session.setAttribute("user", user);
		return 1;
	}
	
    @RequestMapping(value="/login")
    public String login(Model model, String error, String logout) {
    	
    	

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	
    	model.addAttribute("reviewList", reviewService.getReviewList());
    	
    	if(authentication != null) {
    		System.out.println("LOGIN");
    		
    		
    		return "login/login";
	    	//if(!authentication.getPrincipal().equals("anonymousUser"))
	    	//	return "redirect:/";
    	}
    	
    	

        return "login/login";
    }

    
   
}
