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
public class Expresion {
    private List<String> terminos;
    
    public Expresion (){
        terminos = new ArrayList<>();
    }

    public List<String> getTerminos() {
        return terminos;
    }
    
    public void agregarTermino(String termino){
        terminos.add(termino);
    }
    
    public String obtenerTexto(){
        String texto = "";
        for (int i = 0; i < terminos.size(); i++) {
            if(i>0){
                texto += " + ";
            }
            texto += terminos.get(i);
        }
        return texto;
    }
}
