package vista;

import modelo.Calculadora;
import modelo.Matriz;

public interface IFrontEnd {
    /**
     * Inicia la ventana.
     */
    void arranca();

    /**
     * Establece el atributo calc de this como el parametro calc.
     * <b> pre: </b> el parametro calc no es nulo.
     * @param calc parametro de tipo Calculadora.
     */
    void setCalc(Calculadora calc);

    /**
     * Muestra un cartel de error dado por los parametros.
     * @param titulo es el titulo del cartel.
     * @param mensaje es el cuerpo del cartel.
     */
    void mostrarError(String titulo,String mensaje);
    
    /**
     * Muestra un cartel de información dado por los parametros.
     * @param titulo es el titulo del cartel.
     * @param mensaje es el cuerpo del cartel.
     */
    void mostrarInfo(String titulo, String mensaje);

    /**
     * Limpia el cuadro donde se ingresan las instrucciones.
     */
    void limpiarCalculo();
    
    /**
     * Limpia el cuadro donde se muestran los resultados.
     */
    void limpiarResultado();

    /**
     * Muestra una matriz en el cuadro de resultados.
     * <b> pre: </b> matriz no nula.
     * @param z es la matriz a imprimir - z != null
     * @param nombre es el nombre de la matriz nombre != null
     */
    void imprimeMatriz(Matriz z, String nombre);

    /**
     * Imprime el contenido de la cadena 's' en el Log de errores.
     * @param s es la cadena que se imprimira.
     */
    public void imprimeError(String s);
}
