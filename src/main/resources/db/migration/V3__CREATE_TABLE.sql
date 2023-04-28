create table associado
    (id bigint not null auto_increment,
    cpf varchar(255),
    nome varchar(255),
primary key (id));

create table associado_sessao
    (associado_id bigint not null,
    sessao_id bigint not null);

create table pauta
    (id bigint not null auto_increment,
    assunto varchar(255),
primary key (id));

create table sessao
    (id bigint not null auto_increment,
    duracao datetime(6),
    sessao_encerrada bit,
    total_votos_nao integer not null,
    total_votos_sim integer not null,
    pauta_id bigint,
primary key (id));

alter table associado add constraint cpf_uk unique (cpf);

alter table associado_sessao add constraint sessao_id_fk
foreign key (sessao_id) references sessao (id);

alter table associado_sessao add constraint associado_id_fk
foreign key (associado_id) references associado (id);

alter table sessao add constraint pauta_id_fk
foreign key (pauta_id) references pauta (id);
