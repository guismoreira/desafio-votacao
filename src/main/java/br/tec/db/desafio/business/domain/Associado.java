package br.tec.db.desafio.business.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Associado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "associado_sessao",
            joinColumns = { @JoinColumn(name = "associado_id") },
            inverseJoinColumns = { @JoinColumn(name = "sessao_id") })
    private List<Sessao> sessoes;

    public void addSessao(Sessao sessao) {
        this.sessoes.add(sessao);
        sessao.getAssociados().add(this);
    }

    @Column(unique = true)
    private String cpf;
    private String nome;

    public Associado(String cpf) {
    }
}
