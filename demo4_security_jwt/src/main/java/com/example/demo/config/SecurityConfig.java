package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration   //객체 생성 ... 주입 자바파일 @Bean
@EnableWebSecurity
public class SecurityConfig {

	/*
	 인증과인가(권한)
	 1. in memory
	 2. jdbc (2가지 쿼리 : id,pwd,enabled  :  auth ) 
	 3. mybatis(jpa) : 사용자 정의 UserDetailService ...  
	*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		//인증 , 권한 , 로그인 방식 정의(Spring, 별도의 처리)
		
		//폼 로그인 방식을 사용하지 않아요(x)
		//form > id , pwd > 전송 > 받아서 ....
		
		//문법 람다식으로 ....권장 (6.x.x)
		
		//http.formLogin().disable();  //5.x.x  메서드 체인 ...
		http.formLogin((login) -> login.disable());  //시대의 흐름 (람다 DSL 표기)
		
		//http spring security 제공 자동화 비활성화
		http.httpBasic((basic) -> basic.disable());
		
		/*
		내부적으로 ....아래 코드  
		http.httpBasic(new Customizer<HttpBasicConfigurer<HttpSecurity>>() {
    		@Override
    		public void customize(HttpBasicConfigurer<HttpSecurity> basic) {
        	basic.disable();
    		}
		});
		
		*/
		
		//CSRF(hidden 태그에 암호화된 문자열 -> 위변조 방지  -> 비활성화
		http.csrf((csrf) -> csrf.disable());
		
		//JWT 사용 -> session 통한 정보를 관리하지 않을 거야 -> 비활성화
		
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}
}


/*

기본지식
 
Spring Security는 기본적으로 다음 두 가지 인증 방식을 제공합니다.

1. HTTP Basic Authentication
브라우저의 기본 팝업창으로 아이디/비밀번호를 입력받는 아주 단순한 인증.
Authorization: Basic <username:password> 헤더를 사용.



2. Form Login
우리가 흔히 사용하는 /login 페이지 기반 로그인 폼 인증.
Spring Boot는 spring-boot-starter-security를 추가하면
자동으로 HTTP Basic이 활성화되어 있어서,
별도 로그인 페이지를 만들지 않아도 브라우저 팝업이 뜹니다.

🔹 따라서 이 설정은
http.httpBasic((basic) -> basic.disable());
→ “브라우저 기본 팝업 로그인 기능을 끈다.”
→ “우리는 대신 다른 인증 방식을 사용하겠다 (예: 폼 로그인, JWT, OAuth2 등).” 
*/ 