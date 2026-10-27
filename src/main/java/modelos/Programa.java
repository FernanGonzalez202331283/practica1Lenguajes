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
    private List<Directiva> directivas = new ArrayList<>();
    private List<Agente> agentes = new ArrayList<>();
    private List<String> ejecuciones = new ArrayList<>();
    private List<Integer> filasEjecuciones = new ArrayList<>();
    private List<String> exportaciones = new ArrayList<>();
    private List<Integer> filasExportaciones = new ArrayList<>();

    public List<Directiva> getDirectivas() { return directivas; }
    public List<Agente> getAgentes() { return agentes; }
    public List<String> getEjecuciones() { return ejecuciones; }
    public List<String> getExportaciones() { return exportaciones; }

    public int getFilaEjecucion(int i) { return filasEjecuciones.get(i); }
    public int getFilaExportacion(int i) { return filasExportaciones.get(i); }

    public void agregarDirectiva(Directiva d) { directivas.add(d); }
    public void agregarAgente(Agente a) { agentes.add(a); }

    public void agregarEjecucion(String nombre, int fila) {
        ejecuciones.add(nombre);
        filasEjecuciones.add(fila);
    }
    public void agregarEjecucion(String nombre) { agregarEjecucion(nombre, 0); }

    public void agregarExportacion(String nombre, int fila) {
        exportaciones.add(nombre);
        filasExportaciones.add(fila);
    }
    public void agregarExportaciones(String nombre) { agregarExportacion(nombre, 0); }
    
}
