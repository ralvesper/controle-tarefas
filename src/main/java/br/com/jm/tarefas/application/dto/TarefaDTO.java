package br.com.jm.tarefas.application.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.jm.tarefas.domain.Tarefa;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * DTO com informações de {@link Tarefa}
 */
public class TarefaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private IdentificadorTarefaDTO identificador;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dataCadastro;

    private String titulo;

    private String descricao;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dataDevida;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dataConclusao;

    private StatusTarefaDTO status;

    private UsuarioDTO autor;

    private UsuarioDTO responsavel;

    public TarefaDTO() {
    }

    public TarefaDTO(IdentificadorTarefaDTO identificador, LocalDateTime dataCadastro,
            String titulo, String descricao, LocalDate dataDevida, LocalDateTime dataConclusao,
            StatusTarefaDTO status, UsuarioDTO autor, UsuarioDTO responsavel) {
        super();
        this.identificador = identificador;
        this.dataCadastro = dataCadastro;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataDevida = dataDevida;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.autor = autor;
        this.responsavel = responsavel;
    }

    public IdentificadorTarefaDTO getIdentificador() {
        return identificador;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataDevida() {
        return dataDevida;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public StatusTarefaDTO getStatus() {
        return status;
    }

    public UsuarioDTO getAutor() {
        return autor;
    }

    public UsuarioDTO getResponsavel() {
        return responsavel;
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
        TarefaDTO other = (TarefaDTO) obj;
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
        return "TarefaDTO [identificador=" + identificador + "]";
    }

}
