package br.com.nutriapp.controller.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.nutriapp.ejb.UsuarioRN;
import br.com.nutriapp.exception.SenhasNaoConferemException;
import br.com.nutriapp.exception.UsuarioJaCadastradoException;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroBean implements Serializable {	

	private static final long serialVersionUID = 5224777820210945920L;

	@Inject
	UsuarioRN usuarioRN;

	private Usuario usuario;

	private String dieta;
	
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
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public String getDieta() {
		return dieta;
	}

	public void setDieta(String dieta) {
		this.dieta = dieta;
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

	public String voltar(){
		return "/publico/principal?faces-redirect=true";
	}
}
