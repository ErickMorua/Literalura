# Literalura Application

## Descripción

Literalura es una aplicación desarrollada en Java con el framework Spring Boot y utiliza JPA para interactuar con una base de datos PostgreSQL. La aplicación permite gestionar una colección de libros y autores, proporcionando diversas funcionalidades para buscar y listar información relevante.

## Funcionalidades

El menú principal de la aplicación ofrece las siguientes opciones:

    1. Buscar libro por título
    2. Listar libros registrados
    3. Listar autores registrados
    4. Listar autores vivos en un determinado año
    5. Listar libros por idioma
    6. Top 10 Libros
    0. salir

## 1. Buscar libro por título
Permite buscar un libro en la base de datos utilizando su título. Si el libro se encuentra, muestra sus detalles, incluyendo el autor, idioma y número de descargas.

## 2. Listar libros registrados
Muestra una lista de todos los libros que han sido registrados en la base de datos.

## 3. Listar autores registrados
Muestra una lista de todos los autores registrados en la base de datos.

## 4. Listar autores vivos en un determinado año
Permite listar los autores que estaban vivos en un año específico proporcionado por el usuario.

## 5. Listar libros por idioma
Muestra una lista de libros filtrada por el idioma especificado.

## 6. Top 10 Libros
Muestra una lista de los 10 libros más descargados.

## 0. Salir
Termina la ejecución de la aplicación.

## Configuración del Proyecto
Dependencias
La aplicación utiliza las siguientes dependencias principales:

Spring Boot
Spring Data JPA
PostgreSQL Driver
Jackson
Configuración de la Base de Datos
La configuración de la base de datos PostgreSQL se encuentra en el archivo application.properties:

```java
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## Ejecución de la Aplicación
Para ejecutar la aplicación, sigue estos pasos:

Clona el repositorio del proyecto.
Configura la base de datos PostgreSQL con las credenciales adecuadas en application.properties.
Ejecuta el proyecto utilizando tu IDE preferido o desde la línea de comandos con ./mvnw spring-boot:run.

## Ejemplo de Uso
Buscar Libro por Título
Cuando se selecciona la opción "1" del menú, el usuario debe ingresar el título del libro. La aplicación buscará en la base de datos y mostrará la información del libro si se encuentra registrado.

Listar Libros Registrados
Al seleccionar la opción "2", la aplicación mostrará todos los libros registrados en la base de datos, mostrando detalles como el título, autor y número de descargas.


