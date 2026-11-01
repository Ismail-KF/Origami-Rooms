package com.ismail.hotelesjapon.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ismail.hotelesjapon.util.*;

@Configuration
public class InitializationConfig {
    @Bean
    public CommandLineRunner initData(
            ClienteInitializer clienteInitializer,
            HotelInitializer hotelInitializer,
            RyokanInitializer ryokanInitializer,
            HabitacionInitializer habitacionInitializer,
            ReservaInitializer reservaInitializer) {

        return args -> {
            clienteInitializer.init();
            hotelInitializer.init();
            ryokanInitializer.init();
            habitacionInitializer.init();
            reservaInitializer.init();
        };
    }
}