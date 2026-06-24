package br.projetos.gerenciadorFinanceiro.integrationtests;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import br.projetos.gerenciadorFinanceiro.AbstractTest;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest extends AbstractTest{

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
        	Startables.deepStart(Stream.of(postgres)).join();

            Map<String, Object> properties = Map.of(
                "spring.datasource.url", postgres.getJdbcUrl(),
                "spring.datasource.username", postgres.getUsername(),
                "spring.datasource.password", postgres.getPassword()
            );

            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            environment.getPropertySources()
            		   .addFirst(new MapPropertySource("testcontainers", properties)
            );
        }
    }
}