package br.com.jm.tarefas.interfaces.web.dto;

import java.io.Serializable;

/**
 * DTO que representa uma mensagem de retorno de processamento de uma 
 * requisição
 */
public class MensagemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Tipo tipo;

    private String mensagem;

    private MensagemDTO(Tipo tipo, String mensagem) {
        super();
        this.tipo = tipo;
        this.mensagem = mensagem;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    /**
     * Cria uma nova mensagem de {@link Tipo#SUCESSO}
     * 
     * @param mensagem
     *            conteúdo da mensagem
     * @return nova mensagem de sucesso
     */
    public static MensagemDTO newMensagemSucesso(String mensagem) {
        return new MensagemDTO(Tipo.SUCESSO, mensagem);
    }

    /**
     * Cria uma nova mensagem de {@link Tipo#AVISO}
     * 
     * @param mensagem
     *            conteúdo da mensagem
     * @return nova mensagem de aviso
     */
    public static MensagemDTO newMensagemAviso(String mensagem) {
        return new MensagemDTO(Tipo.AVISO, mensagem);
    }

    /**
     * Cria uma nova mensagem de {@link Tipo#ERRO}
     * 
     * @param mensagem
     *            conteúdo da mensagem
     * @return nova mensagem de erro
     */
    public static MensagemDTO newMensagemErro(String mensagem) {
        return new MensagemDTO(Tipo.ERRO, mensagem);
    }

    /**
     * Tipos de mensagens
     */
    public static enum Tipo {
        SUCESSO,

        AVISO,

        ERRO;
    }

}
