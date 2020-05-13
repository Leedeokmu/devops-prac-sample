package com.chicken.review.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chicken.review.login.service.AwsS3Service;
import com.chicken.review.login.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chicken.review.login.service.ReviewService;
import com.chicken.review.login.service.UserService;
import com.chicken.review.login.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class UserController {
	private final AwsS3Service awsS3Service;
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

		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(user.getId(), "pass", roles);
		
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

    @PostMapping
	@ResponseBody
	public String fileUpload(
			@RequestParam("mediaFile") MultipartFile file,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model
	) throws IllegalStateException, IOException {
		System.out.println("file Upload");
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");

		ReviewVO review = new ReviewVO();
		review.setTitle(title);
		review.setContent(content);
		review.setUserId(user.getId());
		awsS3Service.s3FileUpload(file, review);
		return "login";
	}

   
}
