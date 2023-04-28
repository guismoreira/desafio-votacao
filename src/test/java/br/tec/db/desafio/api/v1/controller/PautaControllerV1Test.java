package br.tec.db.desafio.api.v1.controller;

import br.tec.db.desafio.api.v1.dto.pauta.PautaRequestV1;
import br.tec.db.desafio.api.v1.dto.pauta.PautaResponseV1;
import br.tec.db.desafio.business.service.IPautaService;
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
public class PautaControllerV1Test {
    private static final String ASSUNTO = "tema da pauta";
    private static final String BASE_URI = "http://localhost";
    private static final String BASE_PATH ="/api/v1/pauta";
    @LocalServerPort
    private int port;

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
    void devePersistirPautaComSucesso() throws JsonProcessingException {
        PautaRequestV1 pautaRequestV1 =
                new PautaRequestV1(
                        ASSUNTO);

        Gson gson = new Gson();

        String request = gson.toJson(pautaRequestV1);

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
                .body("assunto", notNullValue());


    }

}

