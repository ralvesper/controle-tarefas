package br.com.jm.tarefas.application.dto;

import java.io.Serializable;

/**
 * DTO com as informações para ação de um usuário em uma tarefa
 */
public class AcaoTarefaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private IdentificadorTarefaDTO identificadorTarefa;

    private IdentificadorUsuarioDTO identificadorResponsavel;

    public AcaoTarefaDTO(IdentificadorTarefaDTO identificadorTarefa,
            IdentificadorUsuarioDTO identificadorResponsavel) {
        super();
        this.identificadorTarefa = identificadorTarefa;
        this.identificadorResponsavel = identificadorResponsavel;
    }

    public AcaoTarefaDTO(Long codigoTarefa, String emailResponsavel) {
        this(new IdentificadorTarefaDTO(codigoTarefa),
                new IdentificadorUsuarioDTO(emailResponsavel));
    }

    public IdentificadorTarefaDTO getIdentificadorTarefa() {
        return identificadorTarefa;
    }

    public IdentificadorUsuarioDTO getIdentificadorResponsavel() {
        return identificadorResponsavel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((identificadorResponsavel == null) ? 0 : identificadorResponsavel.hashCode());
        result = prime * result
                + ((identificadorTarefa == null) ? 0 : identificadorTarefa.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AcaoTarefaDTO other = (AcaoTarefaDTO) obj;
        if (identificadorResponsavel == null) {
            if (other.identificadorResponsavel != null) {
                return false;
            }
        } else if (!identificadorResponsavel.equals(other.identificadorResponsavel)) {
            return false;
        }
        if (identificadorTarefa == null) {
            if (other.identificadorTarefa != null) {
                return false;
            }
        } else if (!identificadorTarefa.equals(other.identificadorTarefa)) {
            return false;
        }
        return true;
    }

}
