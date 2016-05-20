package br.com.nutriapp.security;

import org.springframework.security.core.GrantedAuthority;

import br.com.nutriapp.model.entity.Permissao;

public class GrantedAuthorityDTO implements GrantedAuthority {

	private static final long serialVersionUID = -369031424055124931L;
	
	private String authority;
	
	public GrantedAuthorityDTO(Permissao permissao) {
		super();
		this.authority = permissao.getPermissao();
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}
