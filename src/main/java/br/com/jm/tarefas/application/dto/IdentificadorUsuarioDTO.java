package br.com.jm.tarefas.application.dto;

import java.io.Serializable;

/**
 * DTO com as informações para identificação de um usuário
 */
public class IdentificadorUsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    public IdentificadorUsuarioDTO(String email) {
        super();
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
        IdentificadorUsuarioDTO other = (IdentificadorUsuarioDTO) obj;
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
        return "IdentificadorUsuarioDTO [email=" + email + "]";
    }

}
