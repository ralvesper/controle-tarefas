package br.com.jm.tarefas.domain;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import br.com.jm.tarefas.domain.IdentificadorTarefa;
import br.com.jm.tarefas.domain.IdentificadorUsuario;
import br.com.jm.tarefas.domain.PerfilSemPermissaoCriacaoTarefaException;
import br.com.jm.tarefas.domain.PerfilUsuario;
import br.com.jm.tarefas.domain.Tarefa;
import br.com.jm.tarefas.domain.Usuario;

/**
 * Testes para {@link Usuario}
 */
public class UsuarioTest extends AbstractDomainTest {

    private String descricaoTarefa = "Descrição";
    private String tituloTarefa = "Título";
    private IdentificadorTarefa identificadorTarefa = new IdentificadorTarefa(1L);
    private String nomeUsuario = "Fulano da Silva";
    private IdentificadorUsuario identificadorUsuario = new IdentificadorUsuario("fulano@localhost");
    private String senhaUsuario = "secret";

    @Test
    public void testUsuarioNomeNulo() {
        exception.expect(NullPointerException.class);
        exception.expectMessage(MSG_NOME_USUARIO_NULO_VAZIO);
        new Usuario(null, identificadorUsuario, senhaUsuario, PerfilUsuario.USUARIO);

    }

    @Test
    public void testUsuarioNomeVazio() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(MSG_NOME_USUARIO_NULO_VAZIO);
        new Usuario("", identificadorUsuario, "secret", PerfilUsuario.USUARIO);
    }

    @Test
    public void testUsuarioSemIdentificador() {
        exception.expect(NullPointerException.class);
        exception.expectMessage(MSG_IDENTIFICADOR_USUARIO_NULO);

        new Usuario(nomeUsuario, null, senhaUsuario, PerfilUsuario.USUARIO);
    }

    @Test
    public void testUsuarioSenhaNula() {
        exception.expect(NullPointerException.class);
        exception.expectMessage(MSG_SENHA_USUARIO_NULA_VAZIA);
        new Usuario(nomeUsuario, identificadorUsuario, null, PerfilUsuario.USUARIO);
    }

    @Test
    public void testUsuarioSenhaVazia() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(MSG_SENHA_USUARIO_NULA_VAZIA);
        new Usuario(nomeUsuario, identificadorUsuario, "", PerfilUsuario.USUARIO);
    }

    @Test
    public void testUsuarioSemPerfil() {
        exception.expect(NullPointerException.class);
        exception.expectMessage(MSG_PERFIL_USUARIO_NULO);
        new Usuario(nomeUsuario, identificadorUsuario, senhaUsuario, null);
    }

    @Test
    public void testUsuarioSucesso() {
        Usuario usuario = new Usuario(nomeUsuario, identificadorUsuario, senhaUsuario,
                PerfilUsuario.USUARIO);
        Assert.assertNotNull(usuario);
        Assert.assertEquals(nomeUsuario, usuario.nome());
        Assert.assertEquals(identificadorUsuario, usuario.identificador());
        Assert.assertEquals(senhaUsuario, usuario.senha());
        Assert.assertEquals(PerfilUsuario.USUARIO, usuario.perfil());
        Assert.assertEquals(LocalDate.now(), usuario.dataCadastro());
    }

    @Test
    public void testCriarTarefaSemPermissao() throws PerfilSemPermissaoCriacaoTarefaException {
        exception.expect(PerfilSemPermissaoCriacaoTarefaException.class);
        dadosExemplo.getSicrano().criarTarefa(identificadorTarefa, tituloTarefa, descricaoTarefa,
                LocalDate.now());
    }

    @Test
    public void testCriarTarefaSucesso() throws PerfilSemPermissaoCriacaoTarefaException {
        Tarefa tarefa = dadosExemplo.getFulano().criarTarefa(identificadorTarefa, tituloTarefa,
                descricaoTarefa, LocalDate.now());
        Assert.assertNotNull(tarefa);
    }

    @Test
    public void testIsAdministradorVerdadeiro() {
        Assert.assertTrue(dadosExemplo.getFulano().isAdministrador());
    }

    @Test
    public void testIsAdministradorFalso() {
        Assert.assertFalse(dadosExemplo.getSicrano().isAdministrador());
    }

}
