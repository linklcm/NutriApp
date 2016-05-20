package br.com.nutriapp.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	/**
	 * Busca o EntityManagerFactory a partir do JNDI que foi configurado no persistence.xml. Esse m�todo � necess�rio
	 * quando o persistence.xml est� configurado no projeto ear, visto que o m�todo Persistence.createEntityManagerFactory("PU")
	 *  n�o encontra o persistence unit. 
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory(){
		/*Context ctx;
		EntityManagerFactory factory = null;		
		try {
			ctx = new InitialContext();
			factory = (EntityManagerFactory)ctx.lookup("java:jboss/PUNutriApp");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}*/
		return Persistence.createEntityManagerFactory("PU");
	}
}
