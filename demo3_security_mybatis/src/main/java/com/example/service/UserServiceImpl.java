package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.UserAuth;
import com.example.dto.Users;
import com.example.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	//회원가입 비밀번호 암호화
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//로그인 처리
	@Override
	public Users login(String username) {
		Users user = userMapper.login(username);
		return user;
	}
	
	//POINT 회원가입
	@Override
	@Transactional
	public int join(Users user) throws Exception{//Trigger
		//1.비밀번호 암호화
		String userPw = user.getUserPw();
		String encodeUserPw = passwordEncoder.encode(userPw);
		user.setUserPw(encodeUserPw);
		
		int result = 0;
		try {
			 result = userMapper.join(user); //회원가입 insert
			 if(result > 0) {
				 UserAuth userAuth = new UserAuth();
				 userAuth.setUserId(user.getUserId());
				 userAuth.setAuth("ROLE_USER");
				 result += userMapper.insertAuth(userAuth); //권한 insert
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//회원가입 권한 등록( 트랜잭션, PL-SQL(trigger) )
	@Override
	public int insertAuth(UserAuth userAuth) throws Exception{
		return 0;
		
	}
	

}
