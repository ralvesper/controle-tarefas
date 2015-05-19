package br.com.jm.tarefas.domain;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import br.com.jm.tarefas.domain.IdentificadorTarefa;
import br.com.jm.tarefas.domain.StatusTarefa;
import br.com.jm.tarefas.domain.Tarefa;
import br.com.jm.tarefas.domain.TarefaManipulacaoUsuarioInvalidoException;
import br.com.jm.tarefas.domain.TransicaoStatusTarefaException;
import br.com.jm.tarefas.domain.Tarefa.ConstrutorTarefa;

/**
 * Testes para {@link Tarefa}
 */
public class TarefaTest extends AbstractDomainTest {

	private String tituloTarefa = "Título";
	private String descricaoTarefa = "Descrição";
	private IdentificadorTarefa identificadorTarefa = new IdentificadorTarefa(
			1L);

	@Test
	public void testTarefaSemIdentificador() {
		exception.expect(NullPointerException.class);
		exception.expectMessage(MSG_IDENTIFICADOR_TAREFA_NULO);

		new ConstrutorTarefa().titulo(tituloTarefa).descricao(descricaoTarefa)
				.dataDevida(LocalDate.now()).autor(dadosExemplo.getFulano())
				.construir();
	}

	@Test
	public void testTarefaTituloNulo() {
		exception.expect(NullPointerException.class);
		exception.expectMessage(MSG_TITULO_TAREFA_NULO_VAZIO);

		new ConstrutorTarefa().identificador(identificadorTarefa)
				.descricao(descricaoTarefa).dataDevida(LocalDate.now())
				.autor(dadosExemplo.getFulano()).construir();
	}

	@Test
	public void testTarefaTituloVazio() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(MSG_TITULO_TAREFA_NULO_VAZIO);

		new ConstrutorTarefa().identificador(identificadorTarefa).titulo("")
				.descricao(descricaoTarefa).dataDevida(LocalDate.now())
				.autor(dadosExemplo.getFulano()).construir();
	}

	@Test
	public void testTarefaDescricaoNula() {
		exception.expect(NullPointerException.class);
		exception.expectMessage(MSG_DESCRICAO_TAREFA_NULA_VAZIA);

		new ConstrutorTarefa().identificador(identificadorTarefa)
				.titulo(tituloTarefa).dataDevida(LocalDate.now())
				.autor(dadosExemplo.getFulano()).construir();
	}

	@Test
	public void testTarefaDescricaoVazia() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(MSG_DESCRICAO_TAREFA_NULA_VAZIA);

		new ConstrutorTarefa().identificador(identificadorTarefa)
				.titulo(tituloTarefa).descricao("").dataDevida(LocalDate.now())
				.autor(dadosExemplo.getFulano()).construir();
	}

	@Test
	public void testTarefaSemDataDevida() {
		exception.expect(NullPointerException.class);
		exception.expectMessage(MSG_DATA_DEVIDA_TAREFA_NULA);

		new ConstrutorTarefa().identificador(identificadorTarefa)
				.titulo(tituloTarefa).descricao(descricaoTarefa)
				.autor(dadosExemplo.getFulano()).construir();
	}

	@Test
	public void testTarefaSemAutor() {
		exception.expect(NullPointerException.class);
		exception.expectMessage(MSG_AUTOR_TAREFA_NULO);

		new ConstrutorTarefa().identificador(identificadorTarefa)
				.titulo(tituloTarefa).descricao(descricaoTarefa)
				.dataDevida(LocalDate.now()).construir();
	}

	@Test
	public void testTarefa() {
		Tarefa tarefa = new ConstrutorTarefa().identificador(identificadorTarefa)
				.titulo(tituloTarefa).descricao(descricaoTarefa)
				.autor(dadosExemplo.getFulano()).dataDevida(LocalDate.now())
				.construir();
		Assert.assertNotNull(tarefa);
		
		Assert.assertEquals(dadosExemplo.getFulano(), tarefa.autor());
		Assert.assertEquals(LocalDate.now(), tarefa.cadastro().toLocalDate());
		Assert.assertEquals(descricaoTarefa, tarefa.descricao());
		Assert.assertEquals(tituloTarefa, tarefa.titulo());
		Assert.assertEquals(StatusTarefa.CADASTRADA, tarefa.status());
	}
	
	@Test
	public void testIniciarTarefa() throws ControleTarefasException {
		Tarefa tarefa = dadosExemplo.getTarefaCadastrada();
		tarefa.iniciar(dadosExemplo.getSicrano());
		
		Assert.assertTrue(tarefa.isIniciada());
	}

	@Test
	public void testIniciarTarefaUsuarioInvalido() throws ControleTarefasException {
		exception.expect(TarefaManipulacaoUsuarioInvalidoException.class);
		exception.expectMessage("não pode ser manipulada pelo usuário");
		Tarefa tarefa = dadosExemplo.getTarefaCadastrada();
		
		tarefa.iniciar(dadosExemplo.getSicrano());
		
		tarefa.iniciar(dadosExemplo.getBeltrano());
	}
	
	@Test
	public void testIniciarTarefaIniciada() throws ControleTarefasException {
		exception.expect(TransicaoStatusTarefaException.class);
		exception.expectMessage("Transição do status [INICIADA] para status [INICIADA] não permitida!");
		Tarefa tarefa = dadosExemplo.getTarefaCadastrada();
		
		tarefa.iniciar(dadosExemplo.getSicrano());
		
		tarefa.iniciar(dadosExemplo.getSicrano());
	}
}
