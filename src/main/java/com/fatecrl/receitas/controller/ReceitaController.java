package com.fatecrl.receitas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.receitas.model.Receita;
import com.fatecrl.receitas.service.ReceitaService;

import jakarta.websocket.server.PathParam;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService service;

    @GetMapping
    public ResponseEntity<List<Receita>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(params = "ingrediente")
    public ResponseEntity<List<Receita>> getByIngrediente(@RequestParam String ingrediente){
        return ResponseEntity.of(Optional.of(service.getByIngredientes(ingrediente)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Receita> getById(@PathVariable("id") Long id){
        //return ResponseEntity.of(Optional.of(service.getById(id)));
        Receita receita = service.getById(id);
        if (receita != null){
            return ResponseEntity.ok(receita);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Receita> create(@RequestBody Receita receita){
        service.create(receita);
        URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(receita.getId())
                            .toUri();
        return ResponseEntity.created(location).body(receita);
    }

    @PutMapping
    public ResponseEntity<Receita> update(@RequestBody Receita receita){
        if (service.update(receita)){
            return ResponseEntity.ok(receita);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Receita> delete(@PathVariable("id") Long id){
        if (service.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }    
}
