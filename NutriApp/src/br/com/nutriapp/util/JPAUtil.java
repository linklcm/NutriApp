package br.com.nutriapp.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	/**
	 * Busca o EntityManagerFactory a partir do JNDI que foi configurado no persistence.xml. Esse método é necessário
	 * quando o persistence.xml está configurado no projeto ear, visto que o método Persistence.createEntityManagerFactory("PU")
	 *  não encontra o persistence unit. 
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
