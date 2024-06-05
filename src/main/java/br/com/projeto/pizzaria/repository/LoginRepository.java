package br.com.projeto.pizzaria.repository;

import br.com.projeto.pizzaria.entity.UserConta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<UserConta, Long> {

    /*@Query(value = "SELECT l.usuario FROM UserConta l WHERE l.usuario.nome = :nomeDeLogin")
    List<UserConta> findUserNameByNomeDeLogin(@Param("nomeDeLogin") String nomeDeLogin);

    @Query(value = "SELECT l.funcionario FROM UserConta l WHERE l.funcionario.nome = :nomeDeLogin")
    List<UserConta> findUserNameByNomeDeLoginFuncionario(@Param("nomeDeLogin") String nomeDeLogin);
    */

    //UserConta findByUsername(String username);

    public Optional<UserConta> findByUsername(String login);
}
