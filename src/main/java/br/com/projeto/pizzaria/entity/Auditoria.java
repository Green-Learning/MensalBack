package br.com.projeto.pizzaria.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter
//@MappedSuperclass
@Entity
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnoreProperties("item")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "sabores_id")
    @JsonIgnoreProperties("item")
    private Sabores sabores;
    
    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties("pedido")
    private Item item;

    private String userCriacao;
    private String userAlteracao;
    private String userExclusao;
    private Timestamp dataHoraCriacao;
    private Timestamp dataHoraAlteracao;
    private Timestamp dataHoraExclusao;

}
