package br.com.projeto.pizzaria.controller;

import br.com.projeto.pizzaria.dto.LoginDTO;
import br.com.projeto.pizzaria.dto.UserDTO;
import br.com.projeto.pizzaria.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<UserDTO> logar(@RequestBody LoginDTO loginDTO) {
        logger.info("Received login request for email: {}", loginDTO.getEmail());
        try {
            UserDTO userDTO = loginService.logar(loginDTO);
            logger.info("Login successful for email: {}", loginDTO.getEmail());
            return ResponseEntity.ok(userDTO);
        } catch (AuthenticationException ex) {
            logger.error("Authentication failed for email: {}", loginDTO.getEmail(), ex);
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error during login for email: {}", loginDTO.getEmail(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deslogar")
    public ResponseEntity<HttpStatus> logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}