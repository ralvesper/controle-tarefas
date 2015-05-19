package br.com.jm.tarefas.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.apache.commons.lang3.Validate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.jm.tarefas.domain.comum.Construtor;

/**
 * Uma tarefa é criada por um usuário(ADMINISTRADOR) e pode ser selecionado por
 * um responsável para execução
 */
@Entity
@NamedStoredProcedureQuery(name = "Tarefa.proximoCodigo", procedureName = "PROXIMO_CODIGO_TAREFA", parameters = { @StoredProcedureParameter(mode = ParameterMode.OUT, type = Long.class, name = "PROXIMO_CODIGO") })
public class Tarefa {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private IdentificadorTarefa identificador;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    @Column(name = "DATA_CADASTRO", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "TITULO", nullable = false, length = 50)
    private String titulo;

    @Column(name = "DESCRICAO", nullable = false, length = 200)
    private String descricao;

    @Column(name = "DATA_DEVIDA", nullable = false)
    private LocalDate dataDevida;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    @Column(name = "DATA_CONCLUSAO")
    private LocalDateTime dataConclusao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusTarefa status;

    @ManyToOne
    @JoinColumn(name = "AUTOR_ID", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "RESPONSAVEL_ID")
    private Usuario responsavel;

    Tarefa() {
    }

    Tarefa(ConstrutorTarefa builder) {
        Validate.notNull(builder.identificadorTarefa, "Identificador da tarefa não deve ser nulo!");
        Validate.notEmpty(builder.titulo, "Título da tarefa não deve ser nulo/vazio!");
        Validate.notEmpty(builder.descricao, "Descrição da tarefa não deve ser nula/vazio!");
        Validate.notNull(builder.dataDevida, "Data devida da tarefa não deve ser nula!");
        Validate.notNull(builder.autor, "Autor da tarefa não deve ser nulo!");

        this.identificador = builder.identificadorTarefa;
        this.titulo = builder.titulo;
        this.descricao = builder.descricao;
        this.dataDevida = builder.dataDevida;
        this.autor = builder.autor;
        this.status = StatusTarefa.CADASTRADA;
        this.dataCadastro = LocalDateTime.now();
    }

    public IdentificadorTarefa identificador() {
        return identificador;
    }

    public LocalDateTime cadastro() {
        return dataCadastro;
    }

    public String titulo() {
        return titulo;
    }

    public String descricao() {
        return descricao;
    }

    public LocalDate dataDevida() {
        return dataDevida;
    }

    public LocalDateTime dataConclusao() {
        return dataConclusao;
    }

    public StatusTarefa status() {
        return status;
    }

    public Usuario autor() {
        return autor;
    }

    public Usuario resposanvel() {
        return responsavel;
    }

    public void iniciar(Usuario usuario) throws ControleTarefasException {
        mudarStatus(usuario, StatusTarefa.INICIADA);
    }

    public void concluir(Usuario usuario) throws ControleTarefasException {
        mudarStatus(usuario, StatusTarefa.CONCLUIDA);
        dataConclusao = LocalDateTime.now();
    }

    public void descartar(Usuario usuario) throws ControleTarefasException {
        mudarStatus(usuario, StatusTarefa.DESCARTADA);
    }

    public boolean isCadastrada() {
        return StatusTarefa.CADASTRADA.equals(status);
    }

    public boolean isIniciada() {
        return StatusTarefa.INICIADA.equals(status);
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
        Tarefa other = (Tarefa) obj;
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
        return "Tarefa [identificador=" + identificador + ", titulo=" + titulo + ", dataDevida="
                + dataDevida + "]";
    }

    /**
     * Muda o status da tarefa
     * 
     * @param novoStatus
     *            novo status da tarefa
     * @throws TransicaoStatusTarefaException
     *             caso a transição de status não seja válida
     * @throws TarefaManipulacaoUsuarioInvalidoException
     *             caso o usuário executando a mudança de status não seja o
     *             usuário responsável pela tarefa
     */
    private void mudarStatus(Usuario usuario, StatusTarefa novoStatus)
            throws ControleTarefasException {
        if (isCadastrada()) {
            assumirTarefa(usuario);
        } else {
            verificarResponsavel(usuario);
        }
        EspecificacaoTransicaoStatusTarefa especificao = EspecificacaoTransicaoStatusTarefa
                .getInstance(status());
        if (especificao.isSatisfeitoPor(novoStatus)) {
            status = novoStatus;
        } else {
            throw new TransicaoStatusTarefaException(status(), novoStatus);
        }
    }

    private void verificarResponsavel(Usuario usuario)
            throws TarefaManipulacaoUsuarioInvalidoException {
        if (!responsavel.equals(usuario)) {
            throw new TarefaManipulacaoUsuarioInvalidoException(identificador(),
                    usuario.identificador());
        }
    }

    private void assumirTarefa(Usuario usuario) throws TarefaManipulacaoUsuarioInvalidoException {
        if (responsavel != null) {
            throw new TarefaManipulacaoUsuarioInvalidoException(identificador,
                    usuario.identificador());
        }
        responsavel = usuario;
    }

    /**
     * Construtor para Tarefa
     */
    public static class ConstrutorTarefa implements Construtor<Tarefa> {

        private IdentificadorTarefa identificadorTarefa;

        private String titulo;

        private String descricao;

        private LocalDate dataDevida;

        private Usuario autor;

        public Tarefa construir() {
            return new Tarefa(this);
        }

        public ConstrutorTarefa identificador(IdentificadorTarefa identificadorTarefa) {
            this.identificadorTarefa = identificadorTarefa;
            return this;
        }

        public ConstrutorTarefa titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public ConstrutorTarefa descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public ConstrutorTarefa dataDevida(LocalDate dataDevida) {
            this.dataDevida = dataDevida;
            return this;
        }

        public ConstrutorTarefa autor(Usuario autor) {
            this.autor = autor;
            return this;
        }

    }

}
