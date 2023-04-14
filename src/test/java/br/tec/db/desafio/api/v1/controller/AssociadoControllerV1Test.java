package br.tec.db.desafio.api.v1.controller;

import br.tec.db.desafio.api.v1.dto.associado.AssociadoRequestV1;
import br.tec.db.desafio.api.v1.dto.associado.client.AssociadoClientRequestV1;
import br.tec.db.desafio.business.domain.enums.StatusCpf;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AssociadoControllerV1Test {
    private static final String NOME = "guilherme";
    private static final String CPF = "12312366990";
    private static final String URI ="/api/v1/associado";
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;
    }

    @Test
    void devePersistirAssociadoComSucesso() throws JsonProcessingException {
        AssociadoRequestV1 associadoRequestV1 =
                new AssociadoRequestV1(
                        CPF,
                        NOME
                        );


        String request = new ObjectMapper().writeValueAsString(associadoRequestV1);



        given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .post(URI)
                .then()
                .statusCode(201);


    }

    @Test
    void deveConsultarCpfAssociadoComSucesso() throws JsonProcessingException {
        AssociadoClientRequestV1 associadoRequestV1 =
                new AssociadoClientRequestV1(
                        CPF);


        String request = new ObjectMapper().writeValueAsString(associadoRequestV1);



        given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .post(URI.concat("/statuscpf"))
                .then()
                .statusCode(200);


    }

}

