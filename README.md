# Pillalas Al Vuelo

Aplicación desplegada en http://www.pillasalvuelo.com

Es un repositorio de con información relacionada con el aviturismo. Se puede incluir información sobre aves, puntos de observación, avistamientos y fotografías. 

Se trata del proyecto fin del Ciclo Formativo de Grado Superior de Desarrollo de Aplicaciones Web realizado en el CIFP La Laboral (Gijón, España), y entregado en Junio de 2019.

This application was generated using JHipster 6.0.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.0.1](https://www.jhipster.tech/documentation-archive/v6.0.1).


## Building for production

### Packaging as jar

To build the final jar and optimize the pillalasAlVuelo application for production, run:

    ./mvnw -Pprod clean verify

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.jar

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify
