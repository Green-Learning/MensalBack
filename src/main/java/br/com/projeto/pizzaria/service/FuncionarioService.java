package br.com.projeto.pizzaria.service;


import br.com.projeto.pizzaria.convert.FuncionarioDTOConvert;
import br.com.projeto.pizzaria.dto.FuncionarioDTO;
import br.com.projeto.pizzaria.entity.Funcionario;
import br.com.projeto.pizzaria.enums.Roles;
import br.com.projeto.pizzaria.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioDTOConvert funcionarioDTOConvert;

    public FuncionarioDTO criar(FuncionarioDTO funcionarioDTO){

        Funcionario funcionario = funcionarioDTOConvert.convertFuncionarioDTOToFuncionario(funcionarioDTO);

        this.funcionarioRepository.save(funcionario);

        return funcionarioDTOConvert.convertFuncionarioToFuncionarioDTO(funcionario);
    }

    /*
    public List<FuncionarioDTO> findByRole(Roles roles){

        List<Funcionario> funcionarios = this.funcionarioRepository.findPessoaByRole(roles);
        List<FuncionarioDTO> funcionarioDTOList = new ArrayList<>();

        for(int i=0;i<funcionarios.size();i++){
            funcionarioDTOList.add(funcionarioDTOConvert.convertFuncionarioToFuncionarioDTO(funcionarios.get(i)));
        }

        return funcionarioDTOList;
    }
     */

    public List<FuncionarioDTO> findByNome(String nome){

        List<Funcionario> funcionarios = this.funcionarioRepository.findFuncionarioByNome(nome);
        List<FuncionarioDTO> funcionarioDTOList = new ArrayList<>();

        for(int i=0;i<funcionarios.size();i++){
            funcionarioDTOList.add(funcionarioDTOConvert.convertFuncionarioToFuncionarioDTO(funcionarios.get(i)));
        }

        return funcionarioDTOList;
    }

    /*
    public FuncionarioDTO findById(Long id){
        Funcionario funcionarioBanco = funcionarioRepository.findById(id).orElse(null);

        return toFuncionarioDTO(funcionarioBanco);
    }*/

    public List<FuncionarioDTO> findAllFuncionario(){
        List<Funcionario> funcionarioBanco = funcionarioRepository.findAll();
        List<FuncionarioDTO> funcionarioDTOList = new ArrayList<>();

        for(int i=0;i< funcionarioBanco.size();i++){
            funcionarioDTOList.add(funcionarioDTOConvert.convertFuncionarioToFuncionarioDTO(funcionarioBanco.get(i)));
        }

        return funcionarioDTOList;
    }

    public FuncionarioDTO editar(Long id, FuncionarioDTO funcionarioDTO){
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElse(null);
        Funcionario funcionarioNovo = funcionarioDTOConvert.convertFuncionarioDTOToFuncionario(funcionarioDTO);

        Assert.isTrue(funcionario != null, "Funcionaio Nao Encontrado");

        this.funcionarioRepository.save(funcionarioNovo);

        return funcionarioDTOConvert.convertFuncionarioToFuncionarioDTO(funcionarioNovo);
    }

    public FuncionarioDTO deletar(Long id){
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElse(null);

        Assert.isTrue(funcionario != null, "Funcionario nao encontrado");

        this.funcionarioRepository.delete(funcionario);

        return funcionarioDTOConvert.convertFuncionarioToFuncionarioDTO(funcionario);
    }



}
