package br.com.jm.tarefas.domain;

import java.time.LocalDate;

import br.com.jm.tarefas.domain.Tarefa.ConstrutorTarefa;

/**
 * Utilitário para a criação de dados de teste
 */
public class DadosExemplo {

    private GeradorCodigoTarefa geradorCodigoTarefa;

    private Usuario fulano;
    
    private Usuario sicrano;
    
    private Usuario beltrano;
    
    private Usuario johnDoe;

    private Tarefa tarefaCadastrada;

    private Tarefa tarefaIniciada;

    public DadosExemplo(GeradorCodigoTarefa codigoTarefaGenerator) {
        this.geradorCodigoTarefa = codigoTarefaGenerator;
        
        sicrano = usuario("Sicrano de Tal", new IdentificadorUsuario("sicrano@localhost"), "secret");
        
        beltrano = usuario("Beltrano de Tal", new IdentificadorUsuario("beltrano@localhost"),
                "secret");
        
        fulano = usuarioAdmin("Fulano de Tal", new IdentificadorUsuario("fulano@localhost"),
                "secret");
        
        johnDoe = usuarioAdmin("John Doe", new IdentificadorUsuario("johndoe@localhost"), "secret");

        
        tarefaCadastrada = tarefa(fulano, LocalDate.now().plusDays(5), "Realizar especificação",
                "Realizar especificação");

        
        tarefaIniciada = tarefa(fulano, LocalDate.now().plusDays(3), "Levantamento de requisitos",
                "Levantamento de requisitos");
        try {
        
        	tarefaIniciada.iniciar(sicrano);
        } catch (ControleTarefasException e) {
            
        	throw new IllegalStateException("Erro ao iniciar tarefa!", e);
        }
    }

    public Usuario usuario(String nome, IdentificadorUsuario identificadorUsuario, String senha) {
        return usuario(nome, identificadorUsuario, senha, PerfilUsuario.USUARIO);
    }

    public Usuario usuarioAdmin(String nome, IdentificadorUsuario identificadorUsuario, String senha) {
        return usuario(nome, identificadorUsuario, senha, PerfilUsuario.ADMINISTRADOR);
    }

    public Usuario getFulano() {
        return fulano;
    }

    public Usuario getSicrano() {
        return sicrano;
    }

    public Usuario getBeltrano() {
        return beltrano;
    }

    public Tarefa getTarefaCadastrada() {
        return tarefaCadastrada;
    }

    public Tarefa getTarefaIniciada() {
        return tarefaIniciada;
    }

    public Usuario getJohnDoe() {
        return johnDoe;
    }

    private Usuario usuario(String nome, IdentificadorUsuario identificadorUsuario, String senha,
            PerfilUsuario perfil) {
        return new Usuario(nome, identificadorUsuario, senha, perfil);

    }

    private Tarefa tarefa(Usuario autor, LocalDate dataDevida, String titulo, String descricao) {
        return new ConstrutorTarefa().autor(autor).dataDevida(dataDevida).descricao(descricao)
                .titulo(titulo)
                .identificador(new IdentificadorTarefa(geradorCodigoTarefa.proximoCodigoTarefa()))
                .construir();
    }
}
