package Tienda;

public class MetodosAux {

    // Comprueba si un String se puede convertir a int
    public static boolean esInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Comprueba si un String se puede convertir a double
    public static boolean esDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarDNI(String dni) {
        return dni.matches("\\d{8}[A-Za-z]");
    }
}
