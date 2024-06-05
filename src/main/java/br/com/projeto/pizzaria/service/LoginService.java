package br.com.projeto.pizzaria.service;

import br.com.projeto.pizzaria.config.JwtServiceGenerator;
import br.com.projeto.pizzaria.convert.FuncionarioDTOConvert;
import br.com.projeto.pizzaria.dto.LoginDTO;
import br.com.projeto.pizzaria.dto.UserDTO;
import br.com.projeto.pizzaria.convert.UsuarioDTOConvert;
import br.com.projeto.pizzaria.entity.UserConta;
import br.com.projeto.pizzaria.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private JwtServiceGenerator jwtService;

    @Autowired
    private UsuarioDTOConvert usuarioDTOConvert;

    @Autowired
    private FuncionarioDTOConvert funcionarioDTOConvert;

    @Autowired
    private AuthenticationManager authenticationManager;


    public UserDTO logar(LoginDTO loginDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        UserConta userConta = loginRepository.findByUsername(loginDTO.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(userConta);

        return toUserDTO(userConta, jwtToken);
    }


    public UserDTO toUserDTO(UserConta userConta, String token){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userConta.getId());
        userDTO.setRole(userConta.getRole());
        userDTO.setToken(token);
        userDTO.setUsername(userConta.getUsername());
        return userDTO;
    }


}
