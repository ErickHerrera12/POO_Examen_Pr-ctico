package vista;

import modelo.Tablero;
import modelo.Casilla;
import java.util.Scanner;

// Clase encargada SOLO de mostrar cosas en consola y leer teclado (Patrón MVC)
public class VistaJuego {
    private Scanner scanner;

    public VistaJuego() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Dibuja el tablero en la consola
    public void mostrarTablero(Tablero tablero, boolean mostrarTodo) {
        System.out.println("\n   1 2 3 4 5 6 7 8 9 10"); // Encabezado de columnas
        System.out.println("   --------------------");
        
        for (int i = 0; i < tablero.getTamano(); i++) {
            char letraFila = (char) ('A' + i); // Convertimos indice 0 a 'A', 1 a 'B', etc.
            System.out.print(letraFila + " |");
            
            for (int j = 0; j < tablero.getTamano(); j++) {
                Casilla c = tablero.getCasilla(i, j);
                
                // Lógica de visualización: qué mostramos según el estado
                if (mostrarTodo && c.esMina()) {
                    System.out.print("X "); // Mostramos mina al perder o ganar
                } else if (!c.estaDescubierta()) {
                    if (c.estaMarcada()) {
                        System.out.print("P "); // Bandera (Flag)
                    } else {
                        System.out.print(". "); // Casilla tapada
                    }
                } else {
                    // Si está descubierta...
                    if (c.esMina()) {
                        System.out.print("X ");
                    } else if (c.getMinasAlrededor() == 0) {
                        System.out.print("V "); // Espacio Vacío (según pedía el examen)
                    } else {
                        System.out.print(c.getMinasAlrededor() + " "); // Número de minas vecinas
                    }
                }
            }
            System.out.println(); // Salto de línea para la siguiente fila
        }
        System.out.println();
    }

    // Método genérico para pedir datos al usuario
    public String pedirEntrada(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim().toUpperCase(); // Convertimos a mayúsculas para facilitar validación
    }
    
    public void cerrarScanner() {
        scanner.close();
    }
}