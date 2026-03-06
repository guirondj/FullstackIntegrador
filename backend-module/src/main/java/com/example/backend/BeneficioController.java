package com.example.backend;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.dto.TransferRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    private final BeneficioRepository repository;

    public BeneficioController(BeneficioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Beneficio> list() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Beneficio getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benefício não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beneficio create(@RequestBody Beneficio body) {
        body.setVersion(null);
        return repository.save(body);
    }

    @PutMapping("/{id}")
    public Beneficio update(@PathVariable Long id, @RequestBody Beneficio body) {

        Beneficio beneficio = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benefício não encontrado"));

        beneficio.setNome(body.getNome());
        beneficio.setDescricao(body.getDescricao());
        beneficio.setValor(body.getValor());
        beneficio.setAtivo(body.getAtivo());

        return repository.save(beneficio);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        Beneficio beneficio = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benefício não encontrado"));

        repository.delete(beneficio);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        return ResponseEntity.ok(
                "Transfer request recebida: fromId=" + request.getFromId()
                        + ", toId=" + request.getToId()
                        + ", amount=" + request.getAmount()
        );
    }
}