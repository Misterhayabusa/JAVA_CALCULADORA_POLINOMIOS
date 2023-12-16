/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

import javax.swing.JOptionPane;
import java.util.regex.*;

// Excepción base para errores de formato de polinomio
class FormatoPolinomioException extends Exception {
    public FormatoPolinomioException(String mensaje) {
        super(mensaje);
    }
}

// Excepción específica para errores de coeficiente inválido
class CoeficienteInvalidoException extends FormatoPolinomioException {
    public CoeficienteInvalidoException(String mensaje) {
        super(mensaje);
    }
}

// Excepción específica para errores de exponente inválido
class ExponenteInvalidoException extends FormatoPolinomioException {
    public ExponenteInvalidoException(String mensaje) {
        super(mensaje);
    }
}

// Clase para validar el formato del polinomio
class ValidadorPolinomio {
    private static final Pattern patronTermino = Pattern.compile("(\\+|\\-)?\\d+\\s*x\\s*\\^\\s*\\d+");
    
    public static void validarPolinomio(String polinomio) throws FormatoPolinomioException {
        String[] terminos = polinomio.split("(?=[-+])"); // Separar por "+" o "-"
        
        for (String termino : terminos) {
            if (!patronTermino.matcher(termino).matches()) {
                throw new FormatoPolinomioException("Polinomio inválido: " + termino);
            }
            
            String[] partes = termino.split("\\s*x\\s*\\^\\s*");
            int coeficiente = Integer.parseInt(partes[0]);
            int exponente = Integer.parseInt(partes[1]);
            
            if (coeficiente < 0) {
                throw new CoeficienteInvalidoException("Coeficiente inválido: " + coeficiente);
            }
            
            if (exponente < 0) {
                throw new ExponenteInvalidoException("Exponente inválido: " + exponente);
            }
        }
    }
}

// Ejemplo de uso
public class ExPolinomio {
    public static String revision(String arg) {
        String polinomio = JOptionPane.showInputDialog("Ingrese un formato válido: coeficientex^exponente");
        
        try {
            ValidadorPolinomio.validarPolinomio(polinomio);
            JOptionPane.showMessageDialog(null, "Polinomio válido");
        } catch (FormatoPolinomioException e) {
            JOptionPane.showMessageDialog(null, "Error de formato: " + e.getMessage());
            return revision(arg); // Vuelve a solicitar el polinomio
        }
        
        return polinomio;
    }
}
