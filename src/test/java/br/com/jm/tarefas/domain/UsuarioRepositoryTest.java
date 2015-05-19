package br.com.jm.tarefas.domain;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Testes para {@link UsuarioRepository}
 */
public class UsuarioRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByIdentificador() {
        Optional<Usuario> atual = usuarioRepository.findByIdentificador(getDadosExemplo()
                .getSicrano().identificador());

        Assert.assertTrue(atual.isPresent());
        Assert.assertEquals(getDadosExemplo().getSicrano(), atual.get());
    }

    @Test
    public void testFindByIdentificadorInexistente() {
        Optional<Usuario> atual = usuarioRepository.findByIdentificador(new IdentificadorUsuario(
                "xxxx@localhost"));

        Assert.assertFalse(atual.isPresent());
    }

    @Test
    public void testSave() {
        IdentificadorUsuario identificadorUsuario = new IdentificadorUsuario("jose@localhost");

        Usuario usuario = new Usuario("José Silva", identificadorUsuario, "secret",
                PerfilUsuario.USUARIO);
        usuarioRepository.save(usuario);
        usuarioRepository.flush();
    }

}
