package br.com.nutriapp.controller.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.nutriapp.ejb.DietaRN;
import br.com.nutriapp.ejb.UsuarioRN;
import br.com.nutriapp.exception.SenhasNaoConferemException;
import br.com.nutriapp.exception.UsuarioJaCadastradoException;
import br.com.nutriapp.model.entity.Dieta;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroBean implements Serializable {	

	private static final long serialVersionUID = 5224777820210945920L;

	@Inject
	UsuarioRN usuarioRN;
	
	@Inject
	DietaRN dietaRN;

	private Usuario usuario;

	private List<Dieta> dietas;	
	
	@NotNull(message="É necessário informar a senha")
	@Size(min=4, max=10, message = "A senha deve ter entre {min} e {max} caracteres.")
	private String senha;
	
	@NotNull(message="Informe a senha novamente para confirmação.")
	@Size(min=4, max=10, message = "A senha deve ter entre {min} e {max} caracteres.")
	private String senhaConfirmacao;

	@PostConstruct
	public void iniciar() {		
		dietas = dietaRN.listar();

		usuario = new Usuario();
	}	

	public Usuario getUsuario() {
		return usuario;
	}
	
	public List<Dieta> getDietas() {
		return dietas;
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
	
	public void salvaUsuario() {
		
		try {
			usuarioRN.novoUsuarioPadrao(usuario, senha, senhaConfirmacao);
			
			usuario = new Usuario();
			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "Cadastro efetuado com sucesso!");
		} catch (SenhasNaoConferemException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
		} catch (UsuarioJaCadastradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			e.printStackTrace();
		}
	}	

	public String voltar(){
		return "/publico/principal?faces-redirect=true";
	}
}
