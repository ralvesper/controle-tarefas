package br.com.jm.tarefas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Componente para a identificação de uma tarefa
 */
@Embeddable
public class IdentificadorTarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CODIGO", length = 10, nullable = false, unique = true)
    private Long codigo;

    IdentificadorTarefa() {
    }

    public IdentificadorTarefa(Long codigo) {
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
        IdentificadorTarefa other = (IdentificadorTarefa) obj;
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
        return "IdentificadorTarefa [codigo=" + codigo + "]";
    }

}
