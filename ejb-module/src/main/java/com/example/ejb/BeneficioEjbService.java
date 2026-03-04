package com.example.ejb;

import jakarta.persistence.LockModeType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (fromId == null || toId == null) throw new IllegalArgumentException("fromId/toId obrigatório");
        if (amount == null) throw new IllegalArgumentException("amount obrigatório");
        if (fromId.equals(toId)) throw new IllegalArgumentException("Transferência para o mesmo benefício não é permitida");
        if (amount.signum() <= 0) throw new IllegalArgumentException("amount deve ser maior que zero");

        // Lock em ordem para evitar deadlock
        Long firstId = fromId < toId ? fromId : toId;
        Long secondId = fromId < toId ? toId : fromId;

        Beneficio first = em.find(Beneficio.class, firstId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio second = em.find(Beneficio.class, secondId, LockModeType.PESSIMISTIC_WRITE);

        if (first == null || second == null) throw new IllegalStateException("Benefício não encontrado");

        Beneficio from = fromId.equals(firstId) ? first : second;
        Beneficio to = toId.equals(firstId) ? first : second;

        if (from.getValor() == null || to.getValor() == null) throw new IllegalStateException("Saldo inválido");
        if (from.getValor().compareTo(amount) < 0) throw new IllegalStateException("Saldo insuficiente");

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        // Se as entidades já estão gerenciadas pelo EntityManager, nem precisa merge.
        em.flush();
    }
}
