package br.com.jm.tarefas.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import br.com.jm.tarefas.util.TestConfig;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { TestConfig.class })
@ActiveProfiles("dev")
public abstract class AbstractRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext
    private EntityManager entityManager;

    private DadosExemplo dadosExemplo = new DadosExemplo(new GeradorCodigoTarefaEmMemoria());

    @Before
    public void inicializar() {
        entityManager.persist(dadosExemplo.getFulano());
        entityManager.persist(dadosExemplo.getBeltrano());
        entityManager.persist(dadosExemplo.getSicrano());
        entityManager.persist(dadosExemplo.getTarefaCadastrada());
        entityManager.persist(dadosExemplo.getTarefaIniciada());
    }

    public DadosExemplo getDadosExemplo() {
        return dadosExemplo;
    }

    public void salvar(Object... objects) {
        for (Object object : objects) {
            entityManager.persist(object);
        }
        entityManager.flush();
    }

}
