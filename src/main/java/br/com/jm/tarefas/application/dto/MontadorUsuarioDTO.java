package br.com.jm.tarefas.application.dto;

import br.com.jm.tarefas.domain.Usuario;

/**
 * Utilitário para criação de DTO com informações de {@link Usuario}
 */
public class MontadorUsuarioDTO {

    private MontadorUsuarioDTO() {
        throw new UnsupportedOperationException("Instanciação não permitida!");
    }

    public static UsuarioDTO montarUsuario(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        IdentificadorUsuarioDTO identificadorDTO = new IdentificadorUsuarioDTO(usuario
                .identificador().getEmail());
        return new UsuarioDTO(usuario.nome(), identificadorDTO, usuario.dataCadastro(), usuario
                .perfil().toString());
    }

}
