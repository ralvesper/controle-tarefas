package br.com.jm.tarefas.application.dto;

import java.io.Serializable;

/**
 * DTO com informações para identificação de uma tarefa
 */
public class IdentificadorTarefaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;

    public IdentificadorTarefaDTO(Long codigo) {
        super();
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
        IdentificadorTarefaDTO other = (IdentificadorTarefaDTO) obj;
        if (codigo == null) {
            if (other.codigo != null) {
                return false;
            }
        } else if (!codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IdentificadorTarefaDTO [codigo=" + codigo + "]";
    }

}
