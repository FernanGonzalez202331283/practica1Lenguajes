/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.List;

/**
 *
 * @author fernan
 */
public class Conector {
   private String tipo;
   private Expresion expresion;
   
   public Conector(String tipo, Expresion expresion){
       this.tipo = tipo;
       this.expresion = expresion;
   }
   
   public String getTipo(){
       return tipo;
   }
   
   public void setTipo(String tipo){
       this.tipo = tipo;
   }
   
   public Expresion getExpresion(){
       return expresion;
   }
   
   public void setExpresion(Expresion expresion){
       this.expresion = expresion;
   }
}
