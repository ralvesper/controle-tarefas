package br.com.jm.tarefas.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

/**
 * Repositório para manipulação da entidade {@link Tarefa}
 */
@Profile("prod")
public interface TarefaRepository extends JpaRepository<Tarefa, Long>, GeradorCodigoTarefa {

    /**
     * Recupera o próximo códifo para tarefa
     * 
     * @return próximo código para tarefa
     */
    @Override
    @Procedure(name = "Tarefa.proximoCodigo")
    public Long proximoCodigoTarefa();

    /**
     * Recupera a lista de tarefas para um usuário responsável
     * 
     * @param usuario
     *            usuário responsável pelas tarefas
     * @param sort
     *            parâmetros de ordenação das tarefas
     * 
     * @return lista de tarefas para o usuário ordenadas conforme os parâmetros
     *         de ordenação recebidos
     */
    public List<Tarefa> findByResponsavel(Usuario usuario, Sort sort);

    /**
     * Recupera a lista de tarefas de acordo com o status
     * 
     * @param status
     *            status para filtragem das tarefas
     * @param sort
     *            parâmetros de ordenação das tarefas
     *
     * @return lista de tarefas por status ordenadas conforme os parâmetros de
     *         ordenação recebidos
     */
    public List<Tarefa> findByStatus(StatusTarefa status, Sort sort);

    /**
     * Recupera uma Tarefa através do identificador
     * 
     * @param identificador
     *            identificador da tarefa
     * 
     * @return {@link Optional} encapsulando a tarefa correspondente ao
     *         identificador
     */
    public Optional<Tarefa> findByIdentificador(IdentificadorTarefa identificador);

}
