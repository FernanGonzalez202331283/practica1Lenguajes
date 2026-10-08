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
public class Agente {
    private String nombre;
    private String contexto;
    
    private List<Variable> variables;
    private List<Comando> comandos;

    public Agente(String nombre) {
        this.nombre = nombre;
        this.variables = new ArrayList<>();
        this.comandos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public List<Variable> getVaribales() {
        return variables;
    }

    public List<Comando> getComandos() {
        return comandos;
    }
    public void agregarVariables(Variable variable){
        variables.add(variable);
    }
    
    public void agregarComando(Comando comando){
        comandos.add(comando);
    }
}
