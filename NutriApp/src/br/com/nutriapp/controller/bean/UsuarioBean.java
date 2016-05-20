package br.com.nutriapp.controller.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.nutriapp.ejb.UsuarioRN;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.util.FacesUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {			
	private static final long serialVersionUID = -256366375673512473L;
	
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private Usuario selectedUsuario;
		
	private UsuarioRN usuarioRN;
	
	@PostConstruct
	public void iniciar(){
		usuarioRN = new UsuarioRN();
		
		// deixa a entrada passada como selecionado na página 
		selectedUsuario = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("usuarioSelecionado");
		
	}
	
	public Usuario getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(Usuario selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}	

	public List<Usuario> getUsuarios() {
		if(usuarios.isEmpty()){			
			usuarios = usuarioRN.listar();
		}
		return usuarios;
	}	
	
	public String novoUsuario() {
		return "/restrito/cadastro-usuario?faces-redirect=true";
	}
	
	public String alterarUsuario() {
		if (selectedUsuario == null) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "Selecione o usuário!");
			return"";
		}
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuarioSelecionado", selectedUsuario);
		
		return "/restrito/cadastro-usuario?faces-redirect=true";
	}
	
	public void excluir() {
		try {
			usuarioRN.excluir(selectedUsuario);
			usuarios = null;
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void ativar() {
		try {			
			usuarioRN.ativar(selectedUsuario);
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void desativar() {
		try {
			usuarioRN.desativar(selectedUsuario);
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			e.printStackTrace();
		}
	}
}
