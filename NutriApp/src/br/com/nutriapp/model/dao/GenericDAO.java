package br.com.nutriapp.model.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Implementação do DAO genérico. Tratar as exceções diretamente na classe RN
 * 
 * @param <T>
 *            Entidade que será persistida
 * @param <ID>
 *            Tipo do ID da entidade que será persistida
 */
public class GenericDAO<T extends Entity> implements DAO<T> {
	protected Criteria c;
	private Class<T> persistentClass;
	private EntityManager em;

	/**
	 * Construtor vai setar automaticamente via Refrection qual classe esta
	 * sendo tratada.
	 */
	public GenericDAO(EntityManager em) {
		this.em = em;
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.persistentClass = clazz;
	}

	@SuppressWarnings("unchecked")
	public GenericDAO(EntityManager em, @SuppressWarnings("rawtypes") Class persistentClass) {
		this.em = em;
		this.persistentClass = persistentClass;
	}

	/**
	 * Associação com um membro do objeto buscado
	 * 
	 * @param propertyName
	 *            Atributo presente no objeto
	 * @param alias
	 *            Referencia para ser usado em um criterio
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> associado(String propertyName, String alias) {
		c.createAlias(propertyName, alias);
		return this;
	}

	/**
	 * Associação com um membro do objeto buscado
	 * 
	 * @param propertyName
	 *            Atributo presente no objeto
	 * @param alias
	 *            Referencia para ser usado em um criterio
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	@SuppressWarnings("deprecation")
	public GenericDAO<T> associadoLeft(String propertyName, String alias) {
		c.createAlias(propertyName, alias, Criteria.LEFT_JOIN);
		return this;
	}

	/**
	 * Carrega as informações de relacionamentos lazy, utilizando o join fech;
	 * 
	 * @param propertyName
	 *            Atributo presente no objeto
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> com(String propertyName, String alias) {
		c.createAlias(propertyName, alias);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setFetchMode(propertyName, FetchMode.JOIN);
		return this;
	}

	/**
	 * Começa uma busca de critério com a classe de persistencia
	 * <p>
	 * Exemplo complexo de uso:
	 * </p>
	 * 
	 * <pre>
	 * return ClassePrimariaDAO.buscar().associado(&quot;ClasseMembro&quot;, &quot;cm&quot;).comOrdenadoAsc(&quot;cm.nome&quot;)
	 * 		.comOrdenadoAsc(&quot;nome&quot;).distintos().lista();
	 * </pre>
	 * <p>
	 * O exemplo acima resulta numa query hql aproximadamente igual a esta:
	 * </p>
	 * 
	 * <pre>
	 * SELECT DISTINCT cp ClassePrimaria cp ORDER BY cp.classeMembro.nome, cp.nome;
	 * </pre>
	 * 
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> buscar() {
		c = getSession().createCriteria(persistentClass);
		return this;
	}

	@Override	
	public T carregar(Long id) {
		return (T) this.em.find(persistentClass, id);
	}
	
	@Override	
	public T referenciar(Long id) {
		return (T) this.em.getReference(persistentClass, id);
	}


	/**
	 * Adiciona um criterio na busca, os criterios recomendados são:
	 * <ul>
	 * <li>Restrictions.eq(campo, valor) - igual</li>
	 * <li>Restrictions.lt(campo, valor) - menor</li>
	 * <li>Restrictions.le(campo, valor) - menor ou igual</li>
	 * <li>Restrictions.gt(campo, valor) - maior</li>
	 * <li>Restrictions.ge(campo, valor) - maior ou igual</li>
	 * <li>Restrictions.like(campo, referencia) - like (a referencia deve vir
	 * com as % ja concatenadas</li>
	 * <li>Restrictions.between(campo, start, end) - entre valores</li>
	 * <li>Restrictions.isNotNull(campo) - não nulo</li>
	 * </ul>
	 * 
	 * @param criterio
	 *            Deve receber critérios criados com {@link Restrictions}
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> comCriterio(Criterion criterio) {
		c.add(criterio);
		return this;
	}

	/**
	 * Buscar com um objeto de exemplo
	 * <p>
	 * Objeto deve ser montado com outros parametros como nulo facilitando a
	 * procura com base em alguns requistos
	 * </p>
	 * 
	 * @param example
	 *            Objeto de exemplo para usar como referencia de busca
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> comExemplo(T example) {
		c.add(Example.create(example).enableLike(MatchMode.ANYWHERE).ignoreCase());
		return this;
	}

	/**
	 * Ordenar a busca de forma ascendente baseada num atributo da classe
	 * persistente
	 * 
	 * @param propertyName
	 *            Atributo a ser ordenado de forma ascendente
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> comOrdenadoAsc(String propertyName) {
		c.addOrder(Order.asc(propertyName));
		return this;
	}

	public GenericDAO<T> comMaximoResultado(Integer propertyName) {
		c.setMaxResults(propertyName);
		return this;
	}

	/**
	 * Ordenar a busca de forma descendente baseada num atributo da classe
	 * persistente
	 * 
	 * @param propertyName
	 *            Atributo a ser ordenado de forma descendente
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> comOrdenadoDesc(String propertyName) {
		c.addOrder(Order.desc(propertyName));
		return this;
	}

	/**
	 * Buscar com paginação entre primeiro e quantidade de resultados
	 * 
	 * @param first
	 *            Número da primeira linha
	 * @param max
	 *            Quantidade de registros a serem buscados
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> comResultados(int first, int max) {
		c.setFirstResult(first);
		c.setMaxResults(max);
		return this;
	}

	/**
	 * Contar quantos itens tem em uma busca
	 * 
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */	
	public Long contar() {
		c.setProjection(Projections.rowCount());
		return (Long) c.uniqueResult();
	}

