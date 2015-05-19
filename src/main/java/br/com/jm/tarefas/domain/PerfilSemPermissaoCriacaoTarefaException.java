package br.com.jm.tarefas.domain;

/**
 * Exceção que representa a falta de permissão do perfil para criação de tarefas
 */
public class PerfilSemPermissaoCriacaoTarefaException extends ControleTarefasException {

    private static final long serialVersionUID = 1L;

    private final PerfilUsuario perfilUsuario;

    public PerfilSemPermissaoCriacaoTarefaException(PerfilUsuario perfilUsuario) {
        super(String.format("Perfil [%s] sem permissão para criação de tarefas!", perfilUsuario));
        this.perfilUsuario = perfilUsuario;

    }

    public PerfilUsuario getPerfilUsuario() {
        return perfilUsuario;
    }

}
