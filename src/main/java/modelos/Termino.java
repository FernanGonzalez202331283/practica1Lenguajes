/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author fernan
 */
public class Termino {
    
    public enum Tipo { CADENA, VARIABLE, NUMERO, CARGAR, TIPO_CONTENIDO }

    private final Tipo tipo;
    private final String valor;   
    private final int fila;

    public Termino(Tipo tipo, String valor, int fila) {
        this.tipo = tipo;
        this.valor = valor;
        this.fila = fila;
    }

    public Tipo getTipo() { return tipo; }
    public String getValor() { return valor; }
    public int getFila() { return fila; }
}
