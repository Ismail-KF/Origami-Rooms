package com.ismail.hotelesjapon.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;
import com.ismail.hotelesjapon.entity.Cliente;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.entity.Reserva.Estado;
import com.ismail.hotelesjapon.repository.ClienteRepository;
import com.ismail.hotelesjapon.repository.HabitacionRepository;
import com.ismail.hotelesjapon.service.ReservaService;

@Component
public class ReservaInitializer {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    private final Faker faker = new Faker(Locale.of("es"));

    public void init() {
        List<Long> clienteIds = new ArrayList<>();
        for (Cliente cliente : clienteRepository.findAll()) {
            clienteIds.add(cliente.getId());
        }

        List<Long> habitacionIds = new ArrayList<>();
        for (Habitacion habitacion : habitacionRepository.findAll()) {
            habitacionIds.add(habitacion.getId());
        }

        Estado[] estados = { Estado.COMPLETADA, Estado.PENDIENTE, Estado.CANCELADA };

        for (int i = 0; i < 10; i++) {
            try {
                int diasEntrada = faker.number().numberBetween(1, 180);
                LocalDate fechaEntrada = LocalDate.now().plusDays(diasEntrada);
                LocalDate fechaSalida = fechaEntrada.plusDays(faker.number().numberBetween(1, 10));

                Long clienteId = clienteIds.get(faker.number().numberBetween(0, clienteIds.size()));
                Long habitacionId = habitacionIds.get(faker.number().numberBetween(0, habitacionIds.size()));
                Estado estado = estados[faker.number().numberBetween(0, estados.length)];

                Reserva reserva = new Reserva(fechaEntrada, fechaSalida, estado, 0.0);
                reservaService.crearReserva(reserva, clienteId, List.of(habitacionId));
            } catch (Exception e) {
                System.err.println("Error al crear reserva: " + e.getMessage());
            }
        }
    }
}