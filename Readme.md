# Buscaminas POO - Examen Final

##  Descripción del Proyecto
Este proyecto es una implementación del clásico juego **Buscaminas** desarrollado en Java para consola. El objetivo principal fue aplicar los conceptos de **Programación Orientada a Objetos (POO)** aprendidos en clase, utilizando una arquitectura **MVC (Modelo-Vista-Controlador)**.

El juego permite:
- Jugar en un tablero de 10x10 con 10 minas aleatorias.
- Descubrir casillas y utilizar recursividad para abrir espacios vacíos (Flood Fill).
- Marcar posibles minas con banderas.
- **Guardar y Cargar** la partida utilizando persistencia de datos (serialización).
- Manejo robusto de errores mediante **Excepciones Personalizadas**.

##  Tecnologías y Conceptos Aplicados
- **Java (JDK 8+)**: Lenguaje principal.
- **Patrón MVC**: Separación lógica entre `modelo`, `vista` y `controlador`.
- **Persistencia**: Uso de `Serializable` y `ObjectOutputStream` para guardar el estado.
- **Excepciones**: Implementación de `CasillaYaDescubiertaException`.
- **JUnit**: Para pruebas unitarias.
- **Git/GitHub**: Control de versiones.

##  Instrucciones de Instalación y Ejecución

### Requisitos Previos
Tener instalado el JDK de Java y un editor como VS Code, IntelliJ o NetBeans.

### Ejecución desde Consola
Si deseas compilar y ejecutar manualmente sin IDE:

1. Ubícate en la carpeta raíz del proyecto.
2. Compila el código (esto generará una carpeta `bin`):
   ```bash
   javac -d bin src/modelo/*.java src/vista/*.java src/controlador/*.java src/excepciones/*.java src/main/*.java
   ```
3. Ejecuta el programa:
   ```bash
   java -cp bin main.Main
   ```

### Ejecución desde VS Code
1. Abre la carpeta del proyecto.
2. Abre el archivo `src/main/Main.java`.
3. Haz clic en el botón **Run** (Play).

##  Guía de Uso
El juego te pedirá comandos en la consola:
- **Descubrir casilla**: Escribe la coordenada (ej: `A5`).
- **Poner/Quitar Bandera**: Escribe `M` seguido de la coordenada (ej: `M A5`).
- **Guardar Partida**: Escribe `G`.
- **Cargar Partida**: Escribe `C`.

##  Autor
- Erick Herrera
