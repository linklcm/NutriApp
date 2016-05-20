package br.com.nutriapp.model.dao;

import java.io.Serializable;

/**
 * Classe básica para criação das Entity's.
 */
public interface Entity {
	/**
	 * @return o Id da Entity.
	 */
	public Serializable getId();
}
