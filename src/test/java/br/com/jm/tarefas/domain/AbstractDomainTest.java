package br.com.jm.tarefas.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import br.com.jm.tarefas.domain.DadosExemplo;

public abstract class AbstractDomainTest {

    protected static final String MSG_NOME_USUARIO_NULO_VAZIO = "Nome não deve ser nulo/vazio!";
    protected static final String MSG_IDENTIFICADOR_USUARIO_NULO = "Identificador do usuário não deve ser nulo!";
    protected static final String MSG_SENHA_USUARIO_NULA_VAZIA = "Senha não deve ser nula/vazia!";
    protected static final String MSG_PERFIL_USUARIO_NULO = "Perfil não deve ser nulo!";

    protected static final String MSG_IDENTIFICADOR_TAREFA_NULO = "Identificador da tarefa não deve ser nulo!";
    protected static final String MSG_TITULO_TAREFA_NULO_VAZIO = "Título da tarefa não deve ser nulo/vazio!";
    protected static final String MSG_DESCRICAO_TAREFA_NULA_VAZIA = "Descrição da tarefa não deve ser nula/vazio!";
    protected static final String MSG_DATA_DEVIDA_TAREFA_NULA = "Data devida da tarefa não deve ser nula!";
    protected static final String MSG_AUTOR_TAREFA_NULO = "Autor da tarefa não deve ser nulo!";

    protected DadosExemplo dadosExemplo;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void inicializar() {
        dadosExemplo = new DadosExemplo(new GeradorCodigoTarefaEmMemoria());
    }

}
