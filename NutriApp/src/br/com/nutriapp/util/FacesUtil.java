package br.com.nutriapp.util;

import java.net.URI;
import java.net.URISyntaxException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FacesUtil {
	public static void addMessage(Severity severityError, String mgs) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severityError,mgs,mgs));
	}	
	
	public static void addMultiLinesMessage(Severity severityError, String mgs) {		
		FacesContext fc = FacesContext.getCurrentInstance();
		
		String lineSeparator = "\n";
		for (String str : mgs.split(lineSeparator)) {
			if (!str.isEmpty()){
				FacesMessage fm = new FacesMessage(severityError, "", str);
				fc.addMessage("", fm);
			}
		}
	}
	
	/**
     * Get managed bean based on the bean name.
     *
     * @param beanName the bean name
     * @return the managed bean associated with the bean name
     */
	public static Object getManagedBean(String beanName) {

	     FacesContext fc = FacesContext.getCurrentInstance();
	     ELContext elc = fc.getELContext();
	     ExpressionFactory ef = fc.getApplication().getExpressionFactory();
	     ValueExpression ve = ef.createValueExpression(elc, getJsfEl(beanName), Object.class);
	     return ve.getValue(elc);
	}
	
	private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }
	
	public static String getApplicationUri() {
		  try {
		    FacesContext ctxt = FacesContext.getCurrentInstance();
		    ExternalContext ext = ctxt.getExternalContext();
		    URI uri = new URI(ext.getRequestScheme(),
		          null, ext.getRequestServerName(), ext.getRequestServerPort(),
		          ext.getRequestContextPath(), null, null);
		    return uri.toASCIIString();
		  } catch (URISyntaxException e) {
		    throw new FacesException(e);
		  }
		}
}
