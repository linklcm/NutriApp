package br.com.nutriapp.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.criterion.Restrictions;

import br.com.nutriapp.exception.DietaJaCadastradoException;
import br.com.nutriapp.model.dao.GenericDAO;
import br.com.nutriapp.model.entity.Dieta;

@Local
@Stateless
public class DietaRN {
	
	@Inject
	private EntityManager entityManager;
	
	public List<Dieta> listar() {
		GenericDAO<Dieta> dietaDAO = new GenericDAO<Dieta>(entityManager, Dieta.class);
		return dietaDAO.buscar().comOrdenadoAsc("nome").lista();
	}

	public Dieta findById(Long id) {
		GenericDAO<Dieta> dietaDAO = new GenericDAO<Dieta>(entityManager, Dieta.class);
		return dietaDAO.carregar(id);
	}

	public void salvar(Dieta dieta) throws Exception, DietaJaCadastradoException {
		GenericDAO<Dieta> dietaDAO = new GenericDAO<Dieta>(entityManager, Dieta.class);
				
		Dieta dietaJaExiste = dietaDAO
				.buscar()
				.comCriterio(Restrictions.eq("nome", dieta.getNome()))
				.um();
		
		if (dieta.getId() == null) {
			if (dietaJaExiste != null) throw new DietaJaCadastradoException();
		} else {
			if (dietaJaExiste != null && !dieta.getId().equals(dietaJaExiste.getId()))
				throw new DietaJaCadastradoException();
		}
		
		dietaDAO.salvar(dieta);
	}
	
	public void excluir(Dieta dieta) throws Exception {
		GenericDAO<Dieta> dietaDAO = new GenericDAO<Dieta>(entityManager, Dieta.class);
		dietaDAO.excluir(dieta);
	}
}