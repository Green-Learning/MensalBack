package br.com.projeto.pizzaria.controller;

import br.com.projeto.pizzaria.dto.PedidoDTO;
import br.com.projeto.pizzaria.service.PedidoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@RequestBody Map<String, Object> payload){
        try{
            PedidoDTO pedidoDTO = new ObjectMapper().convertValue(payload.get("pedido"), PedidoDTO.class);
            String userCreacao = (String) payload.get("userCreacao");
            return ResponseEntity.ok(pedidoService.criar(pedidoDTO, userCreacao));
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> buscarTodos(){
        try{
            return ResponseEntity.ok(pedidoService.findAllPedido());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PedidoDTO> buscarId(@RequestParam("id")Long id){
        try{
            return ResponseEntity.ok(pedidoService.findById(id));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<PedidoDTO> editar(@PathVariable Long id, @RequestBody Map<String, Object> payload){
        try{
            PedidoDTO pedidoDTO = new ObjectMapper().convertValue(payload.get("pedido"), PedidoDTO.class);
            String userAlteracao = (String) payload.get("userAlteracao");
            return ResponseEntity.ok(pedidoService.editar(id, pedidoDTO, userAlteracao));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<HttpStatus> deletar(@PathVariable Long id, @RequestParam String userExclusao){
        try{
            pedidoService.deletar(id,userExclusao);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
