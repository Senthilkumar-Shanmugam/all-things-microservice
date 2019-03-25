package com.example.zuulapigateway.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exmaple.security.JwtConfig;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtConfig  jwtConfig;
	
	
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		    .and()
		     //handle unauthorized attempts
		    .exceptionHandling().authenticationEntryPoint((req,res,e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
		    .and()
		    // add a filter to validate the tokens with every request
		    .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
		    .authorizeRequests()
		       //allow every one to access auth service
			   .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()  
			   // must be an admin if trying to access admin area (authentication is also required here)
			   .antMatchers("/gallery" + "/admin/**").hasRole("ADMIN")
			   // Any other request must be authenticated
			   .anyRequest().authenticated(); 

		    
	}
	
	
	@Bean
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}
}
