package com.ismail.hotelesjapon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Ryokan;
import com.ismail.hotelesjapon.service.RyokanService;

@Component
public class RyokanInitializer {

        @Autowired
        private RyokanService ryokanService;

        private final Faker faker = new Faker(Locale.of("es"));

        private final List<String[]> ryokanData = List.of(
                        new String[] { "Ryokan Arashiyama",
                                        "Ryokan tradicional junto al bosque de bambú de Arashiyama.",
                                        "11 Saga Oguracho, Kioto", "KANSAI" },
                        new String[] { "Hakone Yurari", "Ryokan con vistas al monte Fuji y baños termales privados.",
                                        "1297 Ninotaira, Hakone", "KANTOU" },
                        new String[] { "Tohoku Yamano Yado", "Ryokan en plena naturaleza en las montañas de Tohoku.",
                                        "25 Tamagawa Onsen, Akita", "TOHOKU" },
                        new String[] { "Kyushu Onsen Inn", "Ryokan sencillo en la región volcánica de Kyushu.",
                                        "1-1 Kannawa, Beppu", "KYUUSHUU" },
                        new String[] { "Nara Shika Yado", "Ryokan tradicional en Nara rodeado de ciervos y templos.",
                                        "1 Kasugano-cho, Nara", "KANSAI" },
                        new String[] { "Nikko Yama no Ie", "Ryokan de montaña cerca de los templos de Nikko.",
                                        "2307 Sannai, Nikko", "KANTOU" },
                        new String[] { "Uji Matcha An",
                                        "Ryokan boutique especializado en la ceremonia del té y jardines Zen.",
                                        "10 Uji Matashiro, Uji", "KANSAI" },
                        new String[] { "Miyajima Torii An",
                                        "Hospedaje tradicional en la isla sagrada con vistas al torii flotante.",
                                        "1-1 Miyajimacho, Hatsukaichi", "CHUUGOKU" },
                        new String[] { "Kinosaki Kawasemi",
                                        "Histórico ryokan donde podrás pasear en yukata junto al canal de sauces.",
                                        "123 Yushima, Kinosaki Onsen", "KANSAI" },
                        new String[] { "Noboribetsu Jigoku Yado",
                                        "Famoso por la pureza y propiedades curativas de sus termas volcánicas.",
                                        "1 Noboribetsu Onsencho, Noboribetsu", "HOKKAIDOU" },
                        new String[] { "Kurokawa Midori",
                                        "Ryokan oculto en un frondoso valle con baños de piedra al aire libre.",
                                        "6567 Mauta, Minamioguni", "KYUUSHUU" },
                        new String[] { "Ginzan Onsen Kaze",
                                        "Ryokan nostálgico con arquitectura de la era Taisho que parece de cuento.",
                                        "443 Ginzan Shinhata, Obanazawa", "TOHOKU" },
                        new String[] { "Kamakura Ajisai Yado",
                                        "Alojamiento costero tradicional decorado con hortensias de temporada.",
                                        "2-1-1 Hase, Kamakura", "KANTOU" });

        public void init() {
                for (String[] datos : ryokanData) {
                        try {
                                int estrellas = faker.number().numberBetween(1, 6);
                                boolean tieneKimono = estrellas >= 3;
                                boolean tieneJardin = estrellas >= 4;
                                boolean tieneOnsen = estrellas == 5;
                                List<Integer> puntuaciones = new ArrayList<>();
                                int numeroPuntuaciones = faker.number().numberBetween(5, 21);
                                for (int i = 0; i < numeroPuntuaciones; i++) {
                                        puntuaciones.add(faker.number().numberBetween(3, 6));
                                }

                                Region region = Region.valueOf(datos[3]);

                                Ryokan ryokan = new Ryokan(
                                                datos[0],
                                                datos[1],
                                                datos[2],
                                                region,
                                                puntuaciones,
                                                0.0,
                                                estrellas,
                                                tieneOnsen,
                                                tieneKimono,
                                                tieneJardin);
                                ryokanService.crearRyokan(ryokan);
                        } catch (Exception e) {
                                System.err.println("Error al crear ryokan " + datos[0] + ": " + e.getMessage());
                        }
                }
        }
}