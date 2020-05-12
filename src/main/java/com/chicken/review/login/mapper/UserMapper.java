package com.chicken.review.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.chicken.review.login.vo.UserVO;


@Mapper
public interface UserMapper {
	int updateUserJoin(UserVO userVO);
}
