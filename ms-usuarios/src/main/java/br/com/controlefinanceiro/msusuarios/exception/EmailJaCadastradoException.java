package br.com.controlefinanceiro.msusuarios.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("E-mail ja cadastrado: " + email);
    }
}
