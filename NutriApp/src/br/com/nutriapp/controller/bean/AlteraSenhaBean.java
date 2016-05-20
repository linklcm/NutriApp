package br.com.nutriapp.controller.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import br.com.nutriapp.ejb.UsuarioRN;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.util.GeralUtil;

@ManagedBean(name = "alteraSenhaBean")
@RequestScoped
public class AlteraSenhaBean {
	private String senhaAtual;
	private String novaSenha;
	private String confirmaSenha;
	private Usuario usuarioSelecionado;
	
	private UsuarioRN usuarioRN;

	@ManagedProperty("#{contextoBean}")
	private ContextoBean contextoBean;

	@PostConstruct
	public void iniciar() {
		
		usuarioRN = new UsuarioRN();
		usuarioSelecionado = contextoBean.getUsuario();
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String senha) {
		this.novaSenha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public String alterarSenha() {

		if (!this.usuarioRN.verificaSenha(usuarioSelecionado, senhaAtual)) {
			GeralUtil.addMensagem(FacesMessage.SEVERITY_WARN, "Atenção, senha atual inválida!!");
			return "alterasenha";
		}

		if (novaSenha.equals(confirmaSenha)) {
			try {
				usuarioRN.definirSenha(usuarioSelecionado, senhaAtual, novaSenha, confirmaSenha);
				GeralUtil.addMensagem(FacesMessage.SEVERITY_INFO, "Seja Bem vindo, senha alterada com sucesso!");
				
				return "/restrito/principal?faces-redirect=true";
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		} else {

			GeralUtil.addMensagem(FacesMessage.SEVERITY_WARN, "Atenção, a confirmação da senha deve ser igual a senha!");
			return "restrito/alterasenha?faces-redirect=true";
		}

	}

}
