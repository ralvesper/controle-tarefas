package br.com.jm.tarefas.domain;

/**
 * Interface para geração de códigos de tarefas
 */
public interface GeradorCodigoTarefa {

    /**
     * Gera o código para próxima tarefa
     */
    public Long proximoCodigoTarefa();

}
