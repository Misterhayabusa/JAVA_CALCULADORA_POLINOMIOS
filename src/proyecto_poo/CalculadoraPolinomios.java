
import Excepciones.ExPolinomio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;



import javax.script.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class CalculadoraPolinomios extends JFrame implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalculadoraPolinomios().setVisible(true);
            }
        });
    }
    private JTextField txtPolinomio1, txtPolinomio2, txtResultado;
    private JButton btnSumar, btnRestar, btnMultiplicar, btnEvaluar, btnMultiplicarEscalar, btnGraficar;

    public CalculadoraPolinomios() {
        setTitle("Calculadora de Polinomios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        JLabel lblPolinomio1 = new JLabel("Polinomio 1:");
        txtPolinomio1 = new JTextField();
        JLabel lblPolinomio2 = new JLabel("Polinomio 2:");
        txtPolinomio2 = new JTextField();
        JLabel lblResultado = new JLabel("Resultado:");
        txtResultado = new JTextField();
        txtResultado.setEditable(false);

        btnSumar = new JButton("Sumar (+)");
        btnRestar = new JButton("Restar (-)");
        btnMultiplicar = new JButton("Multiplicar (*)");
        btnEvaluar = new JButton("Evaluar");
        btnMultiplicarEscalar = new JButton("Multiplicar por Escalar");
        btnGraficar = new JButton("Graficar");

        btnSumar.addActionListener(this);
        btnRestar.addActionListener(this);
        btnMultiplicar.addActionListener(this);
        btnEvaluar.addActionListener(this);
        btnMultiplicarEscalar.addActionListener(this);
        btnGraficar.addActionListener(this);

        add(lblPolinomio1);
        add(txtPolinomio1);
        add(lblPolinomio2);
        add(txtPolinomio2);
        add(lblResultado);
        add(txtResultado);
        add(btnSumar);
        add(btnRestar);
        add(btnMultiplicar);
        add(btnEvaluar);
        add(btnMultiplicarEscalar);
        add(btnGraficar);
    }

    

    public void actionPerformed(ActionEvent e) {
        String polinomio1 = txtPolinomio1.getText();
        String polinomio2 = txtPolinomio2.getText();
        String resultado = "Hola";
        double numeric =0.0;
        ExPolinomio validacion = new ExPolinomio();
        
        if (e.getSource() == btnSumar) {
            // Lógica para sumar los polinomios
            polinomio1=validacion.revision(polinomio1);
            polinomio2=validacion.revision(polinomio2);
            txtPolinomio1.setText(polinomio1);
            txtPolinomio2.setText(polinomio2);
            
            resultado = sumarPolinomios(polinomio1, polinomio2);
            txtResultado.setText(resultado);
        } else if (e.getSource() == btnRestar) {
            // Lógica para restar los polinomios
            polinomio1=validacion.revision(polinomio1);
            polinomio2=validacion.revision(polinomio2);
            txtPolinomio1.setText(polinomio1);
            txtPolinomio2.setText(polinomio2);
            
            resultado = restarPolinomios(polinomio1, polinomio2);
            txtResultado.setText(resultado);
        } else if (e.getSource() == btnMultiplicar) {
            // Lógica para multiplicar los polinomios
            polinomio1=validacion.revision(polinomio1);
            polinomio2=validacion.revision(polinomio2);
            txtPolinomio1.setText(polinomio1);
            txtPolinomio2.setText(polinomio2);
            
            resultado = multiplicarPolinomios(polinomio1, polinomio2);
            txtResultado.setText(resultado);
        } else if (e.getSource() == btnEvaluar) {
            String valorStr = JOptionPane.showInputDialog("Ingrese el valor a evaluar:");
            double valor = Double.parseDouble(valorStr);
            // Lógica para evaluar el polinomio en el valor dado
            numeric = evaluarPolinomio(polinomio1, valor);
            txtResultado.setText(Double.toString(numeric));
        } else if (e.getSource() == btnMultiplicarEscalar) {
            //validar formato:
            polinomio1=validacion.revision(polinomio1);
            txtPolinomio1.setText(polinomio1);
            
            
            String escalarStr = JOptionPane.showInputDialog("Ingrese el escalar:");
            double escalar = Double.parseDouble(escalarStr);
            // Lógica para multiplicar el polinomio por un escalar
            resultado = multiplicarPorEscalar(polinomio1, escalar);
            txtResultado.setText(resultado);
        } else if (e.getSource() == btnGraficar) {
            // Lógica para graficar el polinomio
            
            graficarPolinomio(polinomio1,polinomio2);
            
        }
    }
    

    private String sumarPolinomios(String polinomio1, String polinomio2) {
        // Convierte los polinomios en arreglos de términos
        String[] terminos1 = polinomio1.split("\\s*\\+\\s*");
        String[] terminos2 = polinomio2.split("\\s*\\+\\s*");

        // Crea un mapa para almacenar los coeficientes de cada grado
        Map<Integer, Integer> coeficientes = new HashMap<>();

        // Suma los coeficientes de los términos del primer polinomio
        for (String termino : terminos1) {
            String[] partes = termino.split("x\\^");
            int coeficiente = Integer.parseInt(partes[0]);
            int grado = Integer.parseInt(partes[1]);
            coeficientes.put(grado, coeficientes.getOrDefault(grado, 0) + coeficiente);
        }

        // Suma los coeficientes de los términos del segundo polinomio
        for (String termino : terminos2) {
            String[] partes = termino.split("x\\^");
            int coeficiente = Integer.parseInt(partes[0]);
            int grado = Integer.parseInt(partes[1]);
            coeficientes.put(grado, coeficientes.getOrDefault(grado, 0) + coeficiente);
        }

        // Construye la cadena del polinomio resultante
        StringBuilder resultado = new StringBuilder();
        boolean primerTermino = true;
        for (Map.Entry<Integer, Integer> entry : coeficientes.entrySet()) {
            int grado = entry.getKey();
            int coeficiente = entry.getValue();

            if (coeficiente != 0) {
                // Agrega el signo "+" si no es el primer término
                if (!primerTermino && coeficiente > 0) {
                    resultado.append(" + ");
                }

                // Agrega el coeficiente y el grado del término
                resultado.append(coeficiente);
                if (grado > 0) {
                    resultado.append("x^").append(grado);
                }

                primerTermino = false;
            }
        }
        
        return acomodarPolinomio(resultado.toString());
        
    }


    // Implementa la lógica para restar los polinomios y retorna el resultado como string pero ya con la operacion matematica
    
    private String restarPolinomios(String polinomio1, String polinomio2) {
        // Convierte los polinomios en arreglos de términos
        String[] terminos1 = polinomio1.split("\\s*\\+\\s*");
        String[] terminos2 = polinomio2.split("\\s*\\+\\s*");

        // Crea un mapa para almacenar los coeficientes de cada grado
        Map<Integer, Integer> coeficientes = new HashMap<>();

        // Resta los coeficientes de los términos del primer polinomio
        for (String termino : terminos1) {
            String[] partes = termino.split("x\\^");
            int coeficiente = Integer.parseInt(partes[0]);
            int grado = Integer.parseInt(partes[1]);
            coeficientes.put(grado, coeficientes.getOrDefault(grado, 0) + coeficiente);
        }

        // Resta los coeficientes de los términos del segundo polinomio
        for (String termino : terminos2) {
            String[] partes = termino.split("x\\^");
            int coeficiente = Integer.parseInt(partes[0]);
            int grado = Integer.parseInt(partes[1]);
            coeficientes.put(grado, coeficientes.getOrDefault(grado, 0) - coeficiente);
        }

        // Construye la cadena del polinomio resultante
        StringBuilder resultado = new StringBuilder();
        boolean primerTermino = true;
        for (Map.Entry<Integer, Integer> entry : coeficientes.entrySet()) {
            int grado = entry.getKey();
            int coeficiente = entry.getValue();

            if (coeficiente != 0) {
                // Agrega el signo "+" si no es el primer término
                if (!primerTermino && coeficiente > 0) {
                    resultado.append(" + ");
                } else if (coeficiente < 0) {
                    resultado.append(" - ");
                    coeficiente = Math.abs(coeficiente);
                }

                // Agrega el coeficiente y el grado del término
                resultado.append(coeficiente);
                if (grado > 0) {
                    resultado.append("x^").append(grado);
                }

                primerTermino = false;
            }
        }
        
        return acomodarPolinomio(resultado.toString());
    }

    

    private String multiplicarPolinomios(String polinomio1, String polinomio2) {
        // Convierte los polinomios en arreglos de términos
        String[] terminos1 = polinomio1.split("\\s*\\+\\s*");
        String[] terminos2 = polinomio2.split("\\s*\\+\\s*");

        // Crea un mapa para almacenar los coeficientes de cada grado
        Map<Integer, Integer> coeficientes = new HashMap<>();

        // Multiplica los coeficientes de los términos de los polinomios
        for (String termino1 : terminos1) {
            String[] partes1 = termino1.split("x\\^");
            int coeficiente1 = Integer.parseInt(partes1[0]);
            int grado1 = Integer.parseInt(partes1[1]);

            for (String termino2 : terminos2) {
                String[] partes2 = termino2.split("x\\^");
                int coeficiente2 = Integer.parseInt(partes2[0]);
                int grado2 = Integer.parseInt(partes2[1]);

                int nuevoCoeficiente = coeficiente1 * coeficiente2;
                int nuevoGrado = grado1 + grado2;

                coeficientes.put(nuevoGrado, coeficientes.getOrDefault(nuevoGrado, 0) + nuevoCoeficiente);
            }
        }

        // Construye la cadena del polinomio resultante
        StringBuilder resultado = new StringBuilder("");
        boolean primerTermino = true;
        for (Map.Entry<Integer, Integer> entry : coeficientes.entrySet()) {
            int grado = entry.getKey();
            int coeficiente = entry.getValue();

            if (coeficiente != 0) {
                // Agrega el signo "+" si no es el primer término
                if (!primerTermino && coeficiente > 0) {
                    resultado.append(" + ");
                } else if (coeficiente < 0) {
                    resultado.append(" - ");
                    coeficiente = Math.abs(coeficiente);
                }

                // Agrega el coeficiente y el grado del término
                resultado.append(coeficiente);
                if (grado > 0) {
                    resultado.append("x^").append(grado);
                }

                primerTermino = false;
            }
        }
        
        
        return acomodarPolinomio(resultado.toString());
    }

    
   
    
    private double evaluarPolinomio(String polinomio, double valor) {
        // Convierte el polinomio en un arreglo de términos
        String[] terminos = polinomio.split("\\s*\\+\\s*");

        // Realiza la evaluación del polinomio en el valor dado
        double resultado = 0.0;
        for (String termino : terminos) {
            String[] partes = termino.split("x\\^");
            double coeficiente = Double.parseDouble(partes[0]);
            int grado = Integer.parseInt(partes[1]);
            resultado += coeficiente * Math.pow(valor, grado);
        }

        return resultado;
    }



    // Implementa la lógica para multiplicar el polinomio por un escalar y retorna el resultado como string pero ya con la operacion matematica
    private String multiplicarPorEscalar(String polinomio, double escalar) {
        // Convierte el polinomio en un arreglo de términos
        String[] terminos = polinomio.split("\\s*\\+\\s*");

        // Realiza la multiplicación por el escalar en cada término
        StringBuilder resultado = new StringBuilder("");
        resultado.append(escalar).append(": ");

        boolean primerTermino = true;
        for (String termino : terminos) {
            String[] partes = termino.split("x\\^");
            double coeficiente = Double.parseDouble(partes[0]);
            int grado = Integer.parseInt(partes[1]);

            double nuevoCoeficiente = coeficiente * escalar;

            // Agrega el signo "+" si no es el primer término
            if (!primerTermino && nuevoCoeficiente > 0) {
                resultado.append(" + ");
            }

            // Agrega el coeficiente y el grado del término
            resultado.append(nuevoCoeficiente);
            if (grado > 0) {
                resultado.append("x^").append(grado);
            }

            primerTermino = false;
        }
        
        return acomodarPolinomio(resultado.toString());
    }
    

    private void graficarPolinomio(String polinomio, String polinomio2) {
        // Implementa la lógica para graficar el polinomio
        JOptionPane.showMessageDialog(this, "Gráfica del polinomio: ");
        Grafica grafica = new Grafica(polinomio,polinomio2);
        grafica.run(polinomio,polinomio2);
    }
    
    
    private String acomodarPolinomio(String polinomio) {
        // Verifica si la cadena contiene el signo "+"
        if (!polinomio.contains("+")) {
            return polinomio; // Devuelve la cadena original sin cambios
        }

        // Convierte los términos del polinomio en un arreglo
        String[] terminos = polinomio.split("\\s*\\+\\s*");

        // Crea un mapa para almacenar los coeficientes de cada grado
        Map<Integer, Integer> coeficientes = new HashMap<>();

        // Procesa cada término del polinomio
        for (String termino : terminos) {
            String[] partes = termino.split("x\\^");
            int coeficiente = Integer.parseInt(partes[0]);
            int grado = Integer.parseInt(partes[1]);
            coeficientes.put(grado, coeficiente);
        }

        // Obtén una lista de las entradas del mapa y ordénalas en orden descendente según el grado
        ArrayList<Map.Entry<Integer, Integer>> listaCoeficientes = new ArrayList<>(coeficientes.entrySet());
        listaCoeficientes.sort((a, b) -> b.getKey().compareTo(a.getKey()));

        // Construye la cadena del polinomio acomodado
        StringBuilder resultado = new StringBuilder();
        boolean primerTermino = true;
        for (Map.Entry<Integer, Integer> entry : listaCoeficientes) {
            int grado = entry.getKey();
            int coeficiente = entry.getValue();

            // Agrega el signo "+" si no es el primer término
            if (!primerTermino && coeficiente >= 0) {
                resultado.append(" + ");
            }

            // Agrega el coeficiente y el grado del término
            resultado.append(coeficiente);
            if (grado > 0) {
                resultado.append("x^").append(grado);
            }

            primerTermino = false;
        }

        return resultado.toString();
    }



}



