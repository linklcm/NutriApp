package br.com.nutriapp.controller.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.nutriapp.ejb.DietaRN;
import br.com.nutriapp.ejb.UsuarioRN;
import br.com.nutriapp.model.entity.Dieta;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.util.FacesUtil;

@ManagedBean
@ViewScoped
public class EditarCadastroBean implements Serializable {	

	private static final long serialVersionUID = 5224777820210945920L;

	@Inject
	UsuarioRN usuarioRN;
	
	@Inject
	DietaRN dietaRN;

	private Usuario usuario;

	private List<Dieta> dietas;	
		
	@ManagedProperty("#{contextoBean}")
	private ContextoBean contextoBean;

	@PostConstruct
	public void iniciar() {		
		usuario = contextoBean.getUsuario();
		usuario = usuarioRN.findById(usuario.getId());
		dietas = dietaRN.listar();
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public List<Dieta> getDietas() {
		return dietas;
	}
		
	public void salvaUsuario() {
		
		try {
			usuarioRN.atualizar(usuario);
						
			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "Cadastro atualizado com sucesso!");		
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			e.printStackTrace();
		}
	}	

	public String voltar(){
		return "/publico/principal?faces-redirect=true";
	}
}
