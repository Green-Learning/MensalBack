package br.com.projeto.pizzaria.service;

import br.com.projeto.pizzaria.convert.FuncionarioDTOConvert;
import br.com.projeto.pizzaria.convert.UsuarioDTOConvert;
import br.com.projeto.pizzaria.dto.LoginDTO;
import br.com.projeto.pizzaria.dto.UserDTO;
import br.com.projeto.pizzaria.entity.UserConta;
import br.com.projeto.pizzaria.repository.LoginRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UsuarioDTOConvert usuarioDTOConvert;

    @Autowired
    private FuncionarioDTOConvert funcionarioDTOConvert;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public UserDTO logar(LoginDTO loginDTO) {
        logger.info("Authenticating user with Keycloak: {}", loginDTO.getEmail());
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(loginDTO.getEmail())
                .password(loginDTO.getPassword())
                .grantType("password")
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().grantToken();

        UserConta userConta = loginRepository.findByUsername(loginDTO.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("User not found in the database"));

        logger.info("User authenticated, generating JWT token");
        return toUserDTO(userConta, tokenResponse.getToken());
    }

    public UserDTO toUserDTO(UserConta userConta, String token) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userConta.getId());
        userDTO.setRole(userConta.getRole());
        userDTO.setToken(token);
        userDTO.setUsername(userConta.getUsername());
        return userDTO;
    }
}
