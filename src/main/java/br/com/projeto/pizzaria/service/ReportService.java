package br.com.projeto.pizzaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.pizzaria.entity.Auditoria;
import br.com.projeto.pizzaria.repository.AuditoriaRepository;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public List<Auditoria> listar() {
        return auditoriaRepository.findAll();
    }
}