	/**
	 * Força para trazer apenas objetos distintos da classe persistida
	 * 
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> distintos() {
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this;
	}

	@Override
	public void excluir(T entidade) {
		em.joinTransaction(); // necessita, devido ao EntityManager estar sendo
								// criado via CDI
		em.remove(em.getReference(entidade.getClass(), entidade.getId()));
	}

	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Session getSession() {
		return em.unwrap(Session.class);
	}

	/**
	 * Restringir a busca com um atributo buscando uma cadeira de caracteres
	 * especifica:
	 * <ul>
	 * <li>LIKE 'A%' - Todas as palavras que iniciem com a letra A;</li>
	 * <li>LIKE '%A' - Todas que terminem com a letra A;</li>
	 * <li>LIKE '%A%' - Todas que tenham a letra A em qualquer posição;</li>
	 * <li>LIKE 'A_' - String de dois caracteres que tenham a primeira letra A e
	 * o segundo caractere seja qualquer outro;</li>
	 * <li>LIKE '_A' - String de dois caracteres cujo primeiro caractere seja
	 * qualquer um e a última letra seja A;</li>
	 * <li>LIKE '_A_' - String de três caracteres cuja segunda letra seja A,
	 * independentemente do primeiro ou do último caractere;</li>
	 * <li>LIKE '%A_' - Todos que tenham a letra A na panúltima posição e a
	 * última seja qualquer outro caractere;</li>
	 * <li>LIKE '_A%' - Todos que tanham a letra A na segunda posição e o
	 * primeiro caractere seja qualquer um;</li>
	 * <li>Caso queira realizar uma busca onde o caracter pesquisado seja por
	 * exemplo o (_) usa-se o caracter de escape \.</li>
	 * </ul>
	 * 
	 * @param propertyName
	 *            Atributo a ser verificado com a referencia LIKE
	 * @param reference
	 *            Referencia de busca para LIKE
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> like(String propertyName, String reference) {
		c.add(Restrictions.like(propertyName, reference));
		return this;
	}

	/**
	 * Trazer uma lista de objetos com base na busca
	 * 
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	@SuppressWarnings("unchecked")
	public List<T> lista() {
		return c.list();
	}

	/**
	 * Restringindo a busca para não trazer campos que estejam nulos no banco de
	 * dados
	 * 
	 * @param propertyName
	 *            Atributo a ser verificado como NOT NULL
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	public GenericDAO<T> naoNulo(String propertyName) {
		c.add(Restrictions.isNotNull(propertyName));
		return this;
	}

	@Override
	public T salvar(T entidade) throws Exception {
		try {
			em.joinTransaction(); // necessita, devido ao EntityManager estar sendo criado via CDI
			//Verifica se a Entity já existe para fazer Merge.
			if (entidade.getId() != null) {
				//Verifica se a Entity não está gerenciavel pelo EntityManager.
				if (!this.em.contains(entidade)) {
					//Busca a Entity da base de dados, baseado no Id.
					if (this.em.find(entidade.getClass(), entidade.getId()) == null) {
						throw new Exception("Objeto não existe!");
					}
				}
				return this.em.merge(entidade);
			} else { // Se a Entity não existir persiste ela.
				this.em.persist(entidade); // persist
			}
			//Retorna a Entity persistida.
			return entidade;
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw pe;
		}
	}

	/**
	 * Trazer apenas um objeto com base na busca
	 * 
	 * @return DAOImpl Retorna o proprio DOA para criar uma interface fluente
	 */
	@SuppressWarnings("unchecked")
	public T um() {
		return (T) c.uniqueResult();
	}

	/**
	 * Retornar uma Query mediante um hql
	 * 
	 * @param hql
	 *            String HQL para criar a Query
	 * @return Query retorna uma Query para executar hql diferenciados
	 */
	public javax.persistence.Query queryHQL(String hql) {
		return em.createQuery(hql);
	}
}