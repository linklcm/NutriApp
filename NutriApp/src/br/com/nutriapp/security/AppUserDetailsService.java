package br.com.nutriapp.security;

import javax.persistence.EntityManager;

import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.nutriapp.model.dao.GenericDAO;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.util.JPAUtil;

public class AppUserDetailsService implements UserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String username)	
			throws UsernameNotFoundException {
				
		EntityManager manager = JPAUtil.getEntityManagerFactory().createEntityManager();				
	
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(manager, Usuario.class);		

		// busca o usuário pelo nome
		Usuario usuario = usuarioDAO.buscar().comCriterio(Restrictions.eq("nomeUsuario", username)).um(); 						
		
		if (usuario == null) {
			throw new UsernameNotFoundException(username + " não encontrado");
		}
		
		return new UserDetailsDTO(usuario);
	}
}
