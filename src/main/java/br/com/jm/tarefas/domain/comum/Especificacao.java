package br.com.jm.tarefas.domain.comum;

/**
 * Interface simplificada para implementações de especificações
 * 
 * @param <T>
 *            tipo parametrizável tratado pela especificação
 */
public interface Especificacao<T> {

    /**
     * Verifica se o objeto recebido satisfaz a condição implementada pela
     * especificação
     * 
     * @param tipo
     *            objeto para verificação
     * @return true se o objeto recebido satisfaz a especificação, false caso
     *         contrário
     */
    boolean isSatisfeitoPor(T tipo);

}
