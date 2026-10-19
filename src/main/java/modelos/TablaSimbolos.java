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
public class TablaSimbolos {
    private List<Simbolo> simbolos;
    
    public TablaSimbolos(){
        simbolos = new ArrayList<>();
    }
    
    public void agregarSimbolo(Simbolo simbolo){
        simbolos.add(simbolo);
    }
    
    public List<Simbolo> getSimbolos(){
        return simbolos;
    }
    
    public boolean existeAgente(String nombre){
        for(Simbolo simbolo: simbolos){
            if(simbolo.getTipo().equals("AGENTE")&& simbolo.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }
    
    public boolean existeVariable(String nombre, String ambito){
        for(Simbolo simbolo: simbolos){
            if(!simbolo.getTipo().equals("AGENTE") && simbolo.getNombre().equals(nombre) && simbolo.getAmbito().equals(ambito)){
                return true;
            }
        }
        return false;
    }
    
    public boolean existeResultado(String nombre, String ambito) {
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getTipo().equals("RESULTADO_COMANDO")
                    && simbolo.getNombre().equals(nombre)
                    && simbolo.getAmbito().equals(ambito)) {
                return true;
            }
        }

        return false;
    }
    
    public void eliminarSimbolosAgente(String nombreAgente) {

        simbolos.removeIf(simbolo
                -> simbolo.getNombre().equals(nombreAgente)
                || simbolo.getAmbito().equals(nombreAgente));
    }
}
