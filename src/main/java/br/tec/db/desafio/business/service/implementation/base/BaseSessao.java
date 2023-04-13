package br.tec.db.desafio.business.service.implementation.base;

import br.tec.db.desafio.business.service.implementation.validacao.FactoryValidacao;
import br.tec.db.desafio.repository.AssociadoRepository;
import br.tec.db.desafio.repository.PautaRepository;
import br.tec.db.desafio.repository.SessaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseSessao {
    protected final SessaoRepository sessaoRepository;
    protected final PautaRepository pautaRepository;
    protected final AssociadoRepository associadoRepository;
    @Autowired
    protected ModelMapper modelMapper;


    public BaseSessao(SessaoRepository sessaoRepository,
                      PautaRepository pautaRepository,
                      AssociadoRepository associadoRepository) {
        this.sessaoRepository = sessaoRepository;
        this.pautaRepository = pautaRepository;
        this.associadoRepository = associadoRepository;
    }




    protected static final FactoryValidacao valida = new FactoryValidacao();


}
