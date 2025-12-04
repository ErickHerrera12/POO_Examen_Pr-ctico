package main;

import controlador.ControladorJuego;

// Clase principal, solo sirve de punto de entrada.
public class Main {
    public static void main(String[] args) {
        // Instanciamos el controlador e iniciamos el juego
        ControladorJuego juego = new ControladorJuego();
        juego.iniciar();
    }
}