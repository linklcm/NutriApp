package br.com.nutriapp.controller.bean;

import java.io.Serializable;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import br.com.nutriapp.ejb.UsuarioRN;
import br.com.nutriapp.model.entity.Usuario;
import br.com.nutriapp.security.SecurityUtil;
import br.com.nutriapp.security.UserDetailsDTO;

@ManagedBean(name = "contextoBean")
@SessionScoped
public class ContextoBean implements Serializable {
	
	private static final long serialVersionUID = -480192591596208705L;
	private Usuario usuario;
	
	private TimeZone timeZone = TimeZone.getDefault();
	
	/*@Inject
	UsuarioRN usuarioRN;*/
	
	@PostConstruct
	public void init() {
		Usuario usuario = new Usuario("linklcm", "linklcm@gmail.com", "linklcm");
		 try {
			 UsuarioRN usuarioRN = new UsuarioRN();
			 usuarioRN.salvar(usuario, "link", "link");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	public Usuario getUsuario() {
		if (usuario == null){
			UserDetailsDTO user = SecurityUtil.getUserCredential();
			if (user != null )
				this.usuario = user.getUsuario();
		}
		return usuario;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
}
