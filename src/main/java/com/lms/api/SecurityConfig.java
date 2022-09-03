package com.lms.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lms.api.service.MyUserDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		auth.authenticationProvider(getAuthProvider());
		/*
		 * auth.inMemoryAuthentication() .withUser("harry")
		 * .password(getPasswordEncoder().encode("potter")) .authorities("ADMIN") .and()
		 * .withUser("ron") .password(getPasswordEncoder().encode("weasely"))
		 * .authorities("USER");
		 */
		
	}
	
	
	
	private AuthenticationProvider getAuthProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(myUserDetailsService);
		auth.setPasswordEncoder(getPasswordEncoder());
		return auth;
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		                   .antMatchers(HttpMethod.POST,"/user/register").permitAll()
		                   .antMatchers(HttpMethod.GET,"/user/login").authenticated()
		                   .antMatchers(HttpMethod.PUT,"/user/update/{id}").authenticated()
		                   .antMatchers(HttpMethod.GET,"/learningtrack").permitAll()
		                   .antMatchers(HttpMethod.POST,"/course/**").hasAnyAuthority("ADMIN")
		       			.antMatchers(HttpMethod.POST,"/learningtrack").hasAnyAuthority("ADMIN")
		       			.antMatchers(HttpMethod.POST,"/enroll/user/learning-track/*/*").authenticated()
		       			.antMatchers(HttpMethod.GET,"/user/learning-track").authenticated()
		       			.antMatchers(HttpMethod.POST,"/author").permitAll()
		       			.antMatchers(HttpMethod.POST,"/author/course/{cid}/{aid}").authenticated()
		       			.antMatchers(HttpMethod.PUT,"/author/course/{cid}/{aid}").authenticated()
		       			
		       			.antMatchers(HttpMethod.POST,"/review/**").authenticated()
		       			.antMatchers(HttpMethod.GET,"/reviews").permitAll()
		       			.antMatchers(HttpMethod.GET,"/course/sort-rating/**").permitAll()
		       			.antMatchers(HttpMethod.PUT,"/review/{rid}").authenticated()
		       			.antMatchers(HttpMethod.POST,"/review/stats/{cid}").authenticated()
		       			
		       			.antMatchers(HttpMethod.POST,"/modules/{cid}").authenticated()
		       			.antMatchers(HttpMethod.POST,"/video/{mid}").authenticated()
		       			.antMatchers(HttpMethod.GET,"/module/alternate/{cid}").permitAll()
		       			.antMatchers(HttpMethod.GET,"/course/video/stats/{cid}").permitAll()
		       			
		       			.antMatchers(HttpMethod.POST,"/topic").authenticated()
		       			.antMatchers(HttpMethod.POST,"/question").authenticated()
		       			.antMatchers(HttpMethod.POST,"/answer").authenticated()
		       			.antMatchers(HttpMethod.POST,"/topic/question/{tid}/{qid}").authenticated()
		       			.antMatchers(HttpMethod.POST,"/question/answer/{qid}/{aid}").authenticated()
		       			.antMatchers(HttpMethod.GET,"/answer/question/{qid}").permitAll()
		       			.antMatchers(HttpMethod.PUT,"/question/{qid}").authenticated()
		       			.antMatchers(HttpMethod.PUT,"/answer/{aid}").authenticated()
		       			.antMatchers(HttpMethod.GET,"/question/likes/{qid}").permitAll()
		       			.antMatchers(HttpMethod.GET,"/answer/likes/{aid}").permitAll()
		       			.antMatchers(HttpMethod.GET,"/question/{tid}").permitAll()
		       			.antMatchers(HttpMethod.GET,"/answer/desc/{qid}").permitAll()
		       			.antMatchers(HttpMethod.GET,"/topic/records").permitAll()
		       			
		       			.anyRequest().permitAll()
		       			.and()
		       			.httpBasic()
		       			.and()
		       			.csrf().disable();
		       	}
		       	
		       	@Bean
		       	public PasswordEncoder getPasswordEncoder()
		       	{
		       		PasswordEncoder passEncoder = new BCryptPasswordEncoder();
		       		return passEncoder;
		       	}
		       }