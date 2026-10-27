/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import java.util.LinkedHashSet;
import java.util.Set;
import modelos.Agente;
import modelos.Comando;
import modelos.Conector;
import modelos.Directiva;
import modelos.Expresion;
import modelos.Programa;
import modelos.Termino;
import modelos.Variable;

/**
 *
 * @author fernan
 */
public class GeneradorPromptZal {
    
    public static String generar(Programa programa) {

        StringBuilder sb = new StringBuilder();

        sb.append("# Prompt generado con PromptZal\n");

        String modelo = directiva(programa, "@modelo");
        String rol = directiva(programa, "@rol");
        String formato = directiva(programa, "@formato");

        if (modelo != null)  sb.append("Modelo destino: ").append(modelo).append("\n");
        if (rol != null)     sb.append("Rol general: ").append(rol).append("\n");
        if (formato != null) sb.append("Formato de respuesta: ").append(formato).append("\n");

        Set<String> yaIncluidos = new LinkedHashSet<>();

        for (String nombre : programa.getEjecuciones()) {

            if (!yaIncluidos.add(nombre)) continue;

            Agente agente = buscarAgente(programa, nombre);
            if (agente == null) continue;   

            agregarAgente(sb, agente);
        }

        sb.append("\n## Resultado solicitado\n");
        for (String exportado : programa.getExportaciones()) {
            sb.append("- ").append(exportado).append("\n");
        }

        return sb.toString();
    }

    private static void agregarAgente(StringBuilder sb, Agente agente) {

        sb.append("\n## Agente: ").append(agente.getNombre()).append("\n");

        if (agente.getContexto() != null) {
            sb.append("Contexto: ").append(agente.getContexto()).append("\n");
        }

        if (!agente.getVariables().isEmpty()) {
            sb.append("Variables disponibles:\n");
            for (Variable v : agente.getVariables()) {
                sb.append("- ").append(v.getNombre()).append(": ")
                  .append(origen(v.getExpresion())).append("\n");
            }
        }

        sb.append("Instrucciones:\n");
        int n = 1;
        for (Comando c : agente.getComandos()) {
            sb.append(n++).append(". ").append(instruccion(c)).append("\n");
        }
    }

    private static String origen(Expresion e) {

        if (e == null) return "";

        StringBuilder sb = new StringBuilder();
        boolean primero = true;

        for (Termino t : e.getItems()) {
            if (!primero) sb.append(" + ");
            primero = false;

            switch (t.getTipo()) {
                case CARGAR:
                    sb.append("contenido del archivo \"").append(t.getValor())
                      .append("\" (adjuntar el archivo)");
                    break;
                case CADENA:
                    sb.append("\"").append(t.getValor()).append("\"");
                    break;
                case VARIABLE:
                    sb.append("el valor de ").append(t.getValor());
                    break;
                default:
                    sb.append(t.getValor());
            }
        }
        return sb.toString();
    }

    private static String instruccion(Comando c) {

        String principal = texto(c.getExpresion());
        String destino = c.getDestino();
        String s;

        switch (c.getTipo()) {

           case "PREGUNTAR": {
                String sobre = conector(c, "SOBRE");
                String desde = conector(c, "DESDE");

                if (sobre != null && desde != null) {
                    s = "Basándote en " + sobre
                            + ", utilizando la información de " + desde
                            + ", responde: " + fin(principal);

                } else if (sobre != null) {
                    s = "Basándote en " + sobre
                            + ", responde: " + fin(principal);

                } else if (desde != null) {
                    s = "Utilizando la información de " + desde
                            + ", responde: " + fin(principal);

                } else {
                    s = "Responde: " + fin(principal);
                }

                s += " Guarda la respuesta como " + destino + ".";
                break;
            }
            case "GENERAR": {
                String desde = conector(c, "DESDE");
                s = "Genera " + principal
                        + (desde != null ? " a partir de: " + desde : "")
                        + ". Guarda el resultado como " + destino + ".";
                break;
            }
            case "RESUMIR": {
                String en = conector(c, "EN");
                s = "Resume " + principal
                        + (en != null ? " en un máximo de " + en : "")
                        + ". Guarda el resultado como " + destino + ".";
                break;
            }
            case "ANALIZAR": {
                String sobre = conector(c, "SOBRE");

                s = "Analiza " + principal
                        + (sobre != null ? " considerando: " + sobre : "")
                        + ". Guarda el resultado como " + destino + ".";
                break;
            }
          case "TRADUCIR": {
                String desde = conector(c, "DESDE");
                String en = conector(c, "EN");

                s = "Traduce " + principal
                        + (desde != null ? " utilizando el contenido de " + desde : "")
                        + (en != null ? " al idioma " + en : "")
                        + ". Guarda el resultado como " + destino + ".";
                break;
            }
            case "CLASIFICAR": {
                String en = conector(c, "EN");
                s = "Clasifica " + principal
                        + (en != null ? " en las categorías: " + en : "")
                        + ". Guarda el resultado como " + destino + ".";
                break;
            }
            case "EXTRAER": {
                String desde = conector(c, "DESDE");
                s = "Extrae " + principal
                        + (desde != null ? " a partir de " + desde : "")
                        + ". Guarda el resultado como " + destino + ".";
                break;
            }
            default: {
                s = "Realiza la operación " + c.getTipo() + " con " + principal
                        + ". Guarda el resultado como " + destino + ".";
            }
        }

        String como = conector(c, "COMO");
        if (como != null) {
            s += " Entrega el resultado en formato " + como + ".";
        }
        return s;
    }

    private static String conector(Comando c, String tipo) {
        for (Conector con : c.getConectores()) {
            if (con.getTipo().equals(tipo)) {
                return texto(con.getExpresion());
            }
        }
        return null;
    }

    private static String texto(Expresion e) {

        if (e == null) return "";

        StringBuilder sb = new StringBuilder();
        for (Termino t : e.getItems()) {
            if (sb.length() > 0) sb.append(" ");
            if (t.getTipo() == Termino.Tipo.CARGAR) {
                sb.append("el archivo \"").append(t.getValor()).append("\"");
            } else {
                sb.append(t.getValor());
            }
        }
        return sb.toString();
    }

    private static String fin(String s) {
        if (s.endsWith("?") || s.endsWith("!") || s.endsWith(".")) return s;
        return s + ".";
    }

    private static String directiva(Programa p, String nombre) {
        for (Directiva d : p.getDirectivas()) {
            if (d.getNombre().equals(nombre)) return d.getValor();
        }
        return null;
    }

    private static Agente buscarAgente(Programa p, String nombre) {
        for (Agente a : p.getAgentes()) {
            if (a.getNombre().equals(nombre)) return a;
        }
        return null;
    }
}
