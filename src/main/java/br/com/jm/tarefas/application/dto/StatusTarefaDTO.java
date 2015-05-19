package br.com.jm.tarefas.application.dto;

import java.io.Serializable;

/**
 * DTO que representa o status de uma tarefa
 */
public class StatusTarefaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;

    public StatusTarefaDTO() {
    }

    public StatusTarefaDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

}
