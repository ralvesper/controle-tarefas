package br.com.jm.tarefas.application;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;

import br.com.jm.tarefas.application.dto.AcaoTarefaDTO;
import br.com.jm.tarefas.application.dto.IdentificadorTarefaDTO;
import br.com.jm.tarefas.application.dto.IdentificadorUsuarioDTO;
import br.com.jm.tarefas.application.dto.MontadorTarefaDTO;
import br.com.jm.tarefas.application.dto.NovaTarefaDTO;
import br.com.jm.tarefas.application.dto.StatusTarefaDTO;
import br.com.jm.tarefas.application.dto.TarefaDTO;
import br.com.jm.tarefas.domain.ControleTarefasException;
import br.com.jm.tarefas.domain.IdentificadorTarefa;
import br.com.jm.tarefas.domain.IdentificadorUsuario;
import br.com.jm.tarefas.domain.StatusTarefa;
import br.com.jm.tarefas.domain.Tarefa;
import br.com.jm.tarefas.domain.TarefaRepository;
import br.com.jm.tarefas.domain.Usuario;
import br.com.jm.tarefas.domain.UsuarioRepository;
import br.com.jm.tarefas.infrastructure.PublicadorAtualizacaoTarefa;
import br.com.jm.tarefas.infrastructure.ServicoTransacional;

/**
 * Serviço de Gerenciamento de Tarefas
 */
@ServicoTransacional
public class GerenciamentoTarefasService {

    private static final Sort SORT_DATA_DEVIDA_ASC = new Sort(Sort.Direction.ASC,
            Arrays.asList("dataDevida"));

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicadorAtualizacaoTarefa publicadorAtualizacaoTarefa;

