package br.com.projeto.pizzaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.projeto.pizzaria.entity.Auditoria;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
}
