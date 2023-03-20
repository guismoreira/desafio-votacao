package br.tec.db.desafio.api.v1.handler.dto;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HandlerBusinessExceptionResponse {
    private String dataHora;
    private Integer status;
    private String message;
}
