package br.com.projeto.pizzaria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String role;
    private String token;
    public UserDTO(){

    }

    public UserDTO(Long id, String email, String role) {
        this.id = id;
        this.username = email;
        this.role = role;
    }
}
