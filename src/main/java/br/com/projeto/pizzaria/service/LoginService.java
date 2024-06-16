package br.com.projeto.pizzaria.service;

import br.com.projeto.pizzaria.auth.TokenController;
import br.com.projeto.pizzaria.convert.FuncionarioDTOConvert;
import br.com.projeto.pizzaria.convert.UsuarioDTOConvert;
import br.com.projeto.pizzaria.dto.LoginDTO;
import br.com.projeto.pizzaria.dto.UserDTO;
import br.com.projeto.pizzaria.entity.UserConta;
import br.com.projeto.pizzaria.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public UserDTO logar(LoginDTO loginDTO) {
        logger.info("Authenticating user with Keycloak: {}", loginDTO.getEmail());

        TokenController.User user = new TokenController.User(
                loginDTO.getPassword(),
                clientId,
                "password",
                loginDTO.getEmail(),
                clientSecret);

        ResponseEntity<String> response = TokenController.getTokenResponse(user);
        logger.debug("Response from Keycloak: {}", response);

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Keycloak authentication successful for user: {}", loginDTO.getEmail());
        } else {
            logger.error("Keycloak authentication failed for user: {}. Response: {}", loginDTO.getEmail(), response);
            throw new RuntimeException("Keycloak authentication failed");
        }

        // Assuming the response body contains the token in the same format as the Postman response.
        String token = extractTokenFromResponse(response.getBody());

        UserConta userConta = loginRepository.findByUsername(loginDTO.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("User not found in the database"));

        logger.info("User authenticated, generating JWT token");
        return toUserDTO(userConta, token);
    }

    private String extractTokenFromResponse(String responseBody) {
        // Extract the access token from the response body
        Pattern pattern = Pattern.compile("\"access_token\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(responseBody);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException("Failed to extract access token from response");
        }
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
