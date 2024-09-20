package com.fatecrl.receitas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatecrl.receitas.model.Receita;

@Service
public class ReceitaService {
    private static List<Receita> listaDeReceitas = new ArrayList<>();

    private void receitaFake(){
        Receita receitaFake = new Receita();
        receitaFake.setId(1L);
        receitaFake.setNome("Omelete");
        String[] lista = {"2 ovos", "Queijo", "Cebola"};
        receitaFake.setIngredientes(List.of(lista));
        receitaFake.setInstrucoes("Corte todos os ingredientes em pedaços pequenos, faça uma mistura, jogue tudo na frigideira e seja feliz");
        listaDeReceitas.add(receitaFake);
    }

    public ReceitaService(){
        receitaFake();
    }

    public List<Receita> getAll(){
        return listaDeReceitas;
    }

    public Receita getById(Long id){
        Receita _receita = new Receita(id);
        return listaDeReceitas.stream()
                       .filter(r -> r.equals(_receita))
                       .findFirst().orElse(null);
    }

    public Receita get(Receita receita){
        return this.getById(receita.getId());
    }

    public List<Receita> getByIngredientes(String ingrediente){
        return listaDeReceitas.stream()
                       .filter(r -> r.getIngredientes().stream()
                                                       .filter(i -> i.equals(ingrediente))
                                                       .count() > 0 
                       ).toList();
    }

    public Boolean delete(Long id){
        Receita receita = this.getById(id);
        if (receita != null){
            listaDeReceitas.remove(receita);
            return true;
        }
        return false;
    }

    public Receita create(Receita receita){
        listaDeReceitas.add(receita);
        return receita;
    }

    public Boolean update(Receita receitaParam){
        //Receita _receita = this.getById(receita.getId());
        Receita _receita = this.get(receitaParam);
        if (_receita != null){
            if (receitaParam.getIngredientes() != null 
                && receitaParam.getIngredientes().size() > 0){
                //Sobrescreve os valores atuais
                _receita.setIngredientes(receitaParam.getIngredientes());
                //Adiciona sem remover
                //_receita.getIngredientes().addAll(receita.getIngredientes());
            }
            if (!receitaParam.getNome().isEmpty()){
                _receita.setNome(receitaParam.getNome());
            }
            if (!receitaParam.getInstrucoes().isEmpty()){
                _receita.setInstrucoes(receitaParam.getInstrucoes());
            }
            return true;
        }
        return false;        
    }
    
}
