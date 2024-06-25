package br.com.projeto.pizzaria.controller;

import br.com.projeto.pizzaria.dto.SaboresDTO;
import br.com.projeto.pizzaria.service.SaboresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/sabores")
@CrossOrigin(origins = "*")
public class SaboresController {

    @Autowired
    private SaboresService saboresService;

    @PostMapping
    public ResponseEntity<SaboresDTO> criar (@RequestBody Map<String, Object> payload){
        try{
            SaboresDTO saboresDTO = new ObjectMapper().convertValue(payload.get("sabores"), SaboresDTO.class);
            String userCreacao = (String) payload.get("userCreacao");
            return ResponseEntity.ok(saboresService.criar(saboresDTO, userCreacao));

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SaboresDTO>> buscarTodos(){
        try{
            return ResponseEntity.ok(saboresService.findAllSabores());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public  ResponseEntity<SaboresDTO> buscarId(@RequestParam("id")Long id){
        try{
            return ResponseEntity.ok(saboresService.findById(id));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<SaboresDTO> editar(@PathVariable("id")Long id, @RequestBody Map<String, Object> payload){
        try{
            SaboresDTO saboresDTO = new ObjectMapper().convertValue(payload.get("sabores"), SaboresDTO.class);
            String userAlteracao = (String) payload.get("userAlteracao");
            return ResponseEntity.ok(saboresService.editar(id, saboresDTO, userAlteracao));

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<HttpStatus> deletar(@PathVariable("id") Long id, @RequestParam String userExclusao){
        try{
            saboresService.deletar(id,userExclusao);
            return ResponseEntity.ok(HttpStatus.OK);

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
