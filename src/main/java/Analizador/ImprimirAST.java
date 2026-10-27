/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import modelos.Agente;
import modelos.Comando;
import modelos.Conector;
import modelos.Expresion;
import modelos.Programa;
import modelos.Variable;

/**
 *
 * @author fernan
 */
public class ImprimirAST {
    
    public static void imprimir(Programa programa) {

        System.out.println("\n========== ARBOL DE SINTAXIS ABSTRACTA ==========");

        // DIRECTIVAS
        System.out.println("\n--- DIRECTIVAS ---");

        if (programa.getDirectivas().isEmpty()) {
            System.out.println("No hay directivas.");
        } else {
           for (modelos.Directiva d : programa.getDirectivas()) {
                System.out.println("Directiva: " + d.getNombre() + " " + d.getValor());
            }
        }

        // AGENTES
        System.out.println("\n--- AGENTES ---");

        if (programa.getAgentes().isEmpty()) {
            System.out.println("No hay agentes.");
        } else {

            for (Agente agente : programa.getAgentes()) {

                System.out.println("\nAgente: " + agente.getNombre());

                // CONTEXTO
                System.out.println("  Contexto: " + agente.getContexto());

                // VARIABLES
                System.out.println("  Variables:");

                if (agente.getVariables().isEmpty()) {
                    System.out.println("    No hay variables.");
                } else {

                    for (Variable variable : agente.getVariables()) {

                        System.out.println("    Nombre: " + variable.getNombre());

                        Expresion expresion = variable.getExpresion();

                        if (expresion != null) {
                            System.out.println(
                                    "    Expresion: " + expresion.obtenerTexto()
                            );
                        }
                    }
                }

                // COMANDOS
                System.out.println("  Comandos:");

                if (agente.getComandos().isEmpty()) {
                    System.out.println("    No hay comandos.");
                } else {

                    for (Comando comando : agente.getComandos()) {

                        System.out.println("    Tipo: " + comando.getTipo());

                        Expresion expresion = comando.getExpresion();

                        if (expresion != null) {
                            System.out.println(
                                    "    Expresion: " + expresion.obtenerTexto()
                            );
                        }

                        // CONECTORES
                        System.out.println("    Conectores:");

                        if (comando.getConectores().isEmpty()) {
                            System.out.println("      No hay conectores.");
                        } else {

                            for (Conector conector : comando.getConectores()) {

                                System.out.println(
                                        "      Tipo: " + conector.getTipo()
                                );

                                Expresion expresionConector =
                                        conector.getExpresion();

                                if (expresionConector != null) {
                                    System.out.println(
                                            "      Expresion: "
                                            + expresionConector.obtenerTexto()
                                    );
                                }
                            }
                        }

                        // DESTINO
                        System.out.println(
                                "    Destino: " + comando.getDestino()
                        );
                    }
                }
            }
        }

        // EJECUCIONES
        System.out.println("\n--- EJECUCIONES ---");

        if (programa.getEjecuciones().isEmpty()) {
            System.out.println("No hay ejecuciones.");
        } else {

            for (String ejecucion : programa.getEjecuciones()) {
                System.out.println("Ejecutar: " + ejecucion);
            }
        }

        // EXPORTACIONES
        System.out.println("\n--- EXPORTACIONES ---");

        if (programa.getExportaciones().isEmpty()) {
            System.out.println("No hay exportaciones.");
        } else {

            for (String exportacion : programa.getExportaciones()) {
                System.out.println("Exportar: " + exportacion);
            }
        }

        System.out.println("\n========== FIN DEL AST ==========");
    }
}
