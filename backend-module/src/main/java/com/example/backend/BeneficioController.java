package com.example.backend;

import org.springframework.web.bind.annotation.*;
import java.util.*;

// TODO Sprint 2: substituir stub por CRUD real + integração com EJB

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    @GetMapping
    public List<String> list() {
        return Arrays.asList("Beneficio A", "Beneficio B");
    }
}
