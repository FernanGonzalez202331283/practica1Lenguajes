/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author fernan
 */
public class ErrorSintactico {
    private String encontrado;
    private String esperando;
    private int fila;
    private int columna;

    public ErrorSintactico(String encontrado, String esperando, int fila, int columna) {
        this.encontrado = encontrado;
        this.esperando = esperando;
        this.fila = fila;
        this.columna = columna;
    }

    public String getEncontrado() {
        return encontrado;
    }

    public void setEncontrado(String encontrado) {
        this.encontrado = encontrado;
    }

    public String getEsperando() {
        return esperando;
    }

    public void setEsperando(String esperando) {
        this.esperando = esperando;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }   

    @Override
    public String toString() {
        return "ErrorSintactico " + "encontrado =" + encontrado + ", "
                + "esperando=" + esperando + ", "
                + "fila=" + fila + ", "
                + "columna=" + columna;
    }
    
}
