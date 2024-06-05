package br.com.projeto.pizzaria.dto;

import br.com.projeto.pizzaria.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    private String nome;

    private String telefone;

    private String cpf;

    private Roles roles;

    @JsonIgnoreProperties("usuario")
    private List<EnderecoDTO> enderecos;

    public UsuarioDTO(){

    }

    public UsuarioDTO( Long id, String nome, String telefone, String cpf, Roles roles) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.roles = roles;
    }
}
