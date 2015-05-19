package br.com.jm.tarefas.application.dto;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.jm.tarefas.domain.Usuario;

/**
 * DTO com informações de {@link Usuario}
 */
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private IdentificadorUsuarioDTO identificador;

    private String nome;

    private LocalDate dataCadastro;

    private String perfil;

    public UsuarioDTO(String nome, IdentificadorUsuarioDTO identificador, LocalDate dataCadastro,
            String perfil) {
        super();
        this.nome = nome;
        this.identificador = identificador;
        this.dataCadastro = dataCadastro;
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public IdentificadorUsuarioDTO getIdentificador() {
        return identificador;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public String getPerfil() {
        return perfil;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
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
        UsuarioDTO other = (UsuarioDTO) obj;
        if (identificador == null) {
            if (other.identificador != null) {
                return false;
            }
        } else if (!identificador.equals(other.identificador)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UsuarioDTO [identificador=" + identificador + "]";
    }

}
