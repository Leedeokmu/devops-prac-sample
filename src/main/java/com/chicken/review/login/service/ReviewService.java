package com.chicken.review.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chicken.review.login.mapper.ReviewMapper;
import com.chicken.review.login.vo.ReviewVO;

@Service
public class ReviewService {
	
	
	@Autowired
	private ReviewMapper reviewMapper;
	

	public List<ReviewVO> getReviewList() {
		
		return reviewMapper.getReviewList();
	}
	
	
}
