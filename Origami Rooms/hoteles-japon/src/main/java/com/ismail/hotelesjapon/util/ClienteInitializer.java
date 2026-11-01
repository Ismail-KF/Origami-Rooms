package com.ismail.hotelesjapon.util;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;
import com.ismail.hotelesjapon.entity.Cliente;
import com.ismail.hotelesjapon.service.ClienteService;

@Component
public class ClienteInitializer {

    @Autowired
    private ClienteService clienteService;

    private final Faker faker = new Faker(Locale.of("es"));

    public void init() {
        for (int i = 0; i < 8; i++) {
            try {
                Cliente cliente = new Cliente(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.regexify("[67]\\d{8}"),
                        faker.regexify("[A-Z]{3}[0-9]{6}"));
                cliente.setNacionalidad(faker.country().name());
                clienteService.crearCliente(cliente);
            } catch (Exception e) {
                System.err.println("Error al crear cliente: " + e.getMessage());
            }
        }
    }
}