INSERT IGNORE INTO associado (id, cpf, nome) values (1, '12312366990', 'guilherme');
INSERT IGNORE INTO associado (id, cpf, nome) values (2, '01205411229', 'tom cruise');
INSERT IGNORE INTO pauta (id, assunto) values (1, 'tema 1');
INSERT IGNORE INTO pauta (id, assunto) values (2, 'tema 2');

INSERT IGNORE INTO sessao (id, duracao, sessao_encerrada, total_votos_nao, total_votos_sim,
voto, pauta_id) values (1, '2033-03-23 16:55:53.174643', null, 0, 0, null, 2);

INSERT IGNORE INTO associado_sessoes (id, associados_id, sessoes_id) values (1, 2, 1);