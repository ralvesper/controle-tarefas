package br.com.jm.tarefas.application.dto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**
 * DTO com informações para criação de uma nova tarefa
 */
public class NovaTarefaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;

    private String descricao;

    @DateTimeFormat(iso = ISO.DATE)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataDevida;

    private IdentificadorUsuarioDTO autor;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataDevida() {
        return dataDevida;
    }

    public void setDataDevida(LocalDate dataDevida) {
        this.dataDevida = dataDevida;
    }

    public IdentificadorUsuarioDTO getAutor() {
        return autor;
    }

    public void setAutor(IdentificadorUsuarioDTO autor) {
        this.autor = autor;
    }

}
