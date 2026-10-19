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
import modelos.Expresion;
import modelos.Programa;
import modelos.TablaSimbolos;
import modelos.Variable;

/**
 *
 * @author fernan
 */
public class AnalizadorSemantico {
    private Programa programa;
    private TablaSimbolos tablaSimbolos;
    private List<String> errores;
    List<String> nombresAgentes = new ArrayList<>();

    public AnalizadorSemantico(Programa programa, TablaSimbolos tablaSimbolos) {
        this.programa = programa;
        this.tablaSimbolos = tablaSimbolos;
        this.errores = new ArrayList<>();
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
    
    private void verificarAgentesDuplicados() {

        List<String> nombresAgentes = new ArrayList<>();

        for (Agente agente : programa.getAgentes()) {

            String nombre = agente.getNombre();

            if (nombresAgentes.contains(nombre)) {

                errores.add(
                    "Error semantico: el agente '"
                    + nombre
                    + "' esta duplicado."
                );

            } else {

                nombresAgentes.add(nombre);
            }
        }
    }
    
    private void verificarResultadosDuplicados() {
        for (Agente agente : programa.getAgentes()) {

            List<String> nombresResultados = new ArrayList<>();

            for (Comando comando : agente.getComandos()) {

                String nombre = comando.getDestino();

                if (nombre == null) {
                    continue;
                }

                for (Variable variable : agente.getVariables()) {

                    if (nombre.equals(variable.getNombre())) {

                        errores.add(
                            "Error semantico: el resultado '"
                            + nombre
                            + "' ya existe como variable en el agente '"
                            + agente.getNombre()
                            + "'."
                        );

                        break;
                    }
                }

                // Revisar si el resultado ya fue utilizado
                // como destino de otro comando.
                if (nombresResultados.contains(nombre)) {

                    errores.add(
                        "Error semantico: el resultado '"
                        + nombre
                        + "' ya fue declarado en el agente '"
                        + agente.getNombre()
                        + "'."
                    );

                } else {

                    nombresResultados.add(nombre);
                }
            }
        }
    }
    
    private void verificarVariablesDuplicadas() {
        for (Agente agente : programa.getAgentes()) {

            List<String> nombresVariables = new ArrayList<>();
            for (Variable variable : agente.getVariables()) {

                String nombre = variable.getNombre();

                if (nombresVariables.contains(nombre)) {

                    errores.add(
                        "Error semantico en fila "
                        + variable.getFila()
                        + ": la variable '"
                        + nombre
                        + "' ya fue declarada en el agente '"
                        + agente.getNombre()
                        + "'."
                    );

                } else {

                    nombresVariables.add(nombre);
                }
            }
        }
    }
    
    private void verificarContextos() {

        for (Agente agente : programa.getAgentes()) {

            String contexto = agente.getContexto();
            if (contexto == null || contexto.trim().isEmpty()) {

                errores.add(
                    "Error semantico: el agente '"
                    + agente.getNombre()
                    + "' debe tener un contexto con contenido."
                );
            }
        }
    }
    
    private void verificarEjecuciones() {

        for (String nombreEjecutado : programa.getEjecuciones()) {

            boolean existe = false;

            for (Agente agente : programa.getAgentes()) {

                if (agente.getNombre().equals(nombreEjecutado)) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {

                errores.add(
                    "Error semantico: se intenta ejecutar el agente '"
                    + nombreEjecutado
                    + "', pero no existe."
                );
            }
        }
    }
    
    private void verificarVariables() {

        for (Agente agente : programa.getAgentes()) {

            List<String> variablesDisponibles = new ArrayList<>();

            // Primero revisamos las variables declaradas.
            for (Variable variable : agente.getVariables()) {

                variablesDisponibles.add(variable.getNombre());
            }
            
            for (Comando comando : agente.getComandos()) {

                if (!comando.getTipo().equals("GENERAR")) {

                    verificarExpresion(
                        comando.getExpresion(),
                        variablesDisponibles,
                        agente.getNombre()
                    );

                } else {

                    Expresion expresion = comando.getExpresion();

                    if (expresion != null) {

                        List<String> terminos = expresion.getTerminos();

                        for (int i = 1; i < terminos.size(); i++) {

                            if (expresion.esVariable(i)) {

                                String nombre = terminos.get(i);

                                if (!variablesDisponibles.contains(nombre)) {

                                    errores.add(
                                        "Error semantico en fila "
                                        + expresion.getFila(i)
                                        + ": la variable '"
                                        + nombre
                                        + "' no ha sido declarada en el agente '"
                                        + agente.getNombre()
                                        + "'."
                                    );
                                }
                            }
                        }
                    }
                }

                // Los argumentos de los conectores sí se validan.
                for (Conector conector : comando.getConectores()) {

                    verificarExpresion(
                        conector.getExpresion(),
                        variablesDisponibles,
                        agente.getNombre()
                    );
                }

                // Registrar el resultado del comando.
                if (comando.getDestino() != null) {
                    variablesDisponibles.add(comando.getDestino());
                }
            }
        }
    }
    
    private void verificarExpresion(
            Expresion expresion,
            List<String> variablesDisponibles,
            String nombreAgente) {

        if (expresion == null) {
            return;
        }

        List<String> terminos = expresion.getTerminos();

        for (int i = 0; i < terminos.size(); i++) {

            if (expresion.esVariable(i)) {

                String nombre = terminos.get(i);

                if (!variablesDisponibles.contains(nombre)) {

                    errores.add(
                        "Error semantico en fila "
                        + expresion.getFila(i)
                        + ": la variable '"
                        + nombre
                        + "' no ha sido declarada en el agente '"
                        + nombreAgente
                        + "'."
                    );
                }
            }
        }
    }
    
    private void verificarExportaciones() {

        // Revisar cada identificador indicado en EXPORTAR
        for (String nombreExportado : programa.getExportaciones()) {

            boolean existeEnAgenteEjecutado = false;

            // Recorrer los símbolos registrados
            for (modelos.Simbolo simbolo : tablaSimbolos.getSimbolos()) {

                // Los agentes no se pueden exportar directamente
                if (simbolo.getTipo().equals("AGENTE")) {
                    continue;
                }

                // Comprobar si el nombre coincide con el símbolo
                if (simbolo.getNombre().equals(nombreExportado)) {

                    // Obtener el agente al que pertenece la variable o resultado
                    String ambito = simbolo.getAmbito();

                    // Comprobar si ese agente se ejecuta
                    if (programa.getEjecuciones().contains(ambito)) {
                        existeEnAgenteEjecutado = true;
                        break;
                    }
                }
            }

            // Si no se encontró en ningún agente ejecutado, registrar error
            if (!existeEnAgenteEjecutado) {
                errores.add(
                    "Error semantico: no se puede exportar '" + nombreExportado
                    + "' porque no pertenece a una variable o resultado "
                    + "de un agente ejecutado."
                );
            }
        }
    }
    
    public List<String> getErrores() {
        return errores;
    }
}
