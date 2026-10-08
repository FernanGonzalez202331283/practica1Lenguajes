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
    private List<String> directivas;
    private List<Agente> agentes;
    private List<String> ejercuciones;
    private List<String> exportaciones;

    public Conector(List<String> directivas, List<Agente> agentes, List<String> ejercuciones, List<String> exportaciones) {
        this.directivas = directivas;
        this.agentes = agentes;
        this.ejercuciones = ejercuciones;
        this.exportaciones = exportaciones;
    }
    
    
}
