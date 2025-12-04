package modelo;

import java.io.Serializable;
import java.util.Random;

// Esta clase maneja toda la lógica de la matriz. También es Serializable para guardarse junto con las casillas.
public class Tablero implements Serializable {
    
    private Casilla[][] casillas;
    // Usamos constantes para que sea fácil cambiar el tamaño si hiciera falta luego.
    private final int TAMANO = 10;
    private final int CANTIDAD_MINAS = 10;

    public Tablero() {
        casillas = new Casilla[TAMANO][TAMANO];
        inicializarTablero(); // Llena la matriz de objetos Casilla
        colocarMinas();       // Pone las minas al azar
        calcularNumeros();    // Calcula los numeritos de ayuda
    }

    private void inicializarTablero() {
        // Recorremos la matriz para instanciar cada casilla, si no daría error de nulo.
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;
        
        // Bucle while para asegurar que ponemos exactamente 10 minas
        while (minasColocadas < CANTIDAD_MINAS) {
            int fila = random.nextInt(TAMANO);
            int col = random.nextInt(TAMANO);
            
            // Verificamos si ya hay mina ahí para no repetir
            if (!casillas[fila][col].esMina()) {
                casillas[fila][col].setEsMina(true);
                minasColocadas++;
            }
        }
    }

    // Este método revisa casilla por casilla para contar cuántas minas tiene alrededor
    private void calcularNumeros() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (!casillas[i][j].esMina()) {
                    int minas = contarMinasVecinas(i, j);
                    casillas[i][j].setMinasAlrededor(minas);
                }
            }
        }
    }

    // Aquí revisamos las 8 casillas de alrededor (incluyendo diagonales)
    private int contarMinasVecinas(int fila, int col) {
        int contador = 0;
        // Bucles anidados desde -1 a +1 para recorrer vecinos
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaCol = col + j;
                
                // Importante: validar coordenadas para no salirnos del array (ArrayIndexOutOfBounds)
                if (esCoordenadaValida(nuevaFila, nuevaCol) && casillas[nuevaFila][nuevaCol].esMina()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    // Método auxiliar para validar que no nos salgamos de la matriz
    public boolean esCoordenadaValida(int fila, int col) {
        return fila >= 0 && fila < TAMANO && col >= 0 && col < TAMANO;
    }

    public Casilla getCasilla(int fila, int col) {
        if (esCoordenadaValida(fila, col)) {
            return casillas[fila][col];
        }
        throw new ArrayIndexOutOfBoundsException("Intentaste acceder a una coordenada mala");
    }

    public int getTamano() { return TAMANO; }
    
    // Recorre todo para ver si ya ganamos (si todas las casillas seguras están abiertas)
    public boolean verificarVictoria() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                // Si no es mina y NO está descubierta, falta juego
                if (!casillas[i][j].esMina() && !casillas[i][j].estaDescubierta()) {
                    return false;
                }
            }
        }
        return true; 
    }
}