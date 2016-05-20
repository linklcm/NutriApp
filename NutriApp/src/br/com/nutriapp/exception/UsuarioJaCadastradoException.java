package br.com.nutriapp.exception;

public class UsuarioJaCadastradoException extends Exception {

	private static final long serialVersionUID = 9074908759659977205L;

	public UsuarioJaCadastradoException() {
		super("Usuário já cadastrado!");
	}
}
