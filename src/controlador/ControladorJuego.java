package controlador;

import modelo.Tablero;
import modelo.Casilla;
import vista.VistaJuego;
import excepciones.CasillaYaDescubiertaException;

import java.io.*;

// Aquí unimos el Modelo con la Vista. Controla el flujo del juego.
public class ControladorJuego {
    private Tablero tablero;
    private VistaJuego vista;
    private boolean juegoTerminado;

    public ControladorJuego() {
        this.tablero = new Tablero();
        this.vista = new VistaJuego();
        this.juegoTerminado = false;
    }

    // Bucle principal del juego
    public void iniciar() {
        vista.mostrarMensaje("=== EXAMEN BUSCAMINAS POO ===");
        vista.mostrarMensaje("Instrucciones: Escribe coordenada (A5), 'M' para marcar (M A5), 'G' guardar, 'C' cargar.");

        while (!juegoTerminado) {
            try {
                // Mostramos el tablero en cada turno
                vista.mostrarTablero(tablero, false);
                procesarEntrada();
                
                // Verificamos si ganó después de cada movimiento
                if (tablero.verificarVictoria()) {
                    vista.mostrarTablero(tablero, true);
                    vista.mostrarMensaje("¡FELICIDADES! Ganaste el juego.");
                    juegoTerminado = true;
                }
            } catch (CasillaYaDescubiertaException e) {
                // Capturamos nuestra excepción personalizada
                vista.mostrarMensaje("Cuidado: " + e.getMessage());
            } catch (Exception e) {
                vista.mostrarMensaje("Error inesperado: " + e.getMessage());
            }
        }
        vista.cerrarScanner();
    }

    private void procesarEntrada() throws CasillaYaDescubiertaException, IOException, ClassNotFoundException {
        String entrada = vista.pedirEntrada("Tu jugada: ");

        // Opciones de menú
        if (entrada.equals("G")) {
            guardarJuego();
            return;
        }
        if (entrada.equals("C")) {
            cargarJuego();
            return;
        }
        
        boolean modoMarcar = false;
        // Detectamos si el usuario quiere poner bandera
        if (entrada.startsWith("M ")) {
            modoMarcar = true;
            entrada = entrada.substring(2).trim(); // Quitamos la "M " para quedarnos con la coordenada
        }

        // Validamos longitud (A1, A10)
        if (entrada.length() < 2 || entrada.length() > 3) {
            vista.mostrarMensaje("Formato mal. Escribe LetraNumero (ej. A5).");
            return;
        }

        try {
            // Convertimos la letra a índice (A=0, B=1...)
            int fila = entrada.charAt(0) - 'A';
            // Convertimos el número a índice (restamos 1 porque el usuario ve del 1 al 10)
            int col = Integer.parseInt(entrada.substring(1)) - 1;

            if (!tablero.esCoordenadaValida(fila, col)) {
                vista.mostrarMensaje("Te saliste del tablero.");
                return;
            }

            Casilla casillaObjetivo = tablero.getCasilla(fila, col);

            if (modoMarcar) {
                // Lógica para marcar/desmarcar
                if (casillaObjetivo.estaDescubierta()) {
                    vista.mostrarMensaje("No puedes marcar algo que ya viste.");
                } else {
                    // Invertimos el estado de la marca
                    casillaObjetivo.setEstaMarcada(!casillaObjetivo.estaMarcada());
                    vista.mostrarMensaje(casillaObjetivo.estaMarcada() ? "Bandera puesta." : "Bandera quitada.");
                }
            } else {
                // Lógica para descubrir
                if (casillaObjetivo.estaMarcada()) {
                    vista.mostrarMensaje("Quita la bandera antes de abrir.");
                    return;
                }
                // Llamamos al método recursivo
                descubrirCasillaRecursiva(fila, col);
            }

        } catch (NumberFormatException e) {
            vista.mostrarMensaje("El número de la coordenada no es válido.");
        } catch (StringIndexOutOfBoundsException e) {
             vista.mostrarMensaje("Error al leer la coordenada.");
        }
    }

    // Este es el método recursivo (Flood Fill) para abrir espacios vacíos automáticamente
    private void descubrirCasillaRecursiva(int fila, int col) throws CasillaYaDescubiertaException {
        if (!tablero.esCoordenadaValida(fila, col)) return; // Caso base: fuera de rango
        
        Casilla c = tablero.getCasilla(fila, col);
        
        if (c.estaDescubierta()) {
             // Si ya estaba abierta, solo retornamos (salvo que sea la primera llamada, pero eso lo controlamos antes)
             return; 
        }

        c.setEstaDescubierta(true);

        // Si pisamos mina, game over
        if (c.esMina()) {
            vista.mostrarTablero(tablero, true); // True para mostrar todo el mapa
            vista.mostrarMensaje("¡BOOM! Perdiste.");
            juegoTerminado = true;
            return;
        }

        // Si la casilla tiene 0 minas alrededor (es "V"), abrimos a los vecinos recursivamente
        if (c.getMinasAlrededor() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int nuevaFila = fila + i;
                    int nuevaCol = col + j;
                    // Llamada recursiva a los vecinos que no estén descubiertos
                    if (tablero.esCoordenadaValida(nuevaFila, nuevaCol) 
                        && !tablero.getCasilla(nuevaFila, nuevaCol).estaDescubierta()) {
                        descubrirCasillaRecursiva(nuevaFila, nuevaCol);
                    }
                }
            }
        }
    }

    // --- Persistencia de Datos ---
    
    // Guardamos el objeto Tablero completo en un archivo binario
    private void guardarJuego() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("partida_guardada.dat"))) {
            out.writeObject(tablero);
            vista.mostrarMensaje("Partida guardada correctamente.");
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo guardar: " + e.getMessage());
        }
    }

    // Leemos el objeto Tablero del archivo
    private void cargarJuego() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("partida_guardada.dat"))) {
            this.tablero = (Tablero) in.readObject();
            vista.mostrarMensaje("Partida cargada. ¡Sigue jugando!");
        } catch (IOException | ClassNotFoundException e) {
            vista.mostrarMensaje("No encontré ninguna partida guardada o el archivo está dañado.");
        }
    }
}