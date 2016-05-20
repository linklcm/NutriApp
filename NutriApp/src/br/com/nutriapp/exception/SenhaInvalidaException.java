package br.com.nutriapp.exception;

public class SenhaInvalidaException extends Exception {

	private static final long serialVersionUID = 9074908759659977205L;

	public SenhaInvalidaException() {
		super("Atenção, senha atual inválida!");
	}
}
