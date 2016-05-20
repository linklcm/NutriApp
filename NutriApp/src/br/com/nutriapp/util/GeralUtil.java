package br.com.nutriapp.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/*import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
*/
//import com.sun.org.apache.xml.internal.security.utils.Base64;

public class GeralUtil {
	/*
	 * public static int diasEntre(Date dataInicial, Date dataFinal){ return
	 * diasEntre(new DateTime(dataInicial), new DateTime(dataFinal)); }
	 * 
	 * public static int diasEntre(DateTime dataInicial, DateTime dataFinal){
	 * int dias = Days.daysBetween(dataInicial, dataFinal).getDays();
	 * 
	 * return dias; }
	 * 
	 * public static int semanasEntre(Date dataInicial, Date dataFinal){ return
	 * semanasEntre(new DateTime(dataInicial), new DateTime(dataFinal)); }
	 * 
	 * public static int semanasEntre(DateTime dataInicial, DateTime dataFinal){
	 * int semanas = Weeks.weeksBetween(dataInicial, dataFinal).getWeeks();
	 * 
	 * DateTime dtInicial = dataInicial.plus(Period.weeks(semanas));
	 * 
	 * int dias = Days.daysBetween(dtInicial, dataFinal).getDays();
	 * 
	 * if (dias > 0){ semanas++; }
	 * 
	 * return semanas; }
	 */

	public static Double arredondamento(Double valor) {
		valor = new BigDecimal(valor).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		return valor;
	}

	/**
	 * Mascara texto com formatacao monetaria
	 * 
	 * @param valor
	 *            Valor a ser mascarado
	 * @param moeda
	 *            Padrao monetario a ser usado
	 * @return Valor mascarado de acordo com o padrao especificado
	 */
	public static String fmtValor(double valor) {

		final Locale locale = new Locale("pt", "BR");
		final DecimalFormatSymbols moeda = new DecimalFormatSymbols(locale);
		final DecimalFormat formato = new DecimalFormat("¤ ###,###,##0.00", moeda);

		return formato.format(valor);
	}

	public static Date adicionarDias(Date date, int dias) {
		Locale locale = new Locale("pt", "BR");
		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dias);
		return calendar.getTime();
	}

	public static Date dataAtual() {
		Locale locale = new Locale("pt", "BR");
		@SuppressWarnings("unused")
		Calendar calendar = Calendar.getInstance(locale);
		return Calendar.getInstance().getTime();
	}

	public static Date zerarHora(Date date) {
		Locale locale = new Locale("pt", "BR");
		Calendar c = Calendar.getInstance(locale);
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.getMinimum(Calendar.HOUR),
				c.getMinimum(Calendar.MINUTE), c.getMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getMinimum(Calendar.MILLISECOND));
		return c.getTime();
	}

	public static Integer extrairAno(Date d) {
		Calendar data = new GregorianCalendar();
		data.setTime(d);
		return data.get(Calendar.YEAR);
	}

	public static Integer extrairMes(Date d) {
		Calendar data = new GregorianCalendar();
		data.setTime(d);
		return data.get(Calendar.MONTH);
	}

	public static Integer extrairDia(Date d) {
		Calendar data = new GregorianCalendar();
		data.setTime(d);
		return data.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retorna uma String com a hora do Objeto Date passado como parametro
	 */
	public static String converterDataParaHoraString(Date data) {
		Locale locale = new Locale("pt", "BR");
		DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
		return timeFormat.format(data);
	}

	/*
	 * public static String geraSenhaMD5(String senha){ try{ MessageDigest
	 * messageDiegest = MessageDigest.getInstance("MD5");
	 * messageDiegest.update(senha.getBytes("UTF-8")); return
	 * Base64.encode(messageDiegest.digest()); } catch (NoSuchAlgorithmException
	 * e) { throw new Error(e); } catch (UnsupportedEncodingException e) { throw
	 * new Error(e); } }
	 */
	public static String md5(String texto) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(texto.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static int retornaAno() {
		int ano;
		Calendar c = Calendar.getInstance();
		ano = c.get(Calendar.YEAR);

		return ano;
	}

	/*
	 * public static void addMessage(Severity severityError, String mgs) {
	 * FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage(severityError,mgs,mgs)); }
	 * 
	 * public static void addMultiLinesMessage(Severity severityError, String
	 * mgs) { FacesContext fc = FacesContext.getCurrentInstance();
	 * 
	 * String lineSeparator = "\n"; for (String str : mgs.split(lineSeparator))
	 * { if (!str.isEmpty()){ FacesMessage fm = new FacesMessage(severityError,
	 * "", str); fc.addMessage("", fm); } } }
	 */
	// Funcão para salvar data em hora
	@SuppressWarnings("deprecation")
	public static Time conversorCale(Date c) {
		Calendar aux = Calendar.getInstance();
		aux.set(Calendar.HOUR, c.getHours());
		aux.set(Calendar.MINUTE, c.getMinutes());
		aux.set(Calendar.SECOND, c.getSeconds());

		Time auxT = new Time(aux.getTimeInMillis());
		return auxT;
	}

	/**
	 * Adiciona uma mensagem na pégina. Adicionar a marcaééo
	 * <p:messages id="msg" /> no inécio do formulério.
	 */
	public static void addMensagem(Severity s, String m) {
		FacesContext c = FacesContext.getCurrentInstance();
		FacesMessage fm = new FacesMessage(s, m, m);
		c.addMessage(null, fm);
	}

	/**
	 * Adiciona quantidade de meses na data.
	 * 
	 * @author vagnerpb
	 * @param data
	 * @param qtd
	 * @return
	 */
	public static Date addMes(Date data, int qtd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, qtd);
		return cal.getTime();
	}

	/**
	 * Adiciona quantidade de dias na data.
	 * 
	 * @author vagnerpb
	 * @param data
	 * @param qtd
	 * @return
	 */
	public static Date addDia(Date data, int qtd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_MONTH, qtd);
		return cal.getTime();
	}

	/**
	 * Adiciona quantidade de anos na data.
	 * 
	 * @author vagnerpb
	 * @param data
	 * @param qtd
	 * @return
	 */
	public static Date addAno(Date data, int qtd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.YEAR, qtd);
		return cal.getTime();
	}

	public Boolean filterNotCaseSensitive(Object value, Object filter, Locale locale) {

		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals(""))
			return true;

		if (value == null)
			return false;

		String objectText = value.toString().toUpperCase();
		filterText = filterText.toUpperCase();

		if (objectText.contains(filterText))
			return true;
		else
			return false;
	}

	public static Boolean validaMesAno(String campo){
		String expReg = "/^(0[1-9]|1[0-2])\\/((1[2-9]|[2-9][0-9])[0-9]{2})$/";
		if(campo.matches(expReg)){
			return true;
		}else{
			return false;
		}
		
	}
}