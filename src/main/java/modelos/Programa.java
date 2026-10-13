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
public class Programa {
    private List<String> directivas;
    private List<Agente> agentes;
    private List<String> ejecuciones;
    private List<String> exportaciones;
    
    public Programa(){
        directivas = new ArrayList<>();
        agentes = new ArrayList<>();
        ejecuciones = new ArrayList<>();
        exportaciones = new ArrayList<>();
    }

    public List<String> getDirectivas() {
        return directivas;
    }

    public List<Agente> getAgentes() {
        return agentes;
    }

    public List<String> getEjecuciones() {
        return ejecuciones;
    }

    public List<String> getExportaciones() {
        return exportaciones;
    }

    public void agregarDirectiva(String directiva){
        directivas.add(directiva);
    }
    
    public void agregarAgente(Agente agente){
        agentes.add(agente);
    }
    
    public void agregarEjecucion(String ejecucion){
        ejecuciones.add(ejecucion);
    }
    
    public void agregarExportaciones(String exportacion){
        exportaciones.add(exportacion);
    }
    
}
