package com.chicken.review.login.vo;
import lombok.Data;

@Data
public class ReviewVO {

	String userId;
	int seq;
	String title;
	String content;
	String s3ImageUrl;
}
