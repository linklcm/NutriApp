package br.com.nutriapp.exception;

public class DietaJaCadastradoException extends Exception {

	private static final long serialVersionUID = 9074908759659977205L;

	public DietaJaCadastradoException() {
		super("Dieta já cadastrado!");
	}
}
