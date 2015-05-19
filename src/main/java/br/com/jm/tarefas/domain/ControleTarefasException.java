package br.com.jm.tarefas.domain;

/**
 * Exceção base para os erros nas interações com as tarefas
 **/
public class ControleTarefasException extends Exception {

    private static final long serialVersionUID = 1L;

    public ControleTarefasException(String message) {
        super(message);
    }

    public ControleTarefasException(String message, Throwable cause) {
        super(message, cause);
    }

}
