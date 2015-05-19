package br.com.jm.tarefas.infrastructure.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.jm.tarefas.infrastructure.InicializadorBD;

/**
 * Configurações Gerais
 * */
@Configuration
@ComponentScan(basePackages = "br.com.jm.tarefas")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "br.com.jm.tarefas.domain" })
@Import(DevConfig.class)
public class Config {

    @Bean
    @Profile("prod")
    public DataSource dataSource() {
        DriverManagerDataSource dmds = new DriverManagerDataSource();
        dmds.setUrl("jdbc:postgresql://localhost:5432/ctrl_tarefas");
        dmds.setUsername("ctrl_tarefas");
        dmds.setPassword("secret");
        dmds.setDriverClassName("org.postgresql.Driver");
        return dmds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    @Profile("prod")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setDataSource(dataSource());
        return emfb;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @Conditional(InicializadorBDCondition.class)
    public InicializadorBD databaseLoader() {
        return new InicializadorBD();
    }

    @Bean
    public Boolean inicializadorBD() {
        return Boolean.valueOf(System
                .getProperty(InicializadorBDCondition.INICIALIZADOR_BD_PROPERTY_KEY));
    }
}
