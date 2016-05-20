package br.com.nutriapp.exception;

public class SenhasNaoConferemException extends Exception {

	private static final long serialVersionUID = 9074908759659977205L;

	public SenhasNaoConferemException() {
		super("As senhas não conferem!");
	}
}
