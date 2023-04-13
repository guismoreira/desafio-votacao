package br.tec.db.desafio.business.service.implementation;

import br.tec.db.desafio.api.v1.dto.associado.AssociadoRequestV1;
import br.tec.db.desafio.api.v1.dto.associado.AssociadoResponseV1;
import br.tec.db.desafio.business.domain.Associado;
import br.tec.db.desafio.business.service.IAssociadoService;
import br.tec.db.desafio.business.service.implementation.validacao.FactoryValidacao;
import br.tec.db.desafio.repository.AssociadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class AssociadoService implements IAssociadoService {
    private final AssociadoRepository associadoRepository;
    private final ModelMapper modelMapper;
    
    private static final FactoryValidacao valida = new FactoryValidacao();

    public AssociadoService(AssociadoRepository associadoRepository, ModelMapper modelMapper) {
        this.associadoRepository = associadoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AssociadoResponseV1 criarUmNovoAssociado(AssociadoRequestV1 associadoRequestV1) {
        valida.validarCpf(associadoRequestV1);
        Associado associadoToCreate = modelMapper.map(associadoRequestV1, Associado.class);
        Associado verificaAssociado = associadoRepository.findAssociadoByCpf(associadoToCreate.getCpf());
        valida.validarNaoPodeSerNulo(verificaAssociado);
        return modelMapper.map(associadoToCreate, AssociadoResponseV1.class);


    }


}
