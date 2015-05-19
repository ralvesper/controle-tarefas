package br.com.jm.tarefas.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

/**
 * Testes para {@link TarefaRepository}
 */
public class TarefaRepositoryTest extends AbstractRepositoryTest {

    private static final Sort SORT_DATA_DEVIDA_ASC = new Sort(Sort.Direction.ASC,
            Arrays.asList("dataDevida"));

    @Autowired
    private TarefaRepositoryEmMemoria tarefaRepository;

    @Test
    public void testNextCodigoTarefa() {
        Long next = tarefaRepository.proximoCodigoTarefa();
        Assert.assertEquals(Long.valueOf(1), next);

        next = tarefaRepository.proximoCodigoTarefa();
        Assert.assertEquals(Long.valueOf(2), next);
    }

    @Test
    public void testFindByResponsavel() {
        List<Tarefa> tarefas = tarefaRepository.findByResponsavel(getDadosExemplo().getSicrano(),
                SORT_DATA_DEVIDA_ASC);
        Assert.assertEquals(1, tarefas.size());
        Tarefa atual = tarefas.get(0);
        Assert.assertEquals(getDadosExemplo().getTarefaIniciada(), atual);
    }

    @Test
    public void testFindByResponsavelSemTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findByResponsavel(getDadosExemplo().getBeltrano(),
                SORT_DATA_DEVIDA_ASC);
        Assert.assertEquals(0, tarefas.size());
    }

    @Test
    public void testFindByStatusCadastrada() {
        List<Tarefa> tarefas = tarefaRepository.findByStatus(StatusTarefa.CADASTRADA,
                SORT_DATA_DEVIDA_ASC);
        Assert.assertEquals(1, tarefas.size());
        Tarefa atual = tarefas.get(0);
        Assert.assertEquals(getDadosExemplo().getTarefaCadastrada(), atual);
    }

    @Test
    public void testFindByStatusIniciada() {
        List<Tarefa> tarefas = tarefaRepository.findByStatus(StatusTarefa.INICIADA,
                SORT_DATA_DEVIDA_ASC);
        Assert.assertEquals(1, tarefas.size());
        Tarefa atual = tarefas.get(0);
        Assert.assertEquals(getDadosExemplo().getTarefaIniciada(), atual);
    }

    @Test
    public void testFindByStatusConcluida() {
        List<Tarefa> tarefas = tarefaRepository.findByStatus(StatusTarefa.CONCLUIDA,
                SORT_DATA_DEVIDA_ASC);
        Assert.assertEquals(0, tarefas.size());
    }

    @Test
    public void testFindByStatusDescartada() {
        List<Tarefa> tarefas = tarefaRepository.findByStatus(StatusTarefa.DESCARTADA,
                SORT_DATA_DEVIDA_ASC);
        Assert.assertEquals(0, tarefas.size());
    }

    @Test
    public void testFindByIdentificador() {

        Optional<Tarefa> tarefa = tarefaRepository.findByIdentificador(getDadosExemplo()
                .getTarefaIniciada().identificador());
        Assert.assertTrue(tarefa.isPresent());
        Assert.assertEquals(getDadosExemplo().getTarefaIniciada(), tarefa.get());
    }

    @Test
    public void testFindByIdentificadorInexistente() {

        Optional<Tarefa> tarefa = tarefaRepository.findByIdentificador(new IdentificadorTarefa(
                Long.MAX_VALUE));
        Assert.assertFalse(tarefa.isPresent());
    }

    @Test
    public void testFindAll() {
        List<Tarefa> tarefas = tarefaRepository.findAll(SORT_DATA_DEVIDA_ASC);
        Assert.assertEquals(2, tarefas.size());
        Assert.assertEquals(getDadosExemplo().getTarefaIniciada(), tarefas.get(0));
        Assert.assertEquals(getDadosExemplo().getTarefaCadastrada(), tarefas.get(1));
    }

}
