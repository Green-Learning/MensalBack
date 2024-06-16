package br.com.projeto.pizzaria.controller;

import br.com.projeto.pizzaria.dto.FuncionarioDTO;
import br.com.projeto.pizzaria.dto.UsuarioDTO;
import br.com.projeto.pizzaria.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioDTO> criar(@RequestBody FuncionarioDTO funcionarioDTO){
        try{
            return ResponseEntity.ok( funcionarioService.criar(funcionarioDTO));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> buscarUsuarios(){
        try{
            return ResponseEntity.ok(funcionarioService.findAllFuncionario());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<FuncionarioDTO>> buscarNome(@RequestParam("nome")String nome){
        try{
            return ResponseEntity.ok(funcionarioService.findByNome(nome));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<FuncionarioDTO> editar(@PathVariable("id")Long id,@RequestBody FuncionarioDTO  funcionarioDTO){
        try{
            return ResponseEntity.ok(funcionarioService.editar(id,funcionarioDTO));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<FuncionarioDTO> deletar(@PathVariable("id")Long id){
        try{
            return ResponseEntity.ok(funcionarioService.deletar(id));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
