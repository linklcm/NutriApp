package br.com.nutriapp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.nutriapp.model.entity.Permissao;
import br.com.nutriapp.model.entity.Usuario;

public class UserDetailsDTO implements UserDetails {
		private static final long serialVersionUID = -1891553813173790562L;
		
		private String login;
		private String senha;
		private List<GrantedAuthority> permissoes;
		private boolean contaExpirada = false;
		private boolean bloqueado = false;
		private boolean credenciaisExpirada;
		private boolean ativado;
		private Usuario usuario;			
			
		public UserDetailsDTO(Usuario usuario) {
			super();
			this.login = usuario.getNome();
			this.senha = usuario.getSenha();
			this.credenciaisExpirada = usuario.getDataFim()!=null?usuario.getDataFim().before(new Date()):false;
			this.ativado = usuario.getAtivo();
			this.usuario = usuario;
			
			this.permissoes = new ArrayList<GrantedAuthority>();
	        
			for (Permissao permissao : usuario.getPermissoes()) {		     
	             this.permissoes.add(new SimpleGrantedAuthority(permissao.getPermissao()));
			}
		}		
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return permissoes;
		}

		@Override
		public String getPassword() {
			return senha;
		}

		@Override
		public String getUsername() {
			return login;
		}

		@Override
		public boolean isAccountNonExpired() {
			return !contaExpirada;
		}

		@Override
		public boolean isAccountNonLocked() {
			return !bloqueado;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return !credenciaisExpirada;
		}

		@Override
		public boolean isEnabled() {
			return ativado;
		}
		
		public final Usuario getUsuario() {
			return usuario;
		}

		public void addPermissao(Permissao permissao){
			this.permissoes.add(new SimpleGrantedAuthority(permissao.getPermissao()));
		}
}
