package com.smk.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.smk.blog.security.CustomUserDetailService;
import com.smk.blog.security.JWTAuthenticationEntryPoint;
import com.smk.blog.security.JWTAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
public class SecurityConfig /* extends WebSecurityConfigurerAdapter */ {
	
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/login",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"

	};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JWTAuthenticationEntryPoint jWTAuthenticationEntryPoint;
	
	@Autowired
	private JWTAuthenticationFilter jWTAuthenticationFilter;

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http. csrf(). disable(). authorizeRequests().
	 * antMatchers("/api/v1/auth/login"). permitAll(). anyRequest().
	 * authenticated(). and(). exceptionHandling().
	 * authenticationEntryPoint(jWTAuthenticationEntryPoint). and().
	 * sessionManagement(). sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	 * 
	 * http.addFilterBefore(jWTAuthenticationFilter,
	 * UsernamePasswordAuthenticationFilter.class); }
	 */
	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     
		http.
		csrf().
		disable().
		authorizeRequests().
		antMatchers("/**").
		permitAll().
		anyRequest().
		authenticated().
		and().
		exceptionHandling().
		authenticationEntryPoint(jWTAuthenticationEntryPoint).
		and().
		sessionManagement().
		sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
    }
	
	

	/* @Override */
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}
	
	
	
	/*
	 * @Bean
	 * 
	 * @Override public AuthenticationManager authenticationManagerBean() throws
	 * Exception { // TODO Auto-generated method stub return
	 * super.authenticationManagerBean(); }
	 */
	
	
	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	
	
	

}
