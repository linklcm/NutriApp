package br.com.nutriapp.controller.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.com.nutriapp.ejb.DietaRN;
import br.com.nutriapp.model.entity.Dieta;
import br.com.nutriapp.util.FacesUtil;

@ManagedBean
@ViewScoped
public class DietaBean implements Serializable {			
	private static final long serialVersionUID = -256366375673512473L;
	
	private List<Dieta> dietas;
	private Dieta dieta;
	
	@Inject
	DietaRN dietaRN;
	
	@PostConstruct
	public void iniciar(){
		// deixa a entrada passada como selecionado na página 
		dieta = (Dieta)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dieta");
		
	}
	
	public Dieta getDieta() {
		return dieta;
	}

	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}

	public List<Dieta> getDietas() {
		if (dietas == null) {
			dietas = dietaRN.listar();
		}
		return dietas;
	}

	public String novo() {
		return "/restrito/cadastro-dieta?faces-redirect=true";
	}
	
	public String alterar() {
		if (dieta == null) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "Selecione a dieta!");
			return "";
		}
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dieta", dieta);
		
		return "/restrito/cadastro-dieta?faces-redirect=true";
	}
	
	public void excluir() {
		try {
			dietaRN.excluir(dieta);
			dietas = null;
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			e.printStackTrace();
		}
	}
}
