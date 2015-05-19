package br.com.jm.tarefas.infrastructure.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Implementação de verificação da condição para inicialização(carga) de dados
 * de exemplo no banco de dados
 */
public class InicializadorBDCondition implements Condition {

    public static final String INICIALIZADOR_BD_PROPERTY_KEY = "inicializadorBD";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.valueOf(System.getProperty(INICIALIZADOR_BD_PROPERTY_KEY));
    }

}