class Grafica extends javax.swing.JFrame {
    
    

    public Grafica(String polinomio,String polinomio2) {
        initComponents(polinomio,polinomio2);
    }

    private void initComponents(String polinomio,String polinomio2) {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gráfico del polinomio");
        setSize(500, 500);
        setLocationRelativeTo(null);

        JButton btnGraficar = new JButton();
        btnGraficar.setText("Graficar");
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt,polinomio,polinomio2);
            }
        }
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(btnGraficar)
                                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(btnGraficar)
                                .addContainerGap(200, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt,String polinomio,String polinomio2) {
        //String funcion = JOptionPane.showInputDialog(this, "Ingrese la función:");
        String funcion = polinomio;
        String funcion2 = polinomio2;
        if (funcion != null && !funcion.isEmpty()) {
            graficarFunciones(funcion,funcion2);
        }
    }

    
   
    private void graficarFunciones(String funcion1, String funcion2) {
        // Crea dos series de datos XY para representar las funciones
        XYSeries serie1 = new XYSeries("Función 1");
        XYSeries serie2 = new XYSeries("Función 2");

        // Evalúa las funciones para diferentes valores de x y agrega los puntos a las series
        for (double x = -10.0; x <= 10.0; x += 0.1) {
            double y1 = evaluarFuncion(funcion1, x);
            double y2 = evaluarFuncion(funcion2, x);
            serie1.add(x, y1);
            serie2.add(x, y2);
        }

        // Crea un conjunto de datos XY y agrega las series
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie1);
        dataset.addSeries(serie2);

        // Crea el gráfico de líneas
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfica de polinomios", // Título del gráfico
                "x", // Etiqueta del eje X
                "y", // Etiqueta del eje Y
                dataset, // Datos a mostrar en el gráfico
                PlotOrientation.VERTICAL, // Orientación del gráfico
                true, // Incluir leyenda
                true, // Incluir tooltips
                false // No incluir URLs
        );

        // Crea el marco del gráfico y lo muestra
        ChartFrame frame = new ChartFrame("Gráfica de polinomios", chart);
        frame.pack();
        frame.setVisible(true);
    }


    private double evaluarFuncion(String funcion, double x) {
        
        double resultado= evaluarPolinomio(funcion,x);
        // Retornar el resultado
        return resultado;
    }

    public void run(String polinomio,String polinomio2) {
        new Grafica(polinomio,polinomio2).setVisible(true);
        
    }
    
    
    private double evaluarPolinomio(String polinomio, double valor) {
        // Convierte el polinomio en un arreglo de términos
        String[] terminos = polinomio.split("\\s*\\+\\s*");

        // Realiza la evaluación del polinomio en el valor dado
        double resultado = 0.0;
        for (String termino : terminos) {
            String[] partes = termino.split("x\\^");
            double coeficiente = Double.parseDouble(partes[0]);
            int grado = Integer.parseInt(partes[1]);
            resultado += coeficiente * Math.pow(valor, grado);
        }

        return resultado;
    }
}
