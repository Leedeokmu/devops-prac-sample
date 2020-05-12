package com.chicken.review.login.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chicken.review.login.vo.ReviewVO;


@Mapper
public interface ReviewMapper {

	List<ReviewVO> getReviewList();
	
}
