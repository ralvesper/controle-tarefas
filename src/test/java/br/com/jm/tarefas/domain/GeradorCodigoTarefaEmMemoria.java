package br.com.jm.tarefas.domain;

import java.util.concurrent.atomic.AtomicLong;

import br.com.jm.tarefas.domain.GeradorCodigoTarefa;

public class GeradorCodigoTarefaEmMemoria implements GeradorCodigoTarefa {

    private static final AtomicLong GENERATOR = new AtomicLong(1L);

    @Override
    public Long proximoCodigoTarefa() {
        return GENERATOR.getAndIncrement();
    }

}
