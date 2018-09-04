package vista;

import modelo.Calculadora;
import modelo.Matriz;

public interface IFrontEnd {
    void arranca();
    void setCalc(Calculadora calc);
    void mostrarError(String titulo,String mensaje);
    void mostrarInfo(String titulo, String mensaje);
    void limpiarCalculo();
    void limpiarResultado();
    void imprimeMatriz(Matriz z, String nombre);
    public void imprimeError(String s);
}
