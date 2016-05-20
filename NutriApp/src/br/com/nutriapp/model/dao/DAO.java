package br.com.nutriapp.model.dao;


public interface DAO<T extends Entity> {
	/**
	 * Alterar um registro faz um update no banco
	 * 
	 * @param	id	ID de um objeto a ser carregado
	 * @return	Retorna um objeto tipo T do banco (a classe T deve vir da classe que extender a classe {@link GenericDAO}})
	 * @see		GenericDAO
	 */ 
	T carregar(Long id);

	/**
	 * Excluir um registro apaga um objeto no banco
	 * 
	 * @param	entidade	Objeto a ser excluido
	 * @see		GenericDAO
	 */ 
	void excluir(T entidade);
	
	/**
	 * salvar um registro no banco
	 * 
	 * @param	entidade	Objeto a ser salvo
	 * @see		GenericDAO
	 */ 
	T salvar(T entidade) throws Exception;

	T referenciar(Long id);	
}
