package br.com.jm.tarefas.domain.comum;

/**
 * Interface para implementação de Construtores
 *
 * @param <T>
 *            tipo construído pela implementação do Construtor
 */
public interface Construtor<T> {

    public T construir();

}
