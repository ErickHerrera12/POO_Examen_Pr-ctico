package modelo;

import java.io.Serializable;

// Implementamos Serializable para poder guardar el estado de cada casilla en un archivo binario.
public class Casilla implements Serializable {
    
    // Atributos privados para cumplir con el Encapsulamiento.
    private boolean esMina;
    private boolean estaDescubierta;
    private boolean estaMarcada; // Para saber si el usuario le puso una banderita (P)
    private int minasAlrededor; // El número que se muestra (1, 2, 3...)

    public Casilla() {
        // Inicializamos todo en falso o cero al crear la casilla
        this.esMina = false;
        this.estaDescubierta = false;
        this.estaMarcada = false;
        this.minasAlrededor = 0;
    }

    // --- Getters y Setters ---
    // Usamos esto para acceder a los datos privados desde fuera de la clase.

    public boolean esMina() {
        return esMina;
    }

    public void setEsMina(boolean esMina) {
        this.esMina = esMina;
    }

    public boolean estaDescubierta() {
        return estaDescubierta;
    }

    public void setEstaDescubierta(boolean estaDescubierta) {
        this.estaDescubierta = estaDescubierta;
    }

    public boolean estaMarcada() {
        return estaMarcada;
    }

    public void setEstaMarcada(boolean estaMarcada) {
        this.estaMarcada = estaMarcada;
    }

    public int getMinasAlrededor() {
        return minasAlrededor;
    }

    public void setMinasAlrededor(int minasAlrededor) {
        this.minasAlrededor = minasAlrededor;
    }
}