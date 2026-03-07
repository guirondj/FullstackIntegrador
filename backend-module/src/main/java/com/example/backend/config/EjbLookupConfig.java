package com.example.backend.config;

import com.example.ejb.BeneficioEjbServiceRemote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EjbLookupConfig {

    @Bean
    public BeneficioEjbServiceRemote beneficioEjbServiceRemote() throws NamingException {
        Context context = new InitialContext();

        String jndiName = "java:global/ejb-module-1.0.0/BeneficioEjbService!com.example.ejb.BeneficioEjbServiceRemote";

        return (BeneficioEjbServiceRemote) context.lookup(jndiName);
    }
}