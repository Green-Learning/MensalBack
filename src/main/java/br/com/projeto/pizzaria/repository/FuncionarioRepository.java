package br.com.projeto.pizzaria.repository;

import br.com.projeto.pizzaria.entity.Funcionario;
import br.com.projeto.pizzaria.entity.Usuario;
import br.com.projeto.pizzaria.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {

    @Query(value = "SELECT p FROM Funcionario p where p.nome = :nome")
    List<Funcionario> findFuncionarioByNome(@Param("nome")final String nome);

    /*
    @Query(value = "SELECT p FROM Funcionario p where p.roles = :roles")
    List<Funcionario> findPessoaByRole(@Param("roles")final Roles roles);
    */
}
