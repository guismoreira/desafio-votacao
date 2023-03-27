package br.tec.db.desafio.business.service.implementation.validacao.sessao.votar;


import br.tec.db.desafio.business.domain.Sessao;
import br.tec.db.desafio.exception.BusinessException;

import java.util.List;


public class ValidarSessaoJaVotada {



    public void validar(Sessao sessao) {
        if(sessao != null){
            throw new BusinessException("Associado já votou");
        }
    }


}
