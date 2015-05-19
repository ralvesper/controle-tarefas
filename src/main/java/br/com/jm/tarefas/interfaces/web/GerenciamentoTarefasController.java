package br.com.jm.tarefas.interfaces.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jm.tarefas.application.GerenciamentoTarefasService;
import br.com.jm.tarefas.application.dto.AcaoTarefaDTO;
import br.com.jm.tarefas.application.dto.IdentificadorUsuarioDTO;
import br.com.jm.tarefas.application.dto.NovaTarefaDTO;
import br.com.jm.tarefas.application.dto.StatusTarefaDTO;
import br.com.jm.tarefas.application.dto.TarefaDTO;
import br.com.jm.tarefas.domain.ControleTarefasException;
import br.com.jm.tarefas.infrastructure.security.UsuarioAutenticacao;
import br.com.jm.tarefas.interfaces.web.dto.MensagemDTO;

/**
 * Controller que define a interface REST com as operações para gerenciamento de
 * tarefas
 */
@RestController
@RequestMapping(value = "/protected/tarefas", produces = { MediaType.APPLICATION_JSON_VALUE })
public class GerenciamentoTarefasController {

    private static final Log LOGGER = LogFactory.getLog(GerenciamentoTarefasController.class);

    private static final String MENSAGEM_OPERACAO_SUCESSO = "Operação realizada com sucesso!";

    @Autowired
    private GerenciamentoTarefasService service;

    @Secured("ROLE_ADMINISTRADOR")
    @RequestMapping(value = "/buscar", method = RequestMethod.GET)
    public List<TarefaDTO> getTarefas() {

        return service.buscarTarefas();
    }

    @RequestMapping(value = "/buscar/responsavel/{usuario}", method = RequestMethod.GET)
    public List<TarefaDTO> getTarefasUsuario(@PathVariable("usuario") String usuario,
            @AuthenticationPrincipal UsuarioAutenticacao autenticacao) {

        List<TarefaDTO> tarefas = new ArrayList<TarefaDTO>();
        if (autenticacao.getUsername().equalsIgnoreCase(usuario)) {
            tarefas = service.buscarTarefasResponsavel(new IdentificadorUsuarioDTO(autenticacao
                    .getEmail()));
        }
        return tarefas;
    }

    @RequestMapping(value = "/buscar/status/{status}", method = RequestMethod.GET)
    public List<TarefaDTO> getTarefasStatus(@PathVariable("status") StatusTarefaDTO status) {

        return service.buscarTarefasStatus(status);
    }

    @RequestMapping(value = "/{codigoTarefa}/iniciar", method = RequestMethod.PUT)
    public MensagemDTO iniciarTarefa(@AuthenticationPrincipal UsuarioAutenticacao usuario,
            @PathVariable("codigoTarefa") Long codigoTarefa) {

        try {

            service.iniciarTarefa(new AcaoTarefaDTO(codigoTarefa, usuario.getEmail()));
            return MensagemDTO.newMensagemSucesso(MENSAGEM_OPERACAO_SUCESSO);

        } catch (ControleTarefasException ex) {
            LOGGER.warn(ex.getMessage(), ex);
            return MensagemDTO.newMensagemErro(ex.getMessage());
        } catch (RuntimeException ex) {
            String mensagem = String.format("Erro ao iniciar a tarefa [%s]!", codigoTarefa);
            LOGGER.error(mensagem, ex);
            return MensagemDTO.newMensagemErro(mensagem);
        }
    }

    @RequestMapping(value = "/{codigoTarefa}/concluir", method = RequestMethod.PUT)
    public MensagemDTO concluirTarefa(@AuthenticationPrincipal UsuarioAutenticacao usuario,
            @PathVariable("codigoTarefa") Long codigoTarefa) {

        try {

            service.concluirTarefa(new AcaoTarefaDTO(codigoTarefa, usuario.getEmail()));
            return MensagemDTO.newMensagemSucesso(MENSAGEM_OPERACAO_SUCESSO);

        } catch (ControleTarefasException ex) {
            LOGGER.warn(ex.getMessage(), ex);
            return MensagemDTO.newMensagemErro(ex.getMessage());
        } catch (RuntimeException ex) {
            String mensagem = String.format("Erro ao concluir a tarefa [%s]!", codigoTarefa);
            LOGGER.error(mensagem, ex);
            return MensagemDTO.newMensagemErro(mensagem);
        }
    }

    @RequestMapping(value = "/{codigoTarefa}/descartar", method = RequestMethod.PUT)
    public MensagemDTO descartarTarefa(@AuthenticationPrincipal UsuarioAutenticacao usuario,
            @PathVariable("codigoTarefa") Long codigoTarefa) {

        try {

            service.descartarTarefa(new AcaoTarefaDTO(codigoTarefa, usuario.getEmail()));
            return MensagemDTO.newMensagemSucesso(MENSAGEM_OPERACAO_SUCESSO);

        } catch (ControleTarefasException ex) {
            LOGGER.warn(ex.getMessage(), ex);
            return MensagemDTO.newMensagemErro(ex.getMessage());
        } catch (RuntimeException ex) {
            String mensagem = String.format("Erro ao descartar a tarefa [%s]!", codigoTarefa);
            LOGGER.error(mensagem, ex);
            return MensagemDTO.newMensagemErro(mensagem);
        }
    }

    @Secured("ROLE_ADMINISTRADOR")
    @RequestMapping(value = "/criar", method = RequestMethod.POST)
    public MensagemDTO criarTarefa(@AuthenticationPrincipal UsuarioAutenticacao usuario,
            @RequestBody NovaTarefaDTO novaTarefa) {

        try {

            novaTarefa.setAutor(new IdentificadorUsuarioDTO(usuario.getEmail()));
            service.criarTarefa(novaTarefa);
            return MensagemDTO.newMensagemSucesso(MENSAGEM_OPERACAO_SUCESSO);

        } catch (ControleTarefasException ex) {
            LOGGER.warn(ex.getMessage(), ex);
            return MensagemDTO.newMensagemErro(ex.getMessage());
        } catch (RuntimeException ex) {
            String mensagem = "Erro ao criar tarefa!";
            LOGGER.error(mensagem, ex);
            return MensagemDTO.newMensagemErro(mensagem);
        }
    }

}
