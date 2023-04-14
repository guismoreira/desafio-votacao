package br.tec.db.desafio.business.service.implementation;


import br.tec.db.desafio.api.v1.dto.sessao.request.SessaoParaCriarRequestV1;
import br.tec.db.desafio.api.v1.dto.sessao.request.SessaoParaSaberTotalVotosRequestV1;
import br.tec.db.desafio.api.v1.dto.sessao.request.SessaoParaVotarRequestV1;
import br.tec.db.desafio.api.v1.dto.sessao.response.SessaoCriadaResponseV1;
import br.tec.db.desafio.api.v1.dto.sessao.response.SessaoTotalVotosResponseV1;
import br.tec.db.desafio.api.v1.dto.sessao.response.SessaoVotadaResponseV1;
import br.tec.db.desafio.business.domain.Associado;
import br.tec.db.desafio.business.domain.Pauta;
import br.tec.db.desafio.business.domain.Sessao;
import br.tec.db.desafio.business.service.ISessaoService;
import br.tec.db.desafio.business.service.implementation.validacao.FactoryValidacao;
import br.tec.db.desafio.repository.AssociadoRepository;
import br.tec.db.desafio.repository.PautaRepository;
import br.tec.db.desafio.repository.SessaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SessaoService implements ISessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final AssociadoRepository associadoRepository;
    private final ModelMapper modelMapper;
    private static final FactoryValidacao valida = new FactoryValidacao();


    public SessaoService(SessaoRepository sessaoRepository, PautaRepository pautaRepository, AssociadoRepository associadoRepository, ModelMapper modelMapper) {
        this.sessaoRepository = sessaoRepository;
        this.pautaRepository = pautaRepository;
        this.associadoRepository = associadoRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public SessaoCriadaResponseV1 criarUmaNovaSessao(SessaoParaCriarRequestV1 sessaoRequestV1) {

        Sessao sessaoToCreate = modelMapper.map(sessaoRequestV1, Sessao.class);

        Long idPauta = pautaRepository.findIdByAssunto
                (sessaoToCreate.getPauta().getAssunto());
        valida.validarSessaoInexistente(idPauta);
        Pauta pautaEncontrada = pautaRepository.findPautaByAssunto
                (sessaoToCreate.getPauta().getAssunto());

        valida.validarSessaoRepetida(pautaEncontrada.getSessao());
        sessaoToCreate.setPauta(pautaEncontrada);
        sessaoRepository.save(sessaoToCreate);

        return modelMapper.map(sessaoToCreate, SessaoCriadaResponseV1.class);
    }

    @Override
    public SessaoVotadaResponseV1 votarEmUmaSessao(SessaoParaVotarRequestV1 sessaoRequestV1) {
        Sessao sessaoToCreate = modelMapper.map(sessaoRequestV1, Sessao.class);
        Long idPauta = pautaRepository.findIdByAssunto
                (sessaoToCreate.getPauta().getAssunto());
        valida.validarSessaoInexistente(idPauta);
        Pauta pautaEncontrada = pautaRepository.findPautaByAssunto
                (sessaoToCreate.getPauta().getAssunto());


        Sessao sessaoEncontrada = sessaoRepository.findByPautaId
                (pautaEncontrada.getId());
        Associado associadoEncontrado = associadoRepository.findAssociadoByCpf(
                sessaoRequestV1.getCpf());

        Sessao associadoNaSessaoPorSessao = sessaoRepository.findSessoesByAssociadosIdAndPautaAssunto(
                associadoEncontrado.getId(),
                sessaoEncontrada.getPauta().getAssunto());



        valida.validarSessaoJaVotada(associadoNaSessaoPorSessao);
        valida.validarSessaoExpirada(sessaoEncontrada.getDuracao());


        sessaoEncontrada.addAssociado(associadoEncontrado);
        sessaoEncontrada.novoVoto(sessaoEncontrada,sessaoRequestV1);



        return modelMapper.map(sessaoEncontrada, SessaoVotadaResponseV1.class);

    }

    @Override
    public SessaoTotalVotosResponseV1 totalDeVotosDaSessao(SessaoParaSaberTotalVotosRequestV1 sessaoRequestV1) {


        Sessao sessaoToCreate = modelMapper.map(sessaoRequestV1, Sessao.class);


        Long idPauta = pautaRepository.findIdByAssunto
                (sessaoToCreate.getPauta().getAssunto());
        valida.validarSessaoInexistente(idPauta);

        Pauta pautaEncontrada = pautaRepository.findPautaByAssunto
                (sessaoRequestV1.getAssuntoPauta());

        Sessao sessaoEncontrada = sessaoRepository.findByPautaId
                (pautaEncontrada.getId());
        valida.validarSessaoResultado(sessaoEncontrada);


        return modelMapper.map(sessaoEncontrada, SessaoTotalVotosResponseV1.class);

    }


}
