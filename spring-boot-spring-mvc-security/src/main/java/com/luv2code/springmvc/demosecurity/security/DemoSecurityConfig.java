package com.luv2code.springmvc.demosecurity.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig
{
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource)
    {
    	JdbcUserDetailsManager theUserDetailsManager= new JdbcUserDetailsManager(dataSource);
    	theUserDetailsManager.setUsersByUsernameQuery(
    			"select user_id,pw,active from members where user_id=?"
    			);
    	theUserDetailsManager.setAuthoritiesByUsernameQuery(
    			"select user_id,role from roles where user_id=?"
    			);
    	 return theUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
    	http.authorizeHttpRequests(configuer->
    	  configuer
    	  .requestMatchers("/").hasRole("EMPLOYEE")
    	  .requestMatchers("/leaders/**").hasRole("MANAGER")
    	  .requestMatchers("/systems/**").hasRole("ADMIN")
    	  .anyRequest().authenticated()
    			)
    	.exceptionHandling(configuer->
    	   configuer
    	   .accessDeniedPage("/access-denied")
    			)
    	.formLogin(form->
    	  form
    	  .loginPage("/showMyLoginPage")
    	  .loginProcessingUrl("/authenticateTheUser")
    	  .permitAll()
    			)
    	.logout(logout->
    	   logout.permitAll()
    			);
    	
    	
    	
		return http.build();
    	
    }
    /* @Bean
    public InMemoryUserDetailsManager userDetailsManager()
    {
    	
    	UserDetails chay=User.builder()
    			.username("chay")
    			.password("{noop}test1234")
    			.roles("EMPLOYEE")
    			.build();
    	UserDetails kiran=User.builder()
    			.username("kiran")
    			.password("{noop}test123")
    			.roles("EMPLOYEE","MANAGER")
    			.build();
    	UserDetails chaitu=User.builder()
    			.username("chaitu")
    			.password("{noop}test123")
    			.roles("EMPLOYEE","MANAGER","ADMIN")
    			.build();
    		
		return new InMemoryUserDetailsManager(chay,kiran,chaitu);
    	
    }*/
}
