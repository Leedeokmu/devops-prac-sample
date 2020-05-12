package com.chicken.review.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chicken.review.login.mapper.ReviewMapper;
import com.chicken.review.login.mapper.UserMapper;
import com.chicken.review.login.vo.ReviewVO;
import com.chicken.review.login.vo.UserVO;

@Service
public class UserService {
	
	
	@Autowired
	private UserMapper userMapper;
	

	public int updateUserJoin(UserVO userVO) {
		
		return userMapper.updateUserJoin(userVO);
	}
	
	
}
