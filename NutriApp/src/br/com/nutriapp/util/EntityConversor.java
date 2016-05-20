package br.com.nutriapp.util;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.nutriapp.model.dao.Entity;

/**
 * Converter para entidades JPA. Baseia-se nas anotações @Id e @EmbeddedId para
 * identificar o atributo que representa a identidade da entidade. Também
 * as anotações nas super classes.
 *
 * TODO: Lembrar sempre de adicionar nas dependencias o trugger-2.8.jar
 */
@FacesConverter("entityConversor")
public class EntityConversor implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {
		if (value != null && value.trim().length() > 0) {
			Object obj = component.getAttributes().get(value);
			return obj;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object obj) {
		if (obj != null && !"".equals(obj)) {
			String id;
		
				id = this.getId(obj);
				if (id == null) {
					id = "";
				}
				id = id.trim();
				component.getAttributes().put(id,
						getClazz(ctx, component).cast(obj));
				return id;
		}
		return null;
	}

	/**
	 * Obtém, via expression language, a classe do objeto
	 * 
	 * @param FacesContext
	 *            facesContext
	 * 
	 * @param UICompoment
	 *            compoment
	 * 
	 * @return Class<?>
	 */
	private Class<?> getClazz(FacesContext facesContext, UIComponent component) {
		return component.getValueExpression("value").getType(
				facesContext.getELContext());
	}

	/**
	 * Retorna a representação em String do retorno do método anotado com @Id ou @EmbeddedId
	 * do objeto.
	 * 
	 * @param Object obj
	 * 
	 * @return String
	 */
	public String getId(Object obj) {
		Entity entity = (Entity)obj;
		Object idValue = entity.getId();
		return String.valueOf(idValue);
	}
}
