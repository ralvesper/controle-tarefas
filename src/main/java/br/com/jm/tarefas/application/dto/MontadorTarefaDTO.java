package br.com.jm.tarefas.application.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.jm.tarefas.domain.Tarefa;

/**
 * Utilitário para criação de DTOs com informações de {@link Tarefa}
 */
public class MontadorTarefaDTO {

    private MontadorTarefaDTO() {
        throw new UnsupportedOperationException("Instanciação não permitida!");
    }

    public static TarefaDTO montarTarefa(Tarefa tarefa) {
        if (tarefa == null) {
            return null;
        }
        UsuarioDTO autorDTO = MontadorUsuarioDTO.montarUsuario(tarefa.autor());
        UsuarioDTO responsavelDTO = MontadorUsuarioDTO.montarUsuario(tarefa.resposanvel());
        IdentificadorTarefaDTO identificador = new IdentificadorTarefaDTO(tarefa.identificador()
                .getCodigo());
        return new TarefaDTO(identificador, tarefa.cadastro(), tarefa.titulo(), tarefa.descricao(),
                tarefa.dataDevida(), tarefa.dataConclusao(), new StatusTarefaDTO(tarefa.status()
                        .name()), autorDTO, responsavelDTO);
    }

    public static List<TarefaDTO> montarTarefas(Collection<Tarefa> tarefas) {
        if (tarefas == null || tarefas.isEmpty()) {
            return new ArrayList<TarefaDTO>(0);
        }
        List<TarefaDTO> dtos = new ArrayList<TarefaDTO>(tarefas.size());
        for (Tarefa tarefa : tarefas) {
            dtos.add(montarTarefa(tarefa));
        }
        return dtos;
    }
}
