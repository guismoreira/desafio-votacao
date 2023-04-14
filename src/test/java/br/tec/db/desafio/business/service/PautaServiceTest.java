package br.tec.db.desafio.business.service;

import br.tec.db.desafio.api.v1.dto.pauta.PautaRequestV1;
import br.tec.db.desafio.api.v1.dto.pauta.PautaResponseV1;
import br.tec.db.desafio.business.domain.Pauta;
import br.tec.db.desafio.business.service.implementation.PautaService;
import br.tec.db.desafio.repository.PautaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {
    @Mock
    PautaRepository pautaRepository;
    private static final String ASSUNTO = "tema da pauta";
    @Spy
    ModelMapper modelMapper;

    @Test
    void devePersistirPautaComSucesso() {
        PautaService pautaServiceImpl = new PautaService(pautaRepository, modelMapper);

        PautaRequestV1 pautaRequestV1 = new PautaRequestV1(ASSUNTO);
        Pauta pautaRequestV1ToPauta =modelMapper.map(pautaRequestV1,Pauta.class);

        PautaResponseV1 actual = pautaServiceImpl.criarUmaNovaPauta(pautaRequestV1);

        PautaResponseV1 expected =modelMapper.map(pautaRequestV1ToPauta,PautaResponseV1.class);

        assertThat(expected).usingRecursiveComparison().isEqualTo(
                actual
        );
    }
}
