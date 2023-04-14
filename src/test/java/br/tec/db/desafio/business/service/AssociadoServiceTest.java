package br.tec.db.desafio.business.service;


import br.tec.db.desafio.api.v1.dto.associado.AssociadoRequestV1;
import br.tec.db.desafio.api.v1.dto.associado.AssociadoResponseV1;
import br.tec.db.desafio.business.domain.Associado;
import br.tec.db.desafio.business.service.implementation.AssociadoService;
import br.tec.db.desafio.repository.AssociadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTest {
    @Mock
    AssociadoRepository associadoRepository;
    @Spy
    ModelMapper modelMapper;

    private static final String NOME = "guilherme";
    private static final String CPF = "12312366990";

    @Test
    void devePersistirPautaComSucesso() {
        AssociadoService associadoService = new AssociadoService(associadoRepository, modelMapper);

        AssociadoRequestV1 associadoRequestV1 = new AssociadoRequestV1(CPF, NOME);
        Associado associadoRequestV1ToAssociado =modelMapper.map(associadoRequestV1,Associado.class);

        AssociadoResponseV1 actual = associadoService.criarUmNovoAssociado(associadoRequestV1);

        AssociadoResponseV1 expected =modelMapper.map(associadoRequestV1ToAssociado, AssociadoResponseV1.class);

        assertThat(expected).usingRecursiveComparison().isEqualTo(
                actual
        );

    }
}
