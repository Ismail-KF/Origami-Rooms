package com.ismail.hotelesjapon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Hotel;
import com.ismail.hotelesjapon.service.HotelService;

@Component
public class HotelInitializer {

        @Autowired
        private HotelService hotelService;

        private final Faker faker = new Faker(Locale.of("es"));

        private final List<String[]> hotelesData = List.of(
                        new String[] { "Hotel Shinjuku Grand",
                                        "Lujoso hotel en el corazón de Tokio con vistas al monte Fuji.",
                                        "2-1-1 Nishi-Shinjuku, Tokio", "KANTOU" },
                        new String[] { "Hotel Asakusa View", "Hotel moderno junto al templo Senso-ji.",
                                        "3-17-1 Nishi-Asakusa, Tokio", "KANTOU" },
                        new String[] { "Hotel Kyoto Hana", "Hotel elegante en el barrio de Gion rodeado de templos.",
                                        "480 Gionmachi Minamigawa, Kioto", "KANSAI" },
                        new String[] { "Sapporo Snow Hotel",
                                        "Hotel acojedor en Sapporo ideal para la temporada de nieve.",
                                        "1-1 Odori Nishi, Sapporo", "HOKKAIDOU" },
                        new String[] { "Hotel Osaka Castle", "Hotel con vistas al castillo de Osaka en pleno centro.",
                                        "1-1 Osakajo, Chuo-ku, Osaka", "KANSAI" },
                        new String[] { "Hiroshima Peace Hotel", "Hotel tranquilo cerca del Parque Memorial de la Paz.",
                                        "1-1 Nakajimacho, Naka-ku, Hiroshima", "CHUUGOKU" },
                        new String[] { "Hotel Shibuya Crossing",
                                        "Hotel vanguardista a pocos metros del cruce más famoso del mundo.",
                                        "2-24-1 Shibuya, Tokio", "KANTOU" },
                        new String[] { "Yokohama Sky Tower",
                                        "Impresionante hotel rascacielos con vistas panorámicas a la bahía.",
                                        "2-3-1 Minatomirai, Yokohama", "KANTOU" },
                        new String[] { "Kobe Harbor Grand",
                                        "Hotel sofisticado frente al puerto con excelente gastronomía local.",
                                        "1-1 Hatobacho, Kobe", "KANSAI" },
                        new String[] { "Otaru Canal Hotel",
                                        "Hotel de estilo clásico europeo junto al romántico canal de Otaru.",
                                        "1-2-3 Minatomachi, Otaru", "HOKKAIDOU" },
                        new String[] { "Fukuoka Riverside Hotel",
                                        "Alojamiento urbano y dinámico situado junto al río Naka en Nakasu.",
                                        "1-1 Nakasu, Fukuoka", "KYUUSHUU" },
                        new String[] { "Sendai Station Central",
                                        "Hotel ejecutivo de alta gama conectado con la estación principal.",
                                        "1-1-1 Chuo, Sendai", "TOHOKU" },
                        new String[] { "Nagasaki Port View",
                                        "Hotel moderno en las colinas con espectaculares vistas nocturnas al mar.",
                                        "1-1 Motomachi, Nagasaki", "KYUUSHUU" });

        public void init() {
                for (String[] datos : hotelesData) {
                        try {
                                int estrellas = faker.number().numberBetween(1, 6);
                                boolean tieneRestaurante = estrellas >= 3;
                                boolean tieneServicioHabitaciones = estrellas >= 4;
                                boolean tieneGimnasio = estrellas == 5;
                                List<Integer> puntuaciones = new ArrayList<>();
                                int numeroPuntuaciones = faker.number().numberBetween(5, 21);
                                for (int i = 0; i < numeroPuntuaciones; i++) {
                                        puntuaciones.add(faker.number().numberBetween(3, 6));
                                }

                                Region region = Region.valueOf(datos[3]);

                                Hotel hotel = new Hotel(
                                                datos[0],
                                                datos[1],
                                                datos[2],
                                                region,
                                                puntuaciones,
                                                0.0,
                                                estrellas,
                                                tieneRestaurante,
                                                tieneGimnasio,
                                                tieneServicioHabitaciones);
                                hotelService.crearHotel(hotel);
                        } catch (Exception e) {
                                System.err.println("Error al crear hotel " + datos[0] + ": " + e.getMessage());
                        }
                }
        }
}