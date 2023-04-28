package br.tec.db.desafio.api.v1.controller;

import br.tec.db.desafio.api.v1.dto.sessao.request.SessaoParaCriarRequestV1;
import br.tec.db.desafio.api.v1.dto.sessao.request.SessaoParaSaberTotalVotosRequestV1;
import br.tec.db.desafio.api.v1.dto.sessao.request.SessaoParaVotarRequestV1;
import br.tec.db.desafio.api.v1.dto.sessao.response.SessaoTotalVotosResponseV1;
import br.tec.db.desafio.business.domain.enums.Voto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Sql("/insert.sql")
public class SessaoControllerV1Test {

    private static final String CPF = "01205411229";

    private static final String ASSUNTO_PAUTA_1 = "tema 1";
    private static final String ASSUNTO_PAUTA_2 = "tema 2";
    private static final Long DURACAO_SESSAO = 2L;
    private static final String BASE_URI = "http://localhost";
    private static final String BASE_PATH ="/api/v1/sessao";

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
    void devePersistirSessaoComSucesso() throws JsonProcessingException {
        SessaoParaCriarRequestV1 sessaoParaCriarRequestV1 =
                new SessaoParaCriarRequestV1(
                        ASSUNTO_PAUTA_1,
                        DURACAO_SESSAO);

        Gson gson = new Gson();

        String request = gson.toJson(sessaoParaCriarRequestV1);

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
                .body("assuntoPauta", notNullValue());
    }

    @Test
    void deveVotarEmUmaSessaoComSucesso() throws JsonProcessingException {
        SessaoParaVotarRequestV1 sessaoParaVotarRequestV1 =
                new SessaoParaVotarRequestV1(
                        Voto.SIM,
                        CPF,
                        ASSUNTO_PAUTA_2);

        Gson gson = new Gson();

        String request = gson.toJson(sessaoParaVotarRequestV1);

        given()
                .accept(ContentType.JSON)
                .body(request)
        .when()
                .contentType(ContentType.JSON)
                .post("votar")
        .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat()
                .body("assunto", notNullValue());
    }

    @Test
    void deveSaberTotalDeVotosDeUmaSessaoComSucesso() throws JsonProcessingException {

        SessaoParaSaberTotalVotosRequestV1 sessaoParaSaberTotalVotosRequestV1 =
                new SessaoParaSaberTotalVotosRequestV1(
                        ASSUNTO_PAUTA_2);

        SessaoTotalVotosResponseV1 sessaoTotalVotosResponseV1 =
                new SessaoTotalVotosResponseV1(
                        30,
                        20,
                        true
                );
        Gson gson = new Gson();

        String request = gson.toJson(sessaoParaSaberTotalVotosRequestV1);

        given()
                .accept(ContentType.JSON)
                .body(request)
        .when()
                .contentType(ContentType.JSON)
                .get()
        .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat()
                .body("totalVotosSim", notNullValue());
    }

}
