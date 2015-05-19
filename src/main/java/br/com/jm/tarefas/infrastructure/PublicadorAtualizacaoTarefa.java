package br.com.jm.tarefas.infrastructure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import br.com.jm.tarefas.application.dto.TarefaDTO;

@Component
public class PublicadorAtualizacaoTarefa {

    private static final Log LOGGER = LogFactory.getLog(PublicadorAtualizacaoTarefa.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Publica a atualizacao/criacao da tarefa
     * 
     * @param dto
     *            com as informaćão de criacao/alteracao da tarefa
     */
    public void publicarAtualizacaoTarefa(TarefaDTO dto) {
        try {
            messagingTemplate.convertAndSend("/topic/updates", dto);
        } catch (MessagingException ex) {
            // Neste caso, apenas loga a erro de pulicação, garantindo a
            // efetivação de alteração na tarefa
            LOGGER.error(String.format("Erro ao publicar alteração na tarefa %s", dto), ex);
        }
    }

}
