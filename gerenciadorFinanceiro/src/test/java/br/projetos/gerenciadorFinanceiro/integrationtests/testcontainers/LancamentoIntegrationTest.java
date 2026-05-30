package br.projetos.gerenciadorFinanceiro.integrationtests.testcontainers;

import static br.projetos.gerenciadorFinanceiro.TestDataBuilder.umCartaoDTO;
import static br.projetos.gerenciadorFinanceiro.TestDataBuilder.umLancamentoDTO;
import static br.projetos.gerenciadorFinanceiro.TestDataBuilder.umaCategoriaDTO;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.enums.UserRole;
import br.projetos.gerenciadorFinanceiro.integrationtests.AbstractIntegrationTest;
import br.projetos.gerenciadorFinanceiro.model.User;
import br.projetos.gerenciadorFinanceiro.repository.RefreshTokenRepository;
import br.projetos.gerenciadorFinanceiro.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LancamentoIntegrationTest extends AbstractIntegrationTest {

	@Autowired
    UserRepository userRepository;
	
	@Autowired
	RefreshTokenRepository tokenRepository;
	
    @Autowired
    PasswordEncoder passwordEncoder;
	
    private String token;
    
    @BeforeEach
    void setup(@LocalServerPort int port) {
        RestAssured.port = port;
        userRepository.deleteAll();
        tokenRepository.deleteAll();
        createTestUser();
        token = AuthHelper.loginAndGetToken();
    }
    
    private void createTestUser() {
        userRepository.save(
            new User(
                "admin",
                passwordEncoder.encode("admin123"),
                UserRole.ADMIN
            )
        );
    }

    @Test
    @DisplayName("CENÁRIO: Criar lançamento → dado realmente salvo no banco")
    void deveCriarLancamentoComSucesso() {
    	// Create new Lancamento
        LancamentoDTO dto = umLancamentoDTO().build();
    	
    	given()
    		.log().all()
        	.contentType("application/json")
        	.accept(ContentType.JSON)
        	.header("Authorization", "Bearer " + token )
        	.body(dto)
        .when()
        	.post("/api/lancamento/inclui-lancamento")
        .then()
        	.log().all()
        	.statusCode(HttpStatus.CREATED.value())
        		.body("lancamento.descricao", equalTo("Compra no supermercado"))
        		.body("lancamento.valor", equalTo(150.50F));
    }
    
    @Test
    @DisplayName("CENÁRIO: Listar lançamentos com paginação → dados reais do banco")
    void cenarioListarLancamentosReais() {
        // DADO: Cria 3 lançamentos no banco
        for (int i = 1; i <= 3; i++) {
            given()
            	.log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token )
                .body(umLancamentoDTO().withDescricao("Compra " + i).build())
            .when()
                .post("/api/lancamento/inclui-lancamento")
            .then()
                .statusCode(HttpStatus.CREATED.value());
        }

        // QUANDO: Lista com paginação
        given()
        	.header("Authorization", "Bearer " + token )
            .queryParam("page", 0)
            .queryParam("size", 10)
        .when()
            .get("/api/lancamento/lista-lancamentos")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.lancamentoModelList", hasSize(greaterThanOrEqualTo(3)))
            .body("page.totalElements", greaterThanOrEqualTo(3));
    }

    @Test
    @DisplayName("CENÁRIO: Alterar lançamento → mudança persiste no banco")
    void cenarioAlterarLancamentoPersiste() {
        // DADO: Cria lançamento
        Integer id = given()
        	.header("Authorization", "Bearer " + token )	
            .contentType(ContentType.JSON)
            .body(umLancamentoDTO().withDescricao("Antes").build())
        .when()
            .post("/api/lancamento/inclui-lancamento")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract().path("lancamento.id");

        // QUANDO: Altera
        given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .body(umLancamentoDTO().withDescricao("Depois").withValor(999.99).build())
        .when()
            .put("/api/lancamento/altera-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.OK.value());

        // ENTÃO: Mudança persistiu (consulta mostra novo valor)
        given()
        	.header("Authorization", "Bearer " + token )
            .pathParam("id", id)
        .when()
            .get("/api/lancamento/consulta-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("lancamento.descricao", equalTo("Depois"))
            .body("lancamento.valor", equalTo(999.99f));
    }

    @Test
    @DisplayName("CENÁRIO: Excluir lançamento → realmente removido do banco")
    void cenarioExcluirRemoveDbanco() {
        // DADO: Cria lançamento
        Integer id = given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .body(umLancamentoDTO().build())
        .when()
            .post("/api/lancamento/inclui-lancamento")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract().path("lancamento.id");

        // QUANDO: Exclui
        given()
        	.header("Authorization", "Bearer " + token )
            .pathParam("id", id)
        .when()
            .delete("/api/lancamento/exclui-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.OK.value());

        // ENTÃO: Não existe mais no banco
        given()
        	.header("Authorization", "Bearer " + token )
            .pathParam("id", id)
        .when()
            .get("/api/lancamento/consulta-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    // ==================== VALIDAÇÃO DE REGRAS DE NEGÓCIO ====================

    @Test
    @DisplayName("CENÁRIO: Criar lançamento com categoria inexistente → sistema rejeita")
    void cenarioCategoriaInexistenteRejeitado() {
        var lancamento = umLancamentoDTO()
            .withCategoria(umaCategoriaDTO().withId(999L).buildDespesaDTO())  // Não existe
            .build();

        given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .body(lancamento)
        .when()
            .post("/api/lancamento/inclui-lancamento")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());  // Sistema valida e rejeita
    }

    @Test
    @DisplayName("CENÁRIO: Criar lançamento com cartão inexistente → sistema rejeita")
    void cenarioCartaoInexistenteRejeitado() {
        var lancamento = umLancamentoDTO()
            .withCartao(umCartaoDTO().withId(999L).build())  // Não existe
            .build();

        given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .body(lancamento)
        .when()
            .post("/api/lancamento/inclui-lancamento")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());  // Sistema valida e rejeita
    }

    // ==================== FLUXO COMPLETO DE VALIDAÇÕES ====================

    @Test
    @DisplayName("CENÁRIO: Dados inválidos → rejeitado antes de chegar no banco")
    void cenarioDadosInvalidosRejeitados() {
        var lancamentoInvalido = umLancamentoDTO()
            .withDescricao("")  // @NotBlank
            .withValor(-100)    // @Positive
            .build();

        given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .body(lancamentoInvalido)
        .when()
            .post("/api/lancamento/inclui-lancamento")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());

        // Validação: Nada foi salvo no banco
        given()
        	.header("Authorization", "Bearer " + token )
            .queryParam("page", 0)
            .queryParam("size", 100)
        .when()
            .get("/api/lancamento/lista-lancamentos")
        .then()
            .body("_embedded.lancamentoModelList", 
                not(hasItem(hasEntry("descricao", ""))));
    }

    // ==================== NEGOCIAÇÃO DE CONTEÚDO ====================

    @Test
    @DisplayName("CENÁRIO: Cliente solicita JSON → recebe JSON")
    void cenarioClienteSolicitaJson() {
        given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(umLancamentoDTO().build())
        .when()
            .post("/api/lancamento/inclui-lancamento")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("CENÁRIO: Cliente solicita XML → recebe XML")
    void cenarioClienteSolicitaXml() {
        given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .accept(ContentType.XML)
            .body(umLancamentoDTO().build())
        .when()
            .post("/api/lancamento/inclui-lancamento")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .contentType(ContentType.XML);
    }

    // ==================== FLUXO DE ERRO COMPLETO ====================

    @Test
    @DisplayName("CENÁRIO: Consultar ID inexistente → 404 com mensagem adequada")
    void cenarioConsultarInexistenteRetorna404() {
        given()
        	.header("Authorization", "Bearer " + token )
            .pathParam("id", 99999)
        .when()
            .get("/api/lancamento/consulta-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
            // Idealmente validaria também a estrutura da mensagem de erro
    }

    @Test
    @DisplayName("CENÁRIO: Alterar ID inexistente → 404")
    void cenarioAlterarInexistenteRetorna404() {
        given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .pathParam("id", 99999)
            .body(umLancamentoDTO().build())
        .when()
            .put("/api/lancamento/altera-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("CENÁRIO: Excluir ID inexistente → 404")
    void cenarioExcluirInexistenteRetorna404() {
        given()
        	.header("Authorization", "Bearer " + token )
            .pathParam("id", 99999)
        .when()
            .delete("/api/lancamento/exclui-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    // ==================== CASOS DE USO REAIS COMPLEXOS ====================

    @Test
    @DisplayName("CENÁRIO REAL: Usuário cria vários lançamentos e depois consulta todos")
    void cenarioRealCriarEListar() {
        // Simula usuário criando múltiplos lançamentos
        var ids = new Integer[5];
        for (int i = 0; i < 5; i++) {
            ids[i] = given()
            	.header("Authorization", "Bearer " + token )
                .contentType(ContentType.JSON)
                .body(umLancamentoDTO()
                    .withDescricao("Compra mensal " + (i + 1))
                    .withValor(100.0 * (i + 1))
                    .build())
            .when()
                .post("/api/lancamento/inclui-lancamento")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().path("lancamento.id");
        }

        // Usuário lista todos
        given()
        	.header("Authorization", "Bearer " + token )
            .queryParam("size", 100)
        .when()
            .get("/api/lancamento/lista-lancamentos")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.lancamentoModelList", hasSize(greaterThanOrEqualTo(5)));

        // Usuário consulta um específico
        given()
        	.header("Authorization", "Bearer " + token )
            .pathParam("id", ids[0])
        .when()
            .get("/api/lancamento/consulta-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("lancamento.descricao", equalTo("Compra mensal 1"));
    }

    @Test
    @DisplayName("CENÁRIO REAL: Usuário corrige um lançamento cadastrado errado")
    void cenarioRealCorrigirLancamento() {
        // Cadastrou errado
        Integer id = given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .body(umLancamentoDTO()
                .withDescricao("Compra errada")
                .withValor(50.0)
                .build())
        .when()
            .post("/api/lancamento/inclui-lancamento")
        .then()
        	.log().body()
            .statusCode(HttpStatus.CREATED.value())
            .extract().path("lancamento.id");

        // Percebeu o erro e corrigiu
        given()
        	.header("Authorization", "Bearer " + token )
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .body(umLancamentoDTO()
                .withDescricao("Compra corrigida")
                .withValor(150.0)
                .build())
        .when()
            .put("/api/lancamento/altera-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.OK.value());

        // Valida que a correção funcionou
        given()
        	.header("Authorization", "Bearer " + token )
            .pathParam("id", id)
        .when()
            .get("/api/lancamento/consulta-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("lancamento.descricao", equalTo("Compra corrigida"))
            .body("lancamento.valor", equalTo(150.0f));
    }

    @Test
    @DisplayName("CENÁRIO REAL: Usuário cadastra lançamento duplicado por engano e exclui um")
    void cenarioRealRemoverDuplicado() {
        // Cadastrou duas vezes sem querer
        Integer id1 = given()
        	.header("Authorization", "Bearer " + token )
        	.contentType(ContentType.JSON)
            .body(umLancamentoDTO().withDescricao("Duplicado").build())
            .when().post("/api/lancamento/inclui-lancamento")
            .then().statusCode(HttpStatus.CREATED.value()).extract().path("lancamento.id");

        Integer id2 = given()
        	.header("Authorization", "Bearer " + token )
        	.contentType(ContentType.JSON)
            .body(umLancamentoDTO().withDescricao("Duplicado").build())
            .when().post("/api/lancamento/inclui-lancamento")
            .then().statusCode(HttpStatus.CREATED.value()).extract().path("lancamento.id");

        // Remove um dos duplicados
        given()
        	.header("Authorization", "Bearer " + token )
            .pathParam("id", id2)
        .when()
            .delete("/api/lancamento/exclui-lancamento/{id}")
        .then()
            .statusCode(HttpStatus.OK.value());

        // Valida que um permanece e outro foi removido
        given()
        	.header("Authorization", "Bearer " + token )
        	.pathParam("id", id1)
            .when().get("/api/lancamento/consulta-lancamento/{id}")
            .then().statusCode(HttpStatus.OK.value());

        given()
        	.header("Authorization", "Bearer " + token )
        	.pathParam("id", id2)
            .when().get("/api/lancamento/consulta-lancamento/{id}")
            .then().statusCode(HttpStatus.NOT_FOUND.value());
    }
}