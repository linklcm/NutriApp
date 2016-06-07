package br.com.nutriapp.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable, br.com.nutriapp.model.dao.Entity {
	
	private static final long serialVersionUID = -3796267044549335980L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Temporal(TemporalType.TIMESTAMP)
	private Date cadastro;	
	
	@NotNull(message = "É necessário informar o nome do usuário")
	@Size(min = 2, max = 50, message = "O nome do Usuário do Sistema deve ter entre {min} e {max} caracteres.")	
	private String nome;
	
	@Email(message="E-mail inválido")
	@NotNull(message="É necessário informar o e-mail")
	private String email;
	
	@NotNull(message="É necessário informar a senha")
	private String senha;

	@OneToMany(mappedBy = "usuario", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true) 
	private List<Permissao> permissoes = new ArrayList<Permissao>();
	
	@ManyToOne
	@JoinColumn(name="fk_dieta")
	private Dieta dieta;

	@NotNull
	private Boolean ativo = true;

	@Column(name = "data_fim")
	@Temporal(TemporalType.DATE)
	private Date dataFim;		

	public Usuario() {
		
	}

	public Usuario(String nome, String email, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public Date getCadastro() {
		return cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	
	public void addPermissao(Permissao permissao) {
		permissao.setUsuario(this);
		this.permissoes.add(permissao);
	}

	public Dieta getDieta() {
		return dieta;
	}

	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public String getPrimeiroNome(){
		return this.nome.split(" ")[0];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}	
}