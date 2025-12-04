package excepciones;

// Creamos nuestra propia excepcion para cumplir con el requisito de excepciones personalizadas.
// Esto salta cuando el usuario intenta destapar una casilla que ya estaba abierta.
public class CasillaYaDescubiertaException extends Exception {
    
    public CasillaYaDescubiertaException(String mensaje) {
        super(mensaje); // Pasamos el mensaje a la clase padre Exception
    }
}