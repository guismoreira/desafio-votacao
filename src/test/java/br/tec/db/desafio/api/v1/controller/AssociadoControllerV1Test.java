package br.tec.db.desafio.api.v1.controller;

import br.tec.db.desafio.api.v1.dto.associado.AssociadoRequestV1;
import br.tec.db.desafio.api.v1.dto.associado.client.AssociadoClientRequestV1;
import br.tec.db.desafio.business.domain.enums.StatusCpf;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AssociadoControllerV1Test {
    @LocalServerPort
    private int port;
    private static final String NOME = "guilherme";
    private static final String CPF = "12312366990";
    private static final String BASE_URI = "http://localhost";
    private static final String BASE_PATH ="/api/v1/associado";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI=BASE_URI;
        RestAssured.basePath =BASE_PATH;
    }

    @BeforeEach
    public void init() {
        RestAssured.port = port;
    }

    @Test
    void devePersistirAssociadoComSucesso() {
        AssociadoRequestV1 associadoRequestV1 =
                new AssociadoRequestV1(
                        CPF,
                        NOME
                        );

        Gson gson = new Gson();

        String request = gson.toJson(associadoRequestV1);

        given()
            .accept(ContentType.JSON)
            .body(request)
        .when()
            .contentType(ContentType.JSON)
            .post()
        .then()
            .assertThat()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .assertThat()
            .body("cpf", notNullValue());

    }

    @Test
    void deveConsultarCpfAssociadoComSucesso() throws JsonProcessingException {
        AssociadoClientRequestV1 associadoClientRequestV1 =
                new AssociadoClientRequestV1(
                        CPF);

        Gson gson = new Gson();

        String request = gson.toJson(associadoClientRequestV1);
        given()
                .accept(ContentType.JSON)
                .body(request)
        .when()
                .contentType(ContentType.JSON)
                .post("statuscpf")
        .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat()
                .body("statusCpf", notNullValue());
    }

}

