package com.ismail.hotelesjapon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;
import com.ismail.hotelesjapon.entity.Establecimiento;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Habitacion.Tipo;
import com.ismail.hotelesjapon.repository.EstablecimientoRepository;
import com.ismail.hotelesjapon.service.HabitacionService;

@Component
public class HabitacionInitializer {

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    private final Faker faker = new Faker(Locale.of("es"));

    private final Tipo[] tipos = Tipo.values();

    public void init() {
        List<Long> ids = new ArrayList<>();
        List<Establecimiento> establecimientos = establecimientoRepository.findAll();
        for (Establecimiento establecimiento : establecimientos) {
            ids.add(establecimiento.getId());
        }

        int numeroHabitacion = 101;

        for (Long establecimientoId : ids) {
            int numHabitaciones = faker.number().numberBetween(3, 6);
            for (int i = 0; i < numHabitaciones; i++) {
                try {
                    Tipo tipo = tipos[faker.number().numberBetween(0, tipos.length)];
                    int capacidad = switch (tipo) {
                        case INDIVIDUAL -> 1;
                        case DOBLE -> 2;
                        case TRIPLE -> 3;
                        case SUITE -> 2;
                        case ECONOMICA -> 1;
                    };
                    double precio = switch (tipo) {
                        case INDIVIDUAL -> faker.number().numberBetween(30, 60);
                        case DOBLE -> faker.number().numberBetween(50, 100);
                        case TRIPLE -> faker.number().numberBetween(75, 125);
                        case SUITE -> faker.number().numberBetween(150, 250);
                        case ECONOMICA -> faker.number().numberBetween(20, 40);
                    };

                    Habitacion habitacion = new Habitacion(
                            String.valueOf(numeroHabitacion++),
                            tipo,
                            capacidad,
                            precio);
                    habitacionService.crearHabitacion(habitacion, establecimientoId);
                } catch (Exception e) {
                    System.err.println("Error al crear habitación: " + e.getMessage());
                }
            }
        }
    }
}