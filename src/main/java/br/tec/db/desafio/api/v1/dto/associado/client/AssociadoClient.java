package br.tec.db.desafio.api.v1.dto.associado.client;

import br.tec.db.desafio.business.domain.enums.StatusCpf;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoClient {
    private String cpf;
    private StatusCpf statusCpf;

}
