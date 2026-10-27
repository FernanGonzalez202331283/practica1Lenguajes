/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import java.util.ArrayList;
import java.util.List;
import modelos.Agente;
import modelos.Comando;
import modelos.Conector;
import modelos.ErrorSemantico;
import modelos.Expresion;
import modelos.Programa;
import modelos.Simbolo;
import modelos.TablaSimbolos;
import modelos.Variable;

/**
 *
 * @author fernan
 */
public class AnalizadorSemantico {
    
    private final Programa programa;
    private final TablaSimbolos tablaSimbolos;
    private final List<ErrorSemantico> errores = new ArrayList<>();

    public AnalizadorSemantico(Programa programa, TablaSimbolos tablaSimbolos) {
        this.programa = programa;
        this.tablaSimbolos = tablaSimbolos;
    }

    public void analizar() {
        verificarContextos();
        verificarAgentesDuplicados();
        verificarVariablesDuplicadas();
        verificarResultadosDuplicados();
        verificarEjecuciones();
        verificarVariables();
        verificarExportaciones();
    }

    public List<ErrorSemantico> getErrores() {
        return errores;
    }

    private void error(String descripcion, int fila) {
        errores.add(new ErrorSemantico(descripcion, fila));
    }

    private void verificarContextos() {
        for (Agente a : programa.getAgentes()) {
            if (a.getContexto() != null && a.getContexto().trim().isEmpty()) {
                error("el agente '" + a.getNombre()
                        + "' debe tener un contexto con contenido.", a.getFila());
            }
        }
    }

    private void verificarAgentesDuplicados() {
        List<String> vistos = new ArrayList<>();
        for (Agente a : programa.getAgentes()) {
            if (vistos.contains(a.getNombre())) {
                error("el agente '" + a.getNombre() + "' esta duplicado.", a.getFila());
            } else {
                vistos.add(a.getNombre());
            }
        }
    }

    private void verificarVariablesDuplicadas() {
        for (Agente a : programa.getAgentes()) {
            List<String> vistos = new ArrayList<>();
            for (Variable v : a.getVariables()) {
                if (vistos.contains(v.getNombre())) {
                    error("la variable '" + v.getNombre()
                            + "' ya fue declarada en el agente '" + a.getNombre() + "'.",
                            v.getFila());
                } else {
                    vistos.add(v.getNombre());
                }
            }
        }
    }

    private void verificarResultadosDuplicados() {
        for (Agente a : programa.getAgentes()) {
            List<String> resultados = new ArrayList<>();

            for (Comando c : a.getComandos()) {
                String nombre = c.getDestino();
                if (nombre == null) continue;

                for (Variable v : a.getVariables()) {
                    if (nombre.equals(v.getNombre())) {
                        error("el resultado '" + nombre
                                + "' ya existe como variable en el agente '"
                                + a.getNombre() + "'.", c.getFilaDestino());
                        break;
                    }
                }

                if (resultados.contains(nombre)) {
                    error("el resultado '" + nombre
                            + "' ya fue declarado en el agente '"
                            + a.getNombre() + "'.", c.getFilaDestino());
                } else {
                    resultados.add(nombre);
                }
            }
        }
    }

    private void verificarEjecuciones() {
        for (int i = 0; i < programa.getEjecuciones().size(); i++) {
            String nombre = programa.getEjecuciones().get(i);
            boolean existe = false;

            for (Agente a : programa.getAgentes()) {
                if (a.getNombre().equals(nombre)) { existe = true; break; }
            }

            if (!existe) {
                error("se intenta ejecutar el agente '" + nombre + "', pero no existe.",
                        programa.getFilaEjecucion(i));
            }
        }
    }

    private void verificarVariables() {
        for (Agente a : programa.getAgentes()) {
            List<String> disponibles = new ArrayList<>();

            for (Variable v : a.getVariables()) {
                verificarExpresion(v.getExpresion(), disponibles, a.getNombre());
                disponibles.add(v.getNombre());
            }

            for (Comando c : a.getComandos()) {
                verificarExpresion(c.getExpresion(), disponibles, a.getNombre());

                for (Conector con : c.getConectores()) {
                    verificarExpresion(con.getExpresion(), disponibles, a.getNombre());
                }

                if (c.getDestino() != null) {
                    disponibles.add(c.getDestino());
                }
            }
        }
    }

    private void verificarExpresion(Expresion e, List<String> disponibles, String agente) {
        if (e == null) return;

        List<String> terminos = e.getTerminos();
        for (int i = 0; i < terminos.size(); i++) {
            if (e.esVariable(i) && !disponibles.contains(terminos.get(i))) {
                error("la variable '" + terminos.get(i)
                        + "' no ha sido declarada en el agente '" + agente + "'.",
                        e.getFila(i));
            }
        }
    }

    private void verificarExportaciones() {
        List<String> revisadas = new ArrayList<>();

        for (int i = 0; i < programa.getExportaciones().size(); i++) {
            String nombre = programa.getExportaciones().get(i);
            int fila = programa.getFilaExportacion(i);

            if (revisadas.contains(nombre)) {
                error("el identificador '" + nombre
                        + "' esta duplicado en las exportaciones.", fila);
                continue;
            }
            revisadas.add(nombre);

            boolean valido = false;
            for (Simbolo s : tablaSimbolos.getSimbolos()) {
                if (s.getTipo().equals("AGENTE")) continue;
                if (s.getNombre().equals(nombre)
                        && programa.getEjecuciones().contains(s.getAmbito())) {
                    valido = true;
                    break;
                }
            }

            if (!valido) {
                error("no se puede exportar '" + nombre
                        + "' porque no pertenece a una variable o resultado "
                        + "de un agente ejecutado.", fila);
            }
        }
    }
}
