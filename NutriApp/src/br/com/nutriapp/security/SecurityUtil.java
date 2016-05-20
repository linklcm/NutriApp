package br.com.nutriapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	public static UserDetailsDTO getUserCredential(){
		Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
		Object user = autenticacao.getPrincipal();
		return user instanceof UserDetailsDTO ? (UserDetailsDTO)user : null;
	}
}	
