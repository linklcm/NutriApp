package br.com.nutriapp.controller.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MenuBean {

	public String paginaPrincipal() {
		return "/publico/cadastro?faces-redirect=true";
	}
	
	public String paginaAlterarSenha() {
		return "/restrito/alterarsenha?faces-redirect=true";
	}

	public String usuarios() {
		return "/restrito/usuarios?faces-redirect=true";
	}
}