	/**
	 * Busca todas as tarefas cadastradas
	 * 
	 * @return lista com todas as tarefas cadastradas
	 */
    public List<TarefaDTO> buscarTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll(SORT_DATA_DEVIDA_ASC);
        return MontadorTarefaDTO.montarTarefas(tarefas);
    }

	/**
	 * Busca as tarefas de um usuário responsável
	 * 
	 * @param responsavel
	 *            usuário responsável
	 * @return lista de tarefas do usuário responsável
	 */
    public List<TarefaDTO> buscarTarefasResponsavel(IdentificadorUsuarioDTO responsavel) {
        Usuario usuario = getUsuario(responsavel);
        List<Tarefa> tarefas = tarefaRepository.findByResponsavel(usuario, SORT_DATA_DEVIDA_ASC);
        return MontadorTarefaDTO.montarTarefas(tarefas);
    }

	/**
	 * Busca as tarefas por status
	 * 
	 * @param status
	 *            status da tarefa
	 * @return lista de tarefas em um determinado status
	 */
    public List<TarefaDTO> buscarTarefasStatus(StatusTarefaDTO status) {
        List<Tarefa> tarefas = tarefaRepository.findByStatus(
                StatusTarefa.valueOf(status.getNome()), SORT_DATA_DEVIDA_ASC);
        return MontadorTarefaDTO.montarTarefas(tarefas);
    }

	/**
	 * Cria uma nova tarefa
	 * 
	 * @param novaTarefa
	 *            informações da nova tarefa
	 * @return DTO com as informações da nova tarefa
	 * @throws ControleTarefasException
	 *             se ocorrer algum erro durante a criação da tarefa
	 */
    @Secured("ROLE_ADMINISTRADOR")
    public TarefaDTO criarTarefa(NovaTarefaDTO novaTarefa) throws ControleTarefasException {
        Usuario usuario = getUsuario(novaTarefa.getAutor());
        Long codigoTarefa = tarefaRepository.proximoCodigoTarefa();

        Tarefa tarefa = usuario.criarTarefa(new IdentificadorTarefa(codigoTarefa),
                novaTarefa.getTitulo(), novaTarefa.getDescricao(), novaTarefa.getDataDevida());
        tarefaRepository.save(tarefa);
        TarefaDTO dto = MontadorTarefaDTO.montarTarefa(tarefa);
        publicadorAtualizacaoTarefa.publicarAtualizacaoTarefa(dto);
        return dto;
    }

	/**
	 * Inicia a tarefa
	 * 
	 * @param acaoTarefa
	 *            informações para execução da ação de início na tarefa
	 * @return DTO com as informações da tarefa com ação de início executada
	 * @throws ControleTarefasException
	 *             se ocorrer algum erro ao executar a ação de início na tarefa
	 */
    public TarefaDTO iniciarTarefa(AcaoTarefaDTO acaoTarefa) throws ControleTarefasException {
        Usuario usuario = getUsuario(acaoTarefa.getIdentificadorResponsavel());
        Tarefa tarefa = getTarefa(acaoTarefa.getIdentificadorTarefa());
        tarefa.iniciar(usuario);
        TarefaDTO dto = MontadorTarefaDTO.montarTarefa(tarefa);
        publicadorAtualizacaoTarefa.publicarAtualizacaoTarefa(dto);
        return dto;
    }

	/**
	 * Conclui a tarefa
	 * 
	 * @param acaoTarefa
	 *            informações para execução da ação de conclusão na tarefa
	 * @return DTO com as informações da tarefa com ação de conclusão executada
	 * @throws ControleTarefasException
	 *             se ocorrer algum erro ao executar a ação de conclusão na tarefa
	 */
    public TarefaDTO concluirTarefa(AcaoTarefaDTO acaoTarefa) throws ControleTarefasException {
        Usuario usuario = getUsuario(acaoTarefa.getIdentificadorResponsavel());
        Tarefa tarefa = getTarefa(acaoTarefa.getIdentificadorTarefa());
        tarefa.concluir(usuario);
        TarefaDTO dto = MontadorTarefaDTO.montarTarefa(tarefa);
        publicadorAtualizacaoTarefa.publicarAtualizacaoTarefa(dto);
        return dto;
    }

    /**
	 * Descarta a tarefa
	 * 
	 * @param acaoTarefa
	 *            informações para execução da ação de descarte na tarefa
	 * @return DTO com as informações da tarefa com ação de descarte executada
	 * @throws ControleTarefasException
	 *             se ocorrer algum erro ao executar a ação de descarte na tarefa
	 */
    public TarefaDTO descartarTarefa(AcaoTarefaDTO acaoTarefa) throws ControleTarefasException {
        Usuario usuario = getUsuario(acaoTarefa.getIdentificadorResponsavel());
        Tarefa tarefa = getTarefa(acaoTarefa.getIdentificadorTarefa());
        tarefa.descartar(usuario);
        TarefaDTO dto = MontadorTarefaDTO.montarTarefa(tarefa);
        publicadorAtualizacaoTarefa.publicarAtualizacaoTarefa(dto);
        return dto;
    }

    /*
     * Recupera o usuário pelo identificador
     */
    private Usuario getUsuario(IdentificadorUsuarioDTO identificador) {
        IdentificadorUsuario identificadorUsuario = new IdentificadorUsuario(
                identificador.getEmail());

        Optional<Usuario> usuario = usuarioRepository.findByIdentificador(identificadorUsuario);
        if (!usuario.isPresent()) {
            throw new IllegalArgumentException(String.format(
                    "Usuário com identifcador [%s] não encontrado!", identificadorUsuario));
        }
        return usuario.get();
    }

    /*
     * Recupera a tarefa pelo identificador
     */
    private Tarefa getTarefa(IdentificadorTarefaDTO identificador) {
        IdentificadorTarefa identificadorTarefa = new IdentificadorTarefa(identificador.getCodigo());

        Optional<Tarefa> tarefa = tarefaRepository.findByIdentificador(identificadorTarefa);

        if (!tarefa.isPresent()) {
            throw new IllegalArgumentException(String.format(
                    "Tarefa com identifcador [%s] não encontrada!", identificadorTarefa));
        }
        return tarefa.get();
    }

}
