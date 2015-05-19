package br.com.jm.tarefas.domain;

/**
 * Exceção que representa uma tentativa de transição inválida de status em uma
 * tarefa
 */
public class TransicaoStatusTarefaException extends ControleTarefasException {

    private static final long serialVersionUID = 1L;

    /**
     * Status atual da tarefa
     */
    private final StatusTarefa statusAtual;

    /**
     * Novo status de uma tarefas
     */
    private final StatusTarefa novoStatus;

    public TransicaoStatusTarefaException(StatusTarefa statusAtual, StatusTarefa novoStatus) {
        super(String.format("Transição do status [%s] para status [%s] não permitida!",
                statusAtual, novoStatus));
        this.statusAtual = statusAtual;
        this.novoStatus = novoStatus;

    }

    public StatusTarefa getStatusAtual() {
        return statusAtual;
    }

    public StatusTarefa getNovoStatus() {
        return novoStatus;
    }

}
