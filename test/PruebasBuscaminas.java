package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import modelo.Tablero;
import modelo.Casilla;

public class PruebasBuscaminas {

    private Tablero tablero;

    // Esto se ejecuta antes de cada prueba para tener un tablero limpio
    @Before
    public void setUp() {
        tablero = new Tablero();
    }

    // 1. Probamos que el tablero se cree con las dimensiones correctas (10x10)
    @Test
    public void testDimensionesTablero() {
        assertEquals("El tablero debe ser de tamaño 10", 10, tablero.getTamano());
        
        // Verificamos que no explote en los límites
        assertNotNull(tablero.getCasilla(0, 0));
        assertNotNull(tablero.getCasilla(9, 9));
    }

    // 2. Probamos que se generen exactamente 10 minas
    @Test
    public void testCantidadDeMinas() {
        int contadorMinas = 0;
        for (int i = 0; i < tablero.getTamano(); i++) {
            for (int j = 0; j < tablero.getTamano(); j++) {
                if (tablero.getCasilla(i, j).esMina()) {
                    contadorMinas++;
                }
            }
        }
        assertEquals("El tablero debe tener exactamente 10 minas", 10, contadorMinas);
    }

    // 3. Probamos la validación de coordenadas (importante para evitar errores)
    @Test
    public void testCoordenadasValidas() {
        assertTrue("0,0 debería ser válida", tablero.esCoordenadaValida(0, 0));
        assertTrue("9,9 debería ser válida", tablero.esCoordenadaValida(9, 9));
        
        assertFalse("-1,0 debería ser inválida", tablero.esCoordenadaValida(-1, 0));
        assertFalse("0,10 debería ser inválida", tablero.esCoordenadaValida(0, 10)); // El índice 10 está fuera (0-9)
    }

    // 4. Probamos el funcionamiento básico de una Casilla
    @Test
    public void testEstadoCasilla() {
        Casilla c = new Casilla();
        
        assertFalse("La casilla debe iniciar no descubierta", c.estaDescubierta());
        assertFalse("La casilla debe iniciar sin marca", c.estaMarcada());
        
        c.setEstaMarcada(true);
        assertTrue("La casilla debe estar marcada ahora", c.estaMarcada());
        
        c.setEstaDescubierta(true);
        assertTrue("La casilla debe aparecer como descubierta", c.estaDescubierta());
    }

    // 5. Probamos la condición de victoria lógica
    @Test
    public void testVerificarVictoria() {
        // Simulamos una partida perfecta:
        // Recorremos todo el tablero y "descubrimos" manualmente todas las casillas que NO son minas.
        for (int i = 0; i < tablero.getTamano(); i++) {
            for (int j = 0; j < tablero.getTamano(); j++) {
                Casilla c = tablero.getCasilla(i, j);
                if (!c.esMina()) {
                    c.setEstaDescubierta(true);
                }
            }
        }
        
        // Si todas las seguras están abiertas, el método debe devolver TRUE
        assertTrue("El juego debería detectar la victoria", tablero.verificarVictoria());
    }
    
    // 6. Probamos que no gane si falta una casilla segura por abrir
    @Test
    public void testJuegoIncompleto() {
        // En un tablero nuevo nadie ha jugado, así que victoria debe ser falso
        assertFalse("No se puede ganar apenas inicia el juego", tablero.verificarVictoria());
    }
}