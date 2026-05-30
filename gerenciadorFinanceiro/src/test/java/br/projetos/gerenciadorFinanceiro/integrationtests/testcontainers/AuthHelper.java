package br.projetos.gerenciadorFinanceiro.integrationtests.testcontainers;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

public class AuthHelper {
	
	public static void register() {
		given()
			.contentType(ContentType.JSON)
			.body("""
				     {
				       "login": "bruno2",
				       "password": "123",
				       "role": "ADMIN"
				     }
				 """)
			.when()
				.post("/api/auth/register")
			.then()
				.statusCode(200);
	}

    public static String loginAndGetToken() {
        return given()
        		.contentType(ContentType.JSON)
	            .body("""
	                {
	                  "login": "admin",
	                  "password": "admin123"
	                }
	            	 """)
	            .when()
                	.post("/api/auth/login")
                .then()
                	.statusCode(200)
                	.extract()
                	.path("accessToken");
    }
}

