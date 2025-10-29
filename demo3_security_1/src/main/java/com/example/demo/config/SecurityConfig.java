package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//Spring boot 에서 설정 (xml) 하지 않고 java 파일 (@Configuration 사용해서 빈객체 생성 주입)

@Configuration
@EnableWebSecurity //이 클래스 스프링 시큐리티 설정 가능  (버전 : 5.x.x > 6.x.x 변화가) 
public class SecurityConfig {

	//@Bean
	//public Emp call{ return new Emp()}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		//인증과 권한 (한곳에 모아서 처리하기를 원해요)
		//자원에 대해서 (특정 페이지 , 특정 폴더 , 특정 자원) 
		//Url >  /admin  >> ROLE_USER 안되고  ROLE_ADMIN 권한만 가능 하다 
		
		
		/*
		 spring regacy 사용한 security 

		   <security:intercept-url pattern="/customer/noticeDetail.do" access="hasRole('ROLE_USER')" />
           <security:intercept-url pattern="/customer/noticeReg.do"    access="hasRole('ROLE_ADMIN')" />
 
         spring boot   security 5.x.x  
		   http.authorizeRequests()
				.antMatchers("/admin","/admin/**").hasRole("ADMIN")
				.antMatchers("/user","/user/**").hasAnyRole("USER","ADMIN")
				.antMatchers("/css/**" , "/js/**" , "/imges/**").permitAll() 다 허용할게
				.antMatchers("/**").permitAll()  다 허용할게
				.anyRequest().authenticated();
		 spring boot   security 6.x.x  람다표현식 
		 보통 spring boot 로 :  spring framework 6.x  + spring boot 3.x  + spring security 6.x
		 */
		
		http.authorizeHttpRequests(auth -> auth
				                               .requestMatchers("/admin/**").hasRole("ADMIN")
				                               .requestMatchers("/user/**").hasAnyRole("USER","ADMIN") //ROLE_USER , ROLE_ADMIN
				                               .requestMatchers("/css/**" , "/js/**" , "/images/**").permitAll()
				                               .requestMatchers("/" , "/**").permitAll()
				                               .anyRequest().authenticated() //이제 남은 나머지 인증된 사용다만 허락
				                  ).formLogin(form -> form.permitAll()
				                  ).logout(logout -> logout.permitAll()); 
		
		return http.build();
	}
	

	/*
	        예전 방식은 사용자 (in-memory) 방식은 사용자 , 암호 , 권한 설정해서 TEST
			<security:user-service>
    			<security:user name="hong"  password="1004" authorities="ROLE_USER"/>
    			<security:user name="admin" password="1004" authorities="ROLE_USER,ROLE_ADMIN"/>
    		</security:user-service>

	
	*/
	//인증 권한 (in-memory)
	@Bean
	public UserDetailsService userDetailsService() {  //사용자 정보 (원칙 DB 가지고.... in memory TEST
		
		UserDetails user = User.builder()
						   .username("user")
						   .password(passwordEncoder().encode("1004"))
						   .roles("USER")		   
						   .build();
						 
		UserDetails admin = User.builder()
				   .username("admin")
				   .password(passwordEncoder().encode("1007"))
				   .roles("USER","ADMIN")		   
				   .build();
		
		
		return new InMemoryUserDetailsManager(user,admin); //in memory 방식 사용자 생성
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
