# Project Compile

Project Compile analiza los proyectos Maven que tienes en tu disco duro para crear un json con todas las dependencias entre los proyectos.

Por **defecto** el fichero json generado con la informacion de los proyectos se guarda en el **home** del usuario, pero se puede indicar otro directorio con el siguiente argumento de entrada:
```
--jsonDirectory=C:\Users\user\Desktop\jsonDirectoryPruebas
```

La documentación del API esta generada con **Swagger** y se puede encontrar en la siguiente URL al arrancar el programa.
```
http://localhost:2222/swagger-ui.html#/
```

# Futuras Actualizaciones!

  - Leer y compilar proyectos de tipo Gradle
  - Crear fichero .cmd que arranque automaticamente la aplicación Spring Boot
  - Crear un front con Angular para facilitar el uso del API

# ChangeLog!
## Version 1.0.0 - 2018-08-11
### Added
  - Escaneo de los proyectos en disco
  - Analizador de dependencias innecesarias y dependencias ciclicas
  - Compilacion de proyectos Maven
### Changed
  - Nothing
### Removed
  - Nothing
