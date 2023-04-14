package br.tec.db.desafio.business.service.implementation;


import br.tec.db.desafio.api.v1.dto.pauta.PautaRequestV1;
import br.tec.db.desafio.api.v1.dto.pauta.PautaResponseV1;
import br.tec.db.desafio.business.domain.Pauta;
import br.tec.db.desafio.business.service.IPautaService;
import br.tec.db.desafio.business.service.implementation.validacao.FactoryValidacao;
import br.tec.db.desafio.repository.PautaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PautaService implements IPautaService {
    private final PautaRepository pautaRepository;
    private static final FactoryValidacao valida = new FactoryValidacao();
    private final ModelMapper modelMapper;
    public PautaService(PautaRepository pautaRepository, ModelMapper modelMapper) {
        this.pautaRepository = pautaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PautaResponseV1 criarUmaNovaPauta(PautaRequestV1 pautaRequestV1) {

        Pauta pautaToCreate = modelMapper.map(pautaRequestV1, Pauta.class);
        Pauta verificaPauta = pautaRepository.findPautaByAssunto(pautaRequestV1.getAssunto());
        valida.validarJaExistente(verificaPauta);
        pautaRepository.save(pautaToCreate);

        return modelMapper.map(pautaToCreate, PautaResponseV1.class);
    }


}
