# API rest HotelBookings

API rest de reserva de hoteles que permite a los usuarios reservar habitaciones en distintos hoteles según la disponibilidad de fechas de los mismos. La API permite realizar consultas de disponibilidad, reservas y modificación de datos de las mismas.

## Tecnologías utilizadas

* Maven
* SpringBoot
* SpringData
* Junit
* Mockito
* Swagger-ui (Documentación)

## Instrucciones para la instalación

Para poner en funcionamiento la base de datos, se deben seguir los siguientes pasos:

* 1.Descargar o clonar el repositorio.
* 2.Ejecutar el archivo estructura.sql en su gestor de base de datos (por defecto, se ha nombrado la base de datos como hotel_bookings).
* 3.Ejecutar el archivo datos.sql en su gestor de base de datos para insertar datos de ejemplo.
* 4.Importar la colección hotelbookings.postman_collection.json en POSTMAN para poder realizar todas las operaciones predefinidas en nuestra APIrest.
* 5.Para los test de integración se ha tomado la misma BD (PostgreSQL, con Rollback) , pero con nombre testDB. Para realizar todos los test , es necesario vaciar o crear nueva la BD e insertar el fichero datos.sql, para tener certeza de que se están realizando bien los test.

## Guía del Usuario

La API rest HotelBookings puede ser utilizada de dos formas:
* 1. Acceder a la [web de documentación](https://localhost:8080/hotelbookings) y ejecutar todos los comandos de la API.
* 2. Utilizar cualquier herramienta que permita hacer pruebas con APIs, como POSTMAN
* A continuación, se muestra una captura con todos los métodos documentados en nuestra web y cómo utilizarlos.

![Screenshot of a comment on a GitHub issue showing an image, added in the Markdown, of an Octocat smiling and raising a tentacle.](https://i.imgur.com/wWNv41t.png)

## Ejemplos de uso
A continuación, se presentan algunas de las pruebas realizadas utilizando todos los controladores/mapeos y el resultado obtenido con la base de datos de pruebas que se aporta en el proyecto.

* Consultar listado completo de hoteles, ordenados por ID:

  - GET [http://localhost:8080/hotels ](http://localhost:8080/hotels )

- PUT – [http://localhost:8080/hotels ](http://localhost:8080/hotels ) – Actualizar hotel, pasar contenido en cuerpo JSON

- POST – [http://localhost:8080/hotels ](http://localhost:8080/hotels ) – Crear nuevo hotel , pasar contenido por JSON

- GET – [http://localhost:8080/hotels/{id} ](http://localhost:8080/hotels/{id} ) – Devuelve los datos de un Hotel, id por parámetros.

- GET – [http://localhost:8080/hotels/availabilities ](http://localhost:8080/hotels/availabilities ) – Consulta disponibilidad, parámetro fecha de entrada y salida obligatorios , nombre y categoría de hotel opcional ( todo por parámetros ).

- POST – [http://localhost:8080/bookings/{hotelid} ](http://localhost:8080/bookings/{hotelid} ) – Crea reserva , es necesario envíar el ID del hotel como parámetro, el resto JSON body

- GET – [http://localhost:8080/bookings/{hotelid} ](http://localhost:8080/bookings/{hotelid} ) – Consulta las reservas de un hotel en una fecha determinada.

- GET – [http://localhost:8080/bookings/search/{id} ](http://localhost:8080/bookings/search/{id} ) – Consulta reserva por id , devolvemos reserva y el hotel asociado.

- DELETE – [http://localhost:8080/bookings/delete/{id} ](http://localhost:8080/bookings/delete/{id} ) Borra la reserva según el ID facilitado.

- POST – [http://localhost:8080/availabilities/{hotelid} ](http://localhost:8080/availabilities/{hotelid} )

## License

[MIT](https://choosealicense.com/licenses/mit/)
