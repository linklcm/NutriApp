package br.com.nutriapp.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class StringUtil {

	public static String capitalizarPrimeiroCaracter(String s){		
		return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
	}

	public static final String[] PREPOSICOES = {"a", "e", "a", "de", "do",	"da", "dos", "das"};

	public static Boolean isPreposicao(String palavra){
		List<String> preposicoes = Arrays.asList(PREPOSICOES);
		palavra = palavra.toLowerCase();
		return preposicoes.contains(palavra);
	}
	
	public static String capitalizarIniciais(String expressao){
		expressao = retiraEspacosEmBranco(expressao);
		String capital = "";
		for (String palavra : expressao.split(" ")) {
			capital += capital.equals("") ?  "" : " ";
			capital += isPreposicao(palavra) ? palavra.toLowerCase() : capitalizarPrimeiroCaracter(palavra); 
		}
		return capital;
	}

	public static String retiraPontoEtraco(String c) {
		c = c.replaceAll("[^0-9]", "");
		return c;
	}

	public static String retiraEspacosEmBranco(String str) {
		str = str.trim().toUpperCase().replaceAll("\\s+", " ");
		return str;
	}

	public static String retiraMascara(String c) {
		c = c.replaceAll("[^A-Za-z0-9]", "");
		return c.equals("") ? null : c;
	}

	public static String removerEmDash(String texto) {
		if (texto == null) {
			return null;
		}
		texto = texto.replaceAll("\\p{Pd}","-");
		texto = texto.replaceAll("“","'");
		texto = texto.replaceAll("”","'");
		texto = texto.replaceAll("’", "'");
		return texto;
	}

	public static String converteLATIN1(String texto) throws UnsupportedEncodingException{
		if (texto == null){
			return null;
		}
		byte[] bytes = texto.getBytes();  
		texto = new String(bytes, "LATIN1");
		return texto;
	}	
}
