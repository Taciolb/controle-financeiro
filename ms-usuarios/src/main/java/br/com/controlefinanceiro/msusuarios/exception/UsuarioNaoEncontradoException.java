package br.com.controlefinanceiro.msusuarios.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(String id) {
        super("Usuario nao encontrado com id" + id);
    }
}
