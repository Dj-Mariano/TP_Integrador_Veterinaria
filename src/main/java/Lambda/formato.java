package Lambda;

public class formato implements FormatoString {
    @Override
    public String darFormato(String cadena) {
        return cadena.isEmpty() ? cadena : cadena.substring(0, 1).toUpperCase() + cadena.substring(1).toLowerCase();
    }
}
