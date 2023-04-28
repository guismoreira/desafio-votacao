package br.tec.db.desafio.api.client;


import br.tec.db.desafio.api.v1.dto.associado.client.AssociadoClient;
import br.tec.db.desafio.api.v1.dto.associado.client.AssociadoClientRequestV1;
import br.tec.db.desafio.api.v1.dto.associado.client.AssociadoClientResponseV1;
import br.tec.db.desafio.business.domain.Associado;
import br.tec.db.desafio.business.domain.enums.StatusCpf;

import br.tec.db.desafio.business.service.implementation.validacao.FactoryValidacao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Component
public class AssociadoStatusCpfClient {

    private static final FactoryValidacao valida = new FactoryValidacao();
    private final ModelMapper modelMapper;

    public AssociadoStatusCpfClient(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/statuscpfclient", method = RequestMethod.POST)
    public AssociadoClientResponseV1 getStatusCpfAssociado(
            @RequestBody AssociadoClientRequestV1 associadoClientRequestV1
            ) {
        valida.validarCpf(associadoClientRequestV1);

        AssociadoClient associadoClient = new AssociadoClient(
                associadoClientRequestV1.getCpf(),
                StatusCpf.randomStatusCpf());

        return modelMapper.map(associadoClient, AssociadoClientResponseV1.class);
    }


}
