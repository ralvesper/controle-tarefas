package br.com.jm.tarefas.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

import br.com.jm.tarefas.domain.Tarefa.ConstrutorTarefa;

/**
 * Representa um usuário no sistema de controle de tarefas. Um usuário pode
 * criar tarefas e atribuí-las para um responsável(incluíndo o próprio usuário),
 * neste caso, o usuário que irá executar a tarefa.
 * 
 */
@Entity
public class Usuario {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", length = 50, nullable = false)
    private String nome;

    @Embedded
    private IdentificadorUsuario identificador;

    @Column(name = "SENHA", length = 10, nullable = false)
    private String senha;

    @Column(name = "DATA_CADASTRO", nullable = false)
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERFIL", nullable = false)
    private PerfilUsuario perfil;

    @OneToMany(mappedBy = "autor")
    private Collection<Tarefa> tarefasCriadas = new ArrayList<Tarefa>();

    @OneToMany(mappedBy = "responsavel")
    private Collection<Tarefa> tarefas = new ArrayList<Tarefa>();

    Usuario() {
    }

    Usuario(String nome, IdentificadorUsuario identificadorUsuario, String senha,
            PerfilUsuario perfil) {
        Validate.notEmpty(nome, "Nome não deve ser nulo/vazio!");
        Validate.notNull(identificadorUsuario, "Identificador do usuário não deve ser nulo!");
        Validate.notEmpty(senha, "Senha não deve ser nula/vazia!");
        Validate.notNull(perfil, "Perfil não deve ser nulo!");
        this.nome = nome;
        this.identificador = identificadorUsuario;
        this.senha = senha;
        this.perfil = perfil;
        this.dataCadastro = LocalDate.now();
    }

    public String nome() {
        return nome;
    }

    public IdentificadorUsuario identificador() {
        return identificador;
    }

    public String senha() {
        return senha;
    }

    public PerfilUsuario perfil() {
        return perfil;
    }

    public LocalDate dataCadastro() {
        return dataCadastro;
    }

    /**
     * Cria uma nova tarefa de autoria deste usuário
     * 
     * @param identificadorTarefa
     *            identificador da tarefa
     * @param titulo
     *            título da tarefa
     * @param descricao
     *            descrição da tarefa
     * @param dataDevida
     *            data devida da tarefa
     * @return Tarefa criada
     * @throws PerfilSemPermissaoCriacaoTarefaException
     *             se este usuário não tiver permissão para criação de terafas,
     *             ou seja, o usuário não possui o perfil
     *             {@link PerfilUsuario#ADMINISTRADOR}
     */
    public Tarefa criarTarefa(IdentificadorTarefa identificadorTarefa, String titulo,
            String descricao, LocalDate dataDevida) throws PerfilSemPermissaoCriacaoTarefaException {
        verificarPermissaoCriacaoTarefa(this);
        Tarefa tarefa = new ConstrutorTarefa().identificador(identificadorTarefa).titulo(titulo)
                .descricao(descricao).dataDevida(dataDevida).autor(this).construir();
        tarefasCriadas.add(tarefa);
        return tarefa;
    }

    /**
     * Verifica se o usuário é administrador
     * 
     * @return true se o usuário possui perfil de Administrador, false caso
     *         contrário
     */
    public boolean isAdministrador() {
        return PerfilUsuario.ADMINISTRADOR.equals(perfil);
    }

    /*
     * Verifica a permissão de criação de tarefas pelo usuário
     */
    private void verificarPermissaoCriacaoTarefa(Usuario usuario)
            throws PerfilSemPermissaoCriacaoTarefaException {
        if (!usuario.isAdministrador()) {
            throw new PerfilSemPermissaoCriacaoTarefaException(usuario.perfil);
        }
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
        Usuario other = (Usuario) obj;
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
        return "Usuario [identificador=" + identificador + ", perfil=" + perfil + "]";
    }

}
