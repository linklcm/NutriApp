package br.com.nutriapp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginHandler implements AuthenticationSuccessHandler {
	private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();
	private Authentication auth;
	
	public AuthenticationSuccessHandler getTarget() {
		return target;
	}

	public void setTarget(AuthenticationSuccessHandler target) {
		this.target = target;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		setAuth(authentication);
		
		target.onAuthenticationSuccess(request, response, authentication);		
	}

	public void proceed(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		target.onAuthenticationSuccess(request, response, auth);
	}

	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}
}
