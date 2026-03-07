package com.example.backend;

import com.example.backend.repository.BeneficioRepository;
import com.example.backend.service.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeneficioController.class)
class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficioRepository repository;

    @MockBean
    private TransferService transferService;

    @Test
    void deveListarBeneficios() throws Exception {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/beneficios"))
                .andExpect(status().isOk());
    }
}