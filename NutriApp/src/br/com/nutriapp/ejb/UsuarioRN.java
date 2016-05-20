package br.com.nutriapp.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.criterion.Restrictions;

import br.com.nutriapp.exception.SenhaInvalidaException;
import br.com.nutriapp.exception.SenhasNaoConferemException;
import br.com.nutriapp.exception.UsuarioJaCadastradoException;
import br.com.nutriapp.model.dao.GenericDAO;
import br.com.nutriapp.model.entity.Permissao;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.util.GeralUtil;

@Local
@Stateless
public class UsuarioRN {	
			
	private GenericDAO<Usuario> usuarioDAO;
	
	@Inject
	private EntityManager entityManager;

	public Usuario buscarPorNome(String nome) {		
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		return usuarioDAO.buscar().comCriterio(Restrictions.eq("nome", nome)).um();
	}

	public List<Usuario> listar() {
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		return usuarioDAO.buscar().comOrdenadoAsc("nome").lista();
	}

	public Usuario findById(Long id) {
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		return usuarioDAO.carregar(id);
	}

	public void salvar(Usuario usuario, String senha, String senhaConfirmacao) throws Exception, 
		UsuarioJaCadastradoException, SenhasNaoConferemException {
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager, Usuario.class);
		
		if (!senha.equals(senhaConfirmacao)) {		
			throw new SenhasNaoConferemException();
		}
		
		Usuario usuarioJaExiste = usuarioDAO
				.buscar()
				.comCriterio(Restrictions.eq("nome", usuario.getNome()))
				.um();
		
		if (usuario.getId() == null) {
			if (usuarioJaExiste != null) throw new UsuarioJaCadastradoException();
			usuario.setAtivo(true);
		} else {
			if (usuarioJaExiste != null && !usuario.getId().equals(usuarioJaExiste.getId()))
				throw new UsuarioJaCadastradoException();
		}
		
		usuario.setSenha(GeralUtil.md5(senha));
		
		usuarioDAO.salvar(usuario);
	}
	
	public void novoUsuarioPadrao(Usuario usuario, String senha, String senhaConfirmacao) throws Exception,
		UsuarioJaCadastradoException, SenhasNaoConferemException {
		usuario.addPermissao(new Permissao(usuario, "ROLE_USUARIO"));
		this.salvar(usuario, senha, senhaConfirmacao);
	}
	
	public void ativar(Usuario usuario) throws Exception {
		//GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		usuario.setAtivo(true);
		usuarioDAO.salvar(usuario);
	}
	
	public void desativar(Usuario usuario) throws Exception {
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		usuario.setAtivo(false);
		usuarioDAO.salvar(usuario);
	}
	
	public Boolean verificaSenha(Usuario usuario, String senhaAtual) {
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		Usuario usuarioPersistido = usuarioDAO.carregar(usuario.getId());
		if (usuarioPersistido.getSenha().equals(GeralUtil.md5(senhaAtual))) {
			return true;
		}
		return false;
	}

	public void definirSenha(Usuario usuario, String senhaAtual, String novaSenha, String confirmaSenha) throws 
		Exception, SenhaInvalidaException, SenhasNaoConferemException {
		
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		
		if(!this.verificaSenha(usuario, senhaAtual)){
			throw new SenhaInvalidaException();
		}
		
		if (!novaSenha.equals(confirmaSenha)) {
			throw new SenhasNaoConferemException();
		}

		usuario.setSenha(GeralUtil.md5(novaSenha));

		usuarioDAO.salvar(usuario);
	}	
	
	public Usuario atualizar(Usuario usuario) throws Exception {
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		return usuarioDAO.salvar(usuario);
	}

	public void excluir(Usuario usuario) throws Exception {
		GenericDAO<Usuario> usuarioDAO = new GenericDAO<Usuario>(entityManager);
		usuarioDAO.excluir(usuario);
	}
}