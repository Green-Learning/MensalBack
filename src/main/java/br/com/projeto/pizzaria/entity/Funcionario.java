package br.com.projeto.pizzaria.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Funcionario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "CPF")
    private String cpf;

    @OneToOne
    @JoinColumn(name = "usuario")
    private UserConta userConta;


    public Funcionario(){

    }

    public Funcionario(Long id, String nome, String telefone, String cpf, UserConta userConta) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.userConta = userConta;
    }
}
