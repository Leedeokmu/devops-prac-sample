package com.chicken.review.login.vo;
import lombok.Data;

@Data
public class ReviewVO {

	int seq;
	String title;
	String content;
	String s3ImageUrl;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getS3ImageUrl() {
		return s3ImageUrl;
	}
	public void setS3ImageUrl(String s3ImageUrl) {
		this.s3ImageUrl = s3ImageUrl;
	}
	
}
