package com.smk.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smk.blog.exceptions.BearerMissingTokenExceptionOrEmpty;
import com.smk.blog.exceptions.InvalidTokenException;
import com.smk.blog.exceptions.UserNameNullOrSecurityContextNotNullException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTTokenHelper jWTTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestToken = request.getHeader("Authorization");

		String token = "";
		String userName = "";
		System.out.println("@@@@@@@@@@@"+requestToken);
		System.out.println("============public request beinge intercepted by filter");
		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				userName = jWTTokenHelper.getUsernameFromToken(token);
				if (userName != null && SecurityContextHolder.getContext() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
					if (jWTTokenHelper.validateToken(token, userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					} else {
						throw new InvalidTokenException();
					}
				} else {
					throw new UserNameNullOrSecurityContextNotNullException();
				}
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());

			} catch (ExpiredJwtException ex) {
				System.out.println(ex.getMessage());

			} catch (MalformedJwtException ex) {
				System.out.println(ex.getMessage());

			}
		} else {
			throw new BearerMissingTokenExceptionOrEmpty();
		}
		filterChain.doFilter(request, response);

	}

}
