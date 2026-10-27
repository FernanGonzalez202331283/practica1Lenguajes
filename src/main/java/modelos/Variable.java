/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author fernan
 */
public class Variable {
    private String nombre;
    private Expresion expresion;
    private int fila;
    
    public Variable(String nombre, Expresion expresion, int fila){
        this.nombre = nombre;
        this.expresion = expresion;
        this.fila = fila;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public Expresion getExpresion(){
        return expresion;
    }
    
    public void setExpresion(Expresion expresion){
        this.expresion = expresion;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }
}
