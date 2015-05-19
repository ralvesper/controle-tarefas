package br.com.jm.tarefas.infrastructure;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import br.com.jm.tarefas.domain.DadosExemplo;
import br.com.jm.tarefas.domain.GeradorCodigoTarefa;
import br.com.jm.tarefas.domain.Tarefa;
import br.com.jm.tarefas.domain.Usuario;

/**
 * Carrega o banco com dados para a execução de testes
 */
public class InicializadorBD {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private GeradorCodigoTarefa geradorCodigoTarefa;

    @Autowired
    private JpaRepository<Usuario, Long> usuarioRepository;

    @Autowired
    private JpaRepository<Tarefa, Long> tarefaRepository;

    private DadosExemplo dadosExemplo;

    @PostConstruct
    public void inicializar() {
        dadosExemplo = new DadosExemplo(geradorCodigoTarefa);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((ts) -> {
            carregarDados();
            return null;
        });
    }

    private void carregarDados() {
        usuarioRepository.save(dadosExemplo.getFulano());
        usuarioRepository.save(dadosExemplo.getBeltrano());
        usuarioRepository.save(dadosExemplo.getSicrano());
        usuarioRepository.save(dadosExemplo.getJohnDoe());
        tarefaRepository.save(dadosExemplo.getTarefaCadastrada());
        tarefaRepository.save(dadosExemplo.getTarefaIniciada());
    }
}
