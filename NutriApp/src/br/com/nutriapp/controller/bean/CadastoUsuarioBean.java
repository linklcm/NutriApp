package br.com.nutriapp.controller.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.nutriapp.ejb.UsuarioRN;
import br.com.nutriapp.exception.SenhasNaoConferemException;
import br.com.nutriapp.exception.UsuarioJaCadastradoException;
import br.com.nutriapp.model.entity.Permissao;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.util.FacesUtil;
import br.com.nutriapp.util.Role;

@ManagedBean(name = "cadastroUsuarioBean")
@ViewScoped
public class CadastoUsuarioBean implements Serializable {	

	private static final long serialVersionUID = 5224777820210945920L;

	@EJB
	private UsuarioRN usuarioRN;

	private Usuario usuario;

	private List<Role> permissoes = Arrays.asList(Role.values());
	private Role role;
	private Permissao permissao;
	
	@NotNull(message="É necessário informar a senha")
	@Size(min=4, max=10, message = "A senha deve ter entre {min} e {max} caracteres.")
	private String senha;
	
	@NotNull(message="Informe a senha novamente para confirmação.")
	@Size(min=4, max=10, message = "A senha deve ter entre {min} e {max} caracteres.")
	private String senhaConfirmacao;

	@PostConstruct
	public void iniciar() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		// deixa a entrada passada como selecionado na página 
		usuario = (Usuario)flash.get("usuarioSelecionado");

		if(usuario != null){
			//recarega para não problema com sessão.
			usuario = usuarioRN.findById(usuario.getId());
			senhaConfirmacao = usuario.getSenha();
		} else {
			usuario = new Usuario();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Role> getPermissoes() {
		return permissoes;
	}

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public String salvaUsuario() {
		
		try {
			usuarioRN.salvar(usuario, senha, senhaConfirmacao);
			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuarioSelecionado", usuario);
			return "/restrito/usuarios?faces-redirect=true";
		} catch (SenhasNaoConferemException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			return "";
		} catch (UsuarioJaCadastradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			return "";
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public void adicionaRole() {
		if (role == null) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "Informe o Perfil!");
			return;
		}
		
		for (Permissao per : usuario.getPermissoes()) {
			if (per.getPermissao().equals(role.name())) return;
		}
		
		this.usuario.addPermissao(new Permissao(role.name()));
		this.role = null;
	}

	public void excluirPermissao() {
		usuario.getPermissoes().remove(permissao);
	}

	public String voltar(){
		return "/restrito/usuarios?faces-redirect=true";
	}
}
