package com.example.backend.service;

import com.example.ejb.BeneficioEjbServiceRemote;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final BeneficioEjbServiceRemote ejbService;

    public TransferService(BeneficioEjbServiceRemote ejbService) {
        this.ejbService = ejbService;
    }

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        ejbService.transfer(fromId, toId, amount);
    }
}