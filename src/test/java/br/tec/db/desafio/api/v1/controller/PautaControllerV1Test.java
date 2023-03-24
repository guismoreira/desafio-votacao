package br.tec.db.desafio.api.v1.controller;

import br.tec.db.desafio.api.v1.dto.pauta.PautaRequestV1;
import br.tec.db.desafio.api.v1.dto.pauta.PautaResponseV1;
import br.tec.db.desafio.business.service.IPautaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PautaControllerV1Test {
    private static final String ASSUNTO = "tema da pauta";
    private static final String URI ="/api/v1/pauta";
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;
    }

    @Test
    void devePersistirPautaComSucesso() throws JsonProcessingException {
        PautaRequestV1 pautaRequestV1 =
                new PautaRequestV1(
                        ASSUNTO);

        String request = new ObjectMapper().writeValueAsString(pautaRequestV1);


        given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .post(URI)
                .then()
                .statusCode(201);


    }

}

