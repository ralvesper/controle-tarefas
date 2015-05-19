package br.com.jm.tarefas.infrastructure.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Representa um usuário autenticado no sistema de Controle de Tarefas *
 */
public class UsuarioAutenticacao extends User {

    private static final long serialVersionUID = 1L;

    /**
     * Nome do usuário
     */
    private String nome;

    public UsuarioAutenticacao(String nome, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.nome = nome;
    }

    public UsuarioAutenticacao(String nome, String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return getUsername();
    }

}
