package br.com.jm.tarefas.domain;

/**
 * Manipulação de tarefa com responsável inválido
 */
public class TarefaManipulacaoUsuarioInvalidoException extends ControleTarefasException {

    private static final long serialVersionUID = 1L;

    private final IdentificadorTarefa identificadorTarefa;

    private final IdentificadorUsuario identificadorUsuario;

    public TarefaManipulacaoUsuarioInvalidoException(IdentificadorTarefa identificadorTarefa,
            IdentificadorUsuario identificadorUsuario) {
        super(String.format("Tarefa [%s] não pode ser manipulada pelo usuário [%s]!",
                identificadorTarefa, identificadorUsuario));
        this.identificadorTarefa = identificadorTarefa;
        this.identificadorUsuario = identificadorUsuario;
    }

    public IdentificadorTarefa getIdentificadorTarefa() {
        return identificadorTarefa;
    }

    public IdentificadorUsuario getIdentificadorUsuario() {
        return identificadorUsuario;
    }

}
