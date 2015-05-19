package br.com.jm.tarefas.infrastructure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.jm.tarefas.domain.ControleTarefasException;

/**
 * Anotação (composta) para criação de serviços transacionais
 */
@Documented
@Service
@Transactional
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ServicoTransacional {

    Propagation propagation() default Propagation.REQUIRED;

    boolean readOnly() default false;

    Class<? extends Throwable>[] rollbackFor() default { ControleTarefasException.class };

    Class<? extends Throwable>[] noRollbackFor() default {};

}
