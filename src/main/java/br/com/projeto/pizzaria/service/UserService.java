package br.com.projeto.pizzaria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.projeto.pizzaria.entity.UserConta;
import br.com.projeto.pizzaria.repository.LoginRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private LoginRepository loginRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<UserConta> userOptional = loginRepository.findByUsername(username);

        if(userOptional.isPresent()){
            UserConta user = userOptional.get();

            UserDetails userDetails = User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles("ROLE_USER")
                    .build();

            return userDetails;
        }

        throw new UsernameNotFoundException(username);
    }

    public UserConta criarUser(UserConta userConta) throws Exception{

        Optional<UserConta> username = loginRepository.findByUsername(userConta.getUsername());

        if(username.isPresent()){
            throw new Exception("Username ja esta em uso");
        }

        userConta.setPassword(passwordEncoder.encode(userConta.getPassword()));
        userConta.setRole(userConta.getRole().toUpperCase());

        var userSalvo = loginRepository.save(userConta);

        return userConta;
    }
    
}
