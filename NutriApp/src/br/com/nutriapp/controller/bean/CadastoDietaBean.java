package br.com.nutriapp.controller.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import br.com.nutriapp.ejb.DietaRN;
import br.com.nutriapp.exception.UsuarioJaCadastradoException;
import br.com.nutriapp.model.entity.Alimento;
import br.com.nutriapp.model.entity.Dieta;
import br.com.nutriapp.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastoDietaBean implements Serializable {	

	private static final long serialVersionUID = -4750469943200567996L;

	@Inject
	DietaRN dietaRN;

	private Dieta dieta;
	
	private String nomeAlimento;
	private Alimento alimento;
	
	private List<Alimento> cafeDaManha = new ArrayList<Alimento>(); 
	
	@PostConstruct
	public void iniciar() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		// deixa a entrada passada como selecionado na página 
		dieta = (Dieta)flash.get("dieta");

		if(dieta != null){
			//recarega para não problema com sessão.
			dieta = dietaRN.findById(dieta.getId());
		} else {
			dieta = new Dieta();
		}
	}
	
	public Dieta getDieta() {
		return dieta;
	}

	public String getNomeAlimento() {
		return nomeAlimento;
	}

	public void setNomeAlimento(String nomeAlimento) {
		this.nomeAlimento = nomeAlimento;
	}

	public Alimento getAlimento() {
		return alimento;
	}

	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	}

	public List<Alimento> getCafeDaManha() {
		return cafeDaManha;
	}

	public String salvar() {
		
		try {
			dietaRN.salvar(dieta);
			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dieta", dieta);
			return "/restrito/usuarios?faces-redirect=true";		
		} catch (UsuarioJaCadastradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			return "";
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public void adicionaAlimento() {
		/*if (role == null) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "Informe o Perfil!");
			return;
		}*/
		
		/*for (Permissao per : usuario.getPermissoes()) {
			if (per.getPermissao().equals(role.name())) return;
		}
		
		this.usuario.addPermissao(new Permissao(role.name()));
		this.role = null;*/
	}

	public void excluirAlimento() {
		//usuario.getPermissoes().remove(permissao);
	}

	public String voltar(){
		return "/restrito/dietas?faces-redirect=true";
	}
}
