package br.com.projeto.pizzaria.controller;

import br.com.projeto.pizzaria.dto.ItemDTO;
import br.com.projeto.pizzaria.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDTO> criar(@RequestBody Map<String, Object> payload) {
        try {
            ItemDTO itemDTO = new ObjectMapper().convertValue(payload.get("item"), ItemDTO.class);
            String userCreacao = (String) payload.get("userCreacao");
            return ResponseEntity.ok(itemService.criar(itemDTO, userCreacao));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> buscarTodos() {
        try {
            return ResponseEntity.ok(itemService.findAllItens());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ItemDTO> buscarId(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(itemService.findById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ItemDTO> editar(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            ItemDTO itemDTO = new ObjectMapper().convertValue(payload.get("item"), ItemDTO.class);
            String userAlteracao = (String) payload.get("userAlteracao");
            return ResponseEntity.ok(itemService.editar(id, itemDTO, userAlteracao));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<HttpStatus> deletar(@PathVariable Long id, @RequestParam String userExclusao) {
        try {
            itemService.deletar(id,userExclusao);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
