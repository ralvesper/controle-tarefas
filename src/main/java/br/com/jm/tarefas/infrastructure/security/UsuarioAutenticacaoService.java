package br.com.jm.tarefas.infrastructure.security;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.jm.tarefas.domain.IdentificadorUsuario;
import br.com.jm.tarefas.domain.Usuario;
import br.com.jm.tarefas.domain.UsuarioRepository;
import br.com.jm.tarefas.infrastructure.ServicoTransacional;

/**
 * Implementação de {@link UserDetailsService} para recuperação e criação do
 * usuário autenticado no sistema de Controle de Tarefas
 */
@ServicoTransacional
public class UsuarioAutenticacaoService implements UserDetailsService {

    private static final Log LOGGER = LogFactory.getLog(UsuarioAutenticacaoService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> opcional = usuarioRepository
                .findByIdentificador(new IdentificadorUsuario(username));
        if (!opcional.isPresent()) {
            throw new UsernameNotFoundException(String.format("Usuário [%s] não encontrado!",
                    username));
        }
        LOGGER.info(String.format("Usuário [%s] encontrado!", username));
        Usuario usuario = opcional.get();
        return new UsuarioAutenticacao(usuario.nome(), username, usuario.senha(),
                AuthorityUtils.createAuthorityList("ROLE_" + usuario.perfil().toString()));
    }
}
