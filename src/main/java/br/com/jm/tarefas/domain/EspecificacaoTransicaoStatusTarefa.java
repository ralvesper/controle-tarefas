package br.com.jm.tarefas.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import br.com.jm.tarefas.domain.comum.Especificacao;

/**
 * Especifição para transições permitidas nos status de uma {@link Tarefa}
 */
public class EspecificacaoTransicaoStatusTarefa implements Especificacao<StatusTarefa> {

    private static final EspecificacaoTransicaoStatusTarefa CADASTRADA = new EspecificacaoTransicaoStatusTarefa(
            StatusTarefa.DESCARTADA, new HashSet<StatusTarefa>(Arrays.asList(StatusTarefa.INICIADA,
                    StatusTarefa.DESCARTADA)));

    private static final EspecificacaoTransicaoStatusTarefa INICIADA = new EspecificacaoTransicaoStatusTarefa(
            StatusTarefa.INICIADA, new HashSet<StatusTarefa>(Arrays.asList(StatusTarefa.CONCLUIDA,
                    StatusTarefa.DESCARTADA)));

    private static final EspecificacaoTransicaoStatusTarefa CONCLUIDA = new EspecificacaoTransicaoStatusTarefa(
            StatusTarefa.CONCLUIDA, Collections.emptySet());

    private static final EspecificacaoTransicaoStatusTarefa DESCARTADA = new EspecificacaoTransicaoStatusTarefa(
            StatusTarefa.DESCARTADA, Collections.emptySet());

    /**
     * Status Atual
     */
    private StatusTarefa statusAtual;

    /**
     * Próximos Status
     */
    private Set<StatusTarefa> proximosStatus = new HashSet<>();

    EspecificacaoTransicaoStatusTarefa(StatusTarefa statusAtual, Set<StatusTarefa> proximosStatus) {
        this.statusAtual = statusAtual;
        this.proximosStatus.addAll(proximosStatus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfeitoPor(StatusTarefa proximoStatus) {
        return this.proximosStatus.contains(proximoStatus);
    }

    public StatusTarefa getStatusAtual() {
        return statusAtual;
    }

    /**
     * Recupera a especificação de transição de status de acordo o status
     * recebido
     * 
     * @param statusTarefa
     *            status de tarefa para recuperação da especificação
     * @return Especificação de acordo com o status de tarefa recebido
     * @throws IllegalArgumentException
     *             caso não seja possível definir uma especificação para o
     *             status de tarefa recebido
     */
    public static EspecificacaoTransicaoStatusTarefa getInstance(StatusTarefa statusTarefa) {
        switch (statusTarefa) {
        case CADASTRADA:
            return CADASTRADA;
        case INICIADA:
            return INICIADA;
        case CONCLUIDA:
            return CONCLUIDA;
        case DESCARTADA:
            return DESCARTADA;
        default:
            throw new IllegalArgumentException(String.format("Status de tarefa desconhecido [%s]",
                    statusTarefa));
        }
    }

}
