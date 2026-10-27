/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fernan
 */
public class Comando {
    private String tipo;
    private Expresion expresion; 
    private int fila;
    private int filaDestino;
    private List<Conector> conectores;
    private String destino;
    
    public Comando(String tipo){
        this.tipo = tipo;
        this.conectores = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Expresion getExpresion() {
        return expresion;
    }

    public void setExpresion(Expresion expresion) {
        this.expresion = expresion;
    }

    public List<Conector> getConectores() {
        return conectores;
    }

    public void agregarConector(Conector conector){
        conectores.add(conector);
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getFilaDestino() { return filaDestino; }
    public void setFilaDestino(int filaDestino) {  this.filaDestino = filaDestino; }
    
}
