package br.com.jm.tarefas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Componente para a identificação de um usuário
 */
@Embeddable
public class IdentificadorUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    IdentificadorUsuario() {
    }

    public IdentificadorUsuario(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        IdentificadorUsuario other = (IdentificadorUsuario) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IdentificadorUsuario [email=" + email + "]";
    }

}
