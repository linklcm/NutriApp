package br.com.nutriapp.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dietas")
public class Dieta implements Serializable, br.com.nutriapp.model.dao.Entity {
	
	private static final long serialVersionUID = 1968071948118756855L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "É necessário informar o nome da dieta")
	private String nome;
	
	@OneToMany(mappedBy = "dieta", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true) 
	private List<Refeicao> refeicoes = new ArrayList<Refeicao>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Refeicao> getRefeicoes() {
		return refeicoes;
	}

	public void setRefeicoes(List<Refeicao> refeicoes) {
		this.refeicoes = refeicoes;
	}
	
	public void addRefeicao(Refeicao refeicao) {
		refeicao.setDieta(this);
		this.refeicoes.add(refeicao);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Dieta other = (Dieta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Refeicao getRefeicao(String nome) {
		for (Refeicao refeicao : this.refeicoes) {
			if (refeicao.getNome().equals(nome)) {
				return refeicao;
			}
		}
		return null;
	}
}