package br.tec.db.desafio.business.service;

import br.tec.db.desafio.api.v1.dto.sessao.request.SessaoParaCriarRequestV1;
import br.tec.db.desafio.api.v1.dto.sessao.response.SessaoCriadaResponseV1;
import br.tec.db.desafio.business.domain.Sessao;
import br.tec.db.desafio.business.service.implementation.SessaoService;
import br.tec.db.desafio.repository.AssociadoRepository;
import br.tec.db.desafio.repository.PautaRepository;
import br.tec.db.desafio.repository.SessaoRepository;
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
public class SessaoServiceTest {
    @Mock
    PautaRepository pautaRepository;
    @Mock
    SessaoRepository sessaoRepository;
    @Mock
    AssociadoRepository associadoRepository;
    @Spy
    ModelMapper modelMapper;


    private static final String ASSUNTO_PAUTA = "tema da pauta";
    private static final Long DURACAO_SESSAO = 2L;


    @Test
    void devePersistirSessaoComSucesso() {
        SessaoService sessaoServiceImpl = new SessaoService(sessaoRepository,pautaRepository, associadoRepository, modelMapper
        );

        SessaoParaCriarRequestV1 shouldSessaoRequestV1 =
                new SessaoParaCriarRequestV1(
                        ASSUNTO_PAUTA,
                        2L);

        Sessao shouldSessaoRequestV1ToSessao=modelMapper.map(shouldSessaoRequestV1,Sessao.class);


        when(pautaRepository.findPautaByAssunto(ASSUNTO_PAUTA)
        ).thenReturn(shouldSessaoRequestV1ToSessao.getPauta());


        SessaoParaCriarRequestV1 sessaoRequestV1 = new SessaoParaCriarRequestV1(
                ASSUNTO_PAUTA,
                DURACAO_SESSAO);
        Sessao sessaoRequestV1ToPauta =modelMapper.map(sessaoRequestV1,Sessao.class);

        SessaoCriadaResponseV1 actual = sessaoServiceImpl.criarUmaNovaSessao(sessaoRequestV1);

        SessaoCriadaResponseV1 expected =modelMapper.map(sessaoRequestV1ToPauta,SessaoCriadaResponseV1.class);

        assertThat(expected).usingRecursiveComparison().isEqualTo(
                actual
        );
    }
}

