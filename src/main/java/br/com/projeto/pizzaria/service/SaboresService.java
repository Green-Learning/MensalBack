package br.com.projeto.pizzaria.service;

import br.com.projeto.pizzaria.dto.ItemDTO;
import br.com.projeto.pizzaria.dto.PedidoDTO;
import br.com.projeto.pizzaria.dto.SaboresDTO;
import br.com.projeto.pizzaria.entity.Auditoria;
import br.com.projeto.pizzaria.entity.Item;
import br.com.projeto.pizzaria.entity.Pedido;
import br.com.projeto.pizzaria.entity.Sabores;
import br.com.projeto.pizzaria.repository.AuditoriaRepository;
import br.com.projeto.pizzaria.repository.SaboresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaboresService {

    @Autowired
    private SaboresRepository saboresRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public SaboresDTO criar(SaboresDTO saboresDTO, String userCreacao){
        Sabores sabores = toSabores(saboresDTO);

        this.saboresRepository.save(sabores);

        Auditoria auditoria = new Auditoria();
        auditoria.setSabores(sabores);
        auditoria.setDataHoraCriacao(new Timestamp(System.currentTimeMillis()));
        auditoria.setUserCriacao(userCreacao);
        auditoriaRepository.save(auditoria);

        return toSaboresDTO(sabores);
    }

    public SaboresDTO findById(Long id){
        Sabores saboresBanco = saboresRepository.findById(id).orElse(null);

        return toSaboresDTO(saboresBanco);
    }

    public List<SaboresDTO> findAllSabores(){
        List<Sabores> saboresBanco = saboresRepository.findAll();
        List<SaboresDTO> saboresDTOList = new ArrayList<>();

        for(int i = 0; i < saboresBanco.size(); i++){
            saboresDTOList.add(toSaboresDTO(saboresBanco.get(i)));
        }

        return saboresDTOList;
    }

    public SaboresDTO editar(Long id, SaboresDTO saboresDTO, String userAlteracao){
        Sabores sabores = this.saboresRepository.findById(id).orElse(null);

        Assert.isTrue(sabores != null, "Sabor nao encontrado");

        this.saboresRepository.save(toSabores(saboresDTO));

        Auditoria auditoria = new Auditoria();
        auditoria.setSabores(toSabores(saboresDTO));
        auditoria.setDataHoraAlteracao(new Timestamp(System.currentTimeMillis()));
        auditoria.setUserAlteracao(userAlteracao);
        auditoriaRepository.save(auditoria);

        return saboresDTO;
    }

    public String deletar(Long id, String userExclusao){
        Sabores sabores = this.saboresRepository.findById(id).orElse(null);

        Assert.isTrue(sabores != null, "Sabor nao encontrado");

        this.saboresRepository.delete(sabores);

        Auditoria auditoria = new Auditoria();
            auditoria.setSabores(sabores);
            auditoria.setDataHoraExclusao(new Timestamp(System.currentTimeMillis()));
            auditoria.setUserExclusao(userExclusao);
            auditoriaRepository.save(auditoria);

        return "Sabor deletado";
    }

    public SaboresDTO toSaboresDTO(Sabores sabores){
        SaboresDTO saboresDTO = new SaboresDTO();

        saboresDTO.setNome(sabores.getNome());
        saboresDTO.setId(sabores.getId());

        List<ItemDTO> itemsDump = new ArrayList<>();


        saboresDTO.setItemDTOS(itemsDump);
        return saboresDTO;
    }

    public Sabores toSabores(SaboresDTO saboresDTO){
        Sabores sabores = new Sabores();

        sabores.setNome(saboresDTO.getNome());
        sabores.setId(saboresDTO.getId());

        List<Item> itemsDump = new ArrayList<>();

        if(saboresDTO.getItemDTOS() != null){
            for(int i = 0; i < saboresDTO.getItemDTOS().size(); i++){
                itemsDump.add(itemService.toItem(saboresDTO.getItemDTOS().get(i)));
            }
        }

        sabores.setItem(itemsDump);
        return sabores;
    }

    public ItemDTO toItemDTO(Item item){
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId(item.getId());
        itemDTO.setNome(item.getNome());
        itemDTO.setTamanho(item.getTamanho());
        itemDTO.setPossuiSabores(item.isPossuiSabores());
        itemDTO.setValor(item.getValor());

        List<PedidoDTO> pedidoDTOList = new ArrayList<>();
        itemDTO.setPedido(pedidoDTOList);

        List<SaboresDTO> saboresDTOList = new ArrayList<>();

        if(item.getSabores() != null){
            for(int i=0;i<item.getSabores().size(); i++){
                saboresDTOList.add(toSaboresDTO(item.getSabores().get(i)));
            }
        }
        itemDTO.setSabores(saboresDTOList);
        return itemDTO;
    }

    public Item toItem(ItemDTO itemDTO){
        Item item = new Item();

        item.setId(itemDTO.getId());
        item.setNome(itemDTO.getNome());
        item.setTamanho(itemDTO.getTamanho());
        item.setPossuiSabores(itemDTO.isPossuiSabores());
        item.setValor(itemDTO.getValor());

        List<Pedido> pedidoList = new ArrayList<>();
        List<Sabores> saboresList = new ArrayList<>();


        if(itemDTO.getSabores() != null){
            for (int i=0;i< itemDTO.getSabores().size(); i++){
                saboresList.add(toSabores(itemDTO.getSabores().get(i)));
            }
        }
        item.setSabores(saboresList);
        return item;    }

}
