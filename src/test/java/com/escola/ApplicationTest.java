package com.escola;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springdoc.core.service.OpenAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.escola.domain.controller.AlunoController;
import com.escola.domain.repository.AlunoRepository;
import com.escola.domain.service.AlunoService;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Autowired
    private ApplicationContext context;

	@Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired(required = false)
    private OpenAPIService swaggerOpenAPI;

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoRepository alunoRepository;

	@Test
    public void noExceptionThrownDuringInitializationTest() {
        // Verifica se nenhuma exceção é lançada durante a inicialização
        assertDoesNotThrow(() -> Application.main(new String[]{}));
    }

    @Test
    public void applicationInitializationTest() {
        assertNotNull(context);
    }

	// Verifica se o bean DataSource foi carregado corretamente.
	// Isso garante que a configuração do banco de dados está correta e que a aplicação pode se conectar ao banco de dados.
	@Test
    public void dataSourceLoadsTest() {
        assertNotNull(dataSource);
    }

	// Verifica se o bean EntityManagerFactory foi carregado corretamente.
	// Isso garante que a configuração do JPA está correta e que a aplicação pode persistir e recuperar entidades do banco de dados.
    @Test
    public void entityManagerFactoryLoadsTest() {
        assertNotNull(entityManagerFactory);
    }

	// Verifica se o bean OpenAPI do Swagger foi carregado corretamente.
	// Isso garante que a documentação da API está configurada e disponível.
    @Test
    public void swaggerOpenAPILoadsTest() {
        assertNotNull(swaggerOpenAPI);
    }

	// Verifica se o bean do controller foi carregado corretamente.
	// Se o bean não for encontrado no contexto, isso indicará um problema na configuração ou escaneamento de componentes.
    @Test
    public void contextLoadsTest() {
        assertNotNull(alunoController);
    }

	// Verifica se o bean do serviço foi carregado corretamente.
    @Test
    public void alunoServiceLoadsTest() {
        assertNotNull(alunoService);
    }

	// Verifica se o bean do repositório foi carregado corretamente.
	// Se este bean não for carregado corretamente, isso pode indicar um problema com a configuração do banco de dados ou com a camada de persistência.
    @Test
    public void alunoRepositoryLoadsTest() {
        assertNotNull(alunoRepository);
    }

}
