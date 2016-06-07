package br.com.nutriapp.controller.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import br.com.nutriapp.model.entity.Refeicao;
import br.com.nutriapp.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroDietaBean implements Serializable {	

	private static final long serialVersionUID = -4750469943200567996L;

	@Inject
	DietaRN dietaRN;

	private Dieta dieta;
	
	private String nomeAlimento;
	private Alimento alimento;
	
	private Refeicao cafeDaManha;
	private Refeicao lancheDaManha;
	private Refeicao almoco;
	private Refeicao lancheDaTarde;
	private Refeicao jantar;
	private Refeicao lancheDaNoite;
	
	@PostConstruct
	public void iniciar() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		// deixa a entrada passada como selecionado na página 
		dieta = (Dieta)flash.get("dieta");

		if(dieta != null){
			//recarega para não problema com sessão.
			dieta = dietaRN.findById(dieta.getId());
			
			cafeDaManha = dieta.getRefeicao("Café da Manhã");
			lancheDaManha = dieta.getRefeicao("Lanche da Manhã");
			almoco = dieta.getRefeicao("Almoço");
			lancheDaTarde = dieta.getRefeicao("Lanche da Tarde");
			jantar = dieta.getRefeicao("Jantar");
			lancheDaNoite = dieta.getRefeicao("Lanche da Noite");
			/*for (Refeicao refeicao : dieta.getRefeicoes()) {
				if (refeicao.getNome().equals("Café da Manhã")) {
					cafeDaManha = refeicao;
				} else if (refeicao.getNome().equals("Lanche da Manhã")) {
					lancheDaManha = refeicao;
				} else if (refeicao.getNome().equals("Almoço")) {
					almoco = refeicao;
				} else if (refeicao.getNome().equals("Lanche da Tarde")) {
					lancheDaTarde = refeicao;
				}else if (refeicao.getNome().equals("Jantar")) {
					jantar = refeicao;
				}else if (refeicao.getNome().equals("Lanche da Noite")) {
					lancheDaNoite = refeicao;
				}
			}*/
		} else {
			dieta = new Dieta();
			
			Date seteHoras = null;
			Date dezHoras = null;
			Date meioDiaMeio = null;
			Date quinzeHorasMeia = null;
			Date dezoitoHorasMeia = null;
			Date vinteDuasHoras = null;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
				seteHoras = dateFormat.parse("07:00");
				dezHoras = dateFormat.parse("10:00");
				meioDiaMeio = dateFormat.parse("12:30");
				quinzeHorasMeia = dateFormat.parse("15:30");
				dezoitoHorasMeia = dateFormat.parse("18:30");
				vinteDuasHoras = dateFormat.parse("22:00");
			} catch (ParseException e) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro ao converter a hora");
				e.printStackTrace();
			}
			cafeDaManha = new Refeicao("Café da Manhã", seteHoras);
			lancheDaManha = new Refeicao("Lanche da Manhã", dezHoras);
			almoco = new Refeicao("Almoço", meioDiaMeio);
			lancheDaTarde = new Refeicao("Lanche da Tarde", quinzeHorasMeia);
			jantar = new Refeicao("Jantar", dezoitoHorasMeia);
			lancheDaNoite = new Refeicao("Lanche da Noite", vinteDuasHoras);
			
			dieta.addRefeicao(cafeDaManha);
			dieta.addRefeicao(lancheDaManha);
			dieta.addRefeicao(almoco);
			dieta.addRefeicao(lancheDaTarde);
			dieta.addRefeicao(jantar);
			dieta.addRefeicao(lancheDaNoite);
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

	public Refeicao getCafeDaManha() {
		return cafeDaManha;
	}

	public Refeicao getLancheDaManha() {
		return lancheDaManha;
	}

	public Refeicao getLancheDaTarde() {
		return lancheDaTarde;
	}

	public Refeicao getJantar() {
		return jantar;
	}

	public Refeicao getLancheDaNoite() {
		return lancheDaNoite;
	}

	public Refeicao getAlmoco() {
		return almoco;
	}

	public String salvar() {
		
		if (cafeDaManha.getAlimentos().isEmpty()) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Informe os alimentos do Café da Manhã");
			return "";
		}
		
		if (lancheDaManha.getAlimentos().isEmpty()) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Informe os alimentos do Lanche da Manhã");
			return "";
		}
		
		if (almoco.getAlimentos().isEmpty()) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Informe os alimentos do Almoço");
			return "";
		}
		
		if (lancheDaTarde.getAlimentos().isEmpty()) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Informe os alimentos do Lanche da Tarde");
			return "";
		}
		
		if (jantar.getAlimentos().isEmpty()) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Informe os alimentos do Jantar");
			return "";
		}
		
		if (lancheDaNoite.getAlimentos().isEmpty()) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Informe os alimentos do Lanche da Noite");
			return "";
		}
		
		try {
			dietaRN.salvar(dieta);
			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dieta", dieta);
			return "/restrito/dietas?faces-redirect=true";		
		} catch (UsuarioJaCadastradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			return "";
		} catch (Exception e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "" + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public void addCafeDaManha() {
		nomeAlimento = nomeAlimento.trim();
		for (Alimento alimento : cafeDaManha.getAlimentos()) {
			if (alimento.getNome().toUpperCase().equals(nomeAlimento.toUpperCase())) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Alimento já adicionado para o café da manhã");
				return;
			}
		}
		
		cafeDaManha.addAlimento(nomeAlimento);
				
		this.nomeAlimento = null;
	}
	
	public void addLancheDaManha() {
		nomeAlimento = nomeAlimento.trim();
		for (Alimento alimento : lancheDaManha.getAlimentos()) {
			if (alimento.getNome().toUpperCase().equals(nomeAlimento.toUpperCase())) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Alimento já adicionado para o lanche da manhã");
				return;
			}
		}
		
		lancheDaManha.addAlimento(nomeAlimento);
				
		this.nomeAlimento = null;
	}
	
	public void addAlmoco() {
		nomeAlimento = nomeAlimento.trim();
		for (Alimento alimento : almoco.getAlimentos()) {
			if (alimento.getNome().toUpperCase().equals(nomeAlimento.toUpperCase())) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Alimento já adicionado para o almoço");
				return;
			}
		}
		
		almoco.addAlimento(nomeAlimento);
				
		this.nomeAlimento = null;
	}
	
	public void addLancheDaTarde() {
		nomeAlimento = nomeAlimento.trim();
		for (Alimento alimento : lancheDaTarde.getAlimentos()) {
			if (alimento.getNome().toUpperCase().equals(nomeAlimento.toUpperCase())) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Alimento já adicionado para o lanche da tarde");
				return;
			}
		}
		
		lancheDaTarde.addAlimento(nomeAlimento);
				
		this.nomeAlimento = null;
	}
	
	public void addJantar() {
		nomeAlimento = nomeAlimento.trim();
		for (Alimento alimento : jantar.getAlimentos()) {
			if (alimento.getNome().toUpperCase().equals(nomeAlimento.toUpperCase())) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Alimento já adicionado para o jantar");
				return;
			}
		}
		
		jantar.addAlimento(nomeAlimento);
				
		this.nomeAlimento = null;
	}
	
	public void addLancheDaNoite() {
		nomeAlimento = nomeAlimento.trim();
		for (Alimento alimento : lancheDaNoite.getAlimentos()) {
			if (alimento.getNome().toUpperCase().equals(nomeAlimento.toUpperCase())) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Alimento já adicionado para o lanche da noite");
				return;
			}
		}
		
		lancheDaNoite.addAlimento(nomeAlimento);
				
		this.nomeAlimento = null;
	}

	public void excluirAlimento() {
		alimento.getRefeicao().getAlimentos().remove(alimento);
	}

	public String voltar(){
		return "/restrito/dietas?faces-redirect=true";
	}
}
