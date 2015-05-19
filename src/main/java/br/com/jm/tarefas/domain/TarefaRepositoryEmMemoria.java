package br.com.jm.tarefas.domain;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;

/**
 * Versão do repositório para testes em desenvolvimento
 */
@Profile("dev")
public interface TarefaRepositoryEmMemoria extends TarefaRepository {

    @Override
    @Query(value = "CALL NEXT VALUE FOR SEQUENCIA_CODIGO_TAREFA", nativeQuery = true)
    public Long proximoCodigoTarefa();

}
