# Proyecto Fin de Curso 1º DAM · Gestión de Hoteles: Origami Rooms 🏯🇯🇵

## Descripción del proyecto

En este proyecto he recreado una aplicación web para gestionar una compañía que lleva hoteles y ryokan (establecimientos tradicionales japoneses) en Japón. Permite ver listas de todos los establecimientos, habitaciones, clientes y reservas, así como editarlos, eliminarlos o añadir nuevos.

La navegación está pensada para ser fluida: desde la página principal puedes ir a cada establecimiento, ver sus habitaciones, crear reservas, gestionar clientes, etcétera. Todo está enlazado entre sí mediante botones.

---

## Estructura del proyecto

```
hoteles-japon/ 
├── docs/                      ← Documentación en JavaDoc del backend           
├── src/main/java/com/ismail/hotelesjapon/ ← Backend en Java con el framework de Spring Boot
│   ├── config/                ← Configuración para la inicialización de los datos de prueba
│   ├── controller/            ← Controladores REST
│   ├── entity/                ← Entidades JPA
│   ├── exception/             ← Manejador de excepciones global      
│   ├── repository/            ← Repositorios Spring Data JPA
│   ├── service/               ← Interfaces de servicio
│   │   └── impl/              ← Implementaciones de servicio
│   └── util/                  ← Inicializadores de datos de prueba
├── src/test/java/com/ismail/hotelesjapon/ ← Tests de los métodos aplicados en las implementaciones de los servicios
├── src/main/resources/        ← Frontend con HTML, CSS y JavaScript usando Bootstrap
│    ├──static/                ← Páginas web en HTML
│    │   ├── css/              ← CSS para los archivos HTML
│    │   ├── img/              ← Logo de la página web          
│    │   └── js/               ← Archivos JavaScript
│    │       ├── api/          ← Peticiones fetch a los controladores
│    │       ├── components/   ← Componentes HTML dinámicos y reutilizables
│    │       ├── pages/        ← Scripts específicos para cada página HTML
│    │       └── utils/        ← Manejador centralizado de peticiones HTTP Fetch y control unificado de errores con la API
│    └──application.properties ← Configuración de la base de datos H2
└──UML/                        ← Diagrama de clases
```

---

## Cómo ejecutar el proyecto

1. Abrir el proyecto en cualquier IDE.
2. Ejecutar la clase principal `HotelesJaponApplication.java`.
3. El servidor arrancará en `http://localhost:8080`.

Al arrancar, Spring Boot crea automáticamente la base de datos H2 en memoria y la llenará con datos de prueba generados con JavaFaker.

4. Abre cualquier archivo `.html` directamente en el navegador.

---

## Acceso a la consola H2

Se puede acceder a la base de datos H2 mediante la siguiente URL:

```
http://localhost:8080/h2-console
```

Está configurada para que no requiera contraseña así que basta con pulsar en "conectar" para entrar.

---

## Acceso a Swagger

Como alternativa a Postman, se puede acceder a Swagger para probar las operaciones CRUD de forma más interactiva. Se puede acceder mediante la siguiente URL:

```
http://localhost:8080/swagger-ui/index.html
```

 ## Vídeo presentación del proyecto

 Enlace: https://www.youtube.com/watch?v=q1Y3IE1ZS1M


 Marcas de tiempo:

00:00-04:00: Explicación de la arquitectura

04:00-09:53: Demostración práctica de los endpoints
 
09:53-14:59: Demostración completa del frontend