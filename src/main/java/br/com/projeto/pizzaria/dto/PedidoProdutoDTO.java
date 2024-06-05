package br.com.projeto.pizzaria.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoProdutoDTO {

    private Long id;
	// private PedidoDTO pedido;
	private ItemDTO item;
	private List<SaboresDTO> sabores;
    
}
