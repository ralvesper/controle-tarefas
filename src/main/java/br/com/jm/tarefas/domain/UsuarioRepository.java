package br.com.jm.tarefas.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para manipulação da entidade {@link Usuario}
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Recupera um usuário através do identificador
     * 
     * @param identificadorUsuario
     *            identificador do usuário para recuperação
     * @return {@link Optional} correspondente encapsulando o usuário
     *         correspondente ao identificador
     */
    public Optional<Usuario> findByIdentificador(IdentificadorUsuario identificadorUsuario);

}
