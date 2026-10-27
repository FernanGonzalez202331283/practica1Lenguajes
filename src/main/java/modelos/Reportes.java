/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author fernan
 */
public class Reportes {
    private static final String CSS_BASE = 
        "<style>" +
        "* { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }" +
        "body { background-color: #1e1e2e; color: #cdd6f4; padding: 30px; display: flex; justify-content: center; }" +
        ".container { width: 100%; max-width: 1100px; background: #181825; padding: 25px 35px; border-radius: 12px; box-shadow: 0 10px 30px rgba(0,0,0,0.5); border: 1px solid #313244; }" +
        "header { margin-bottom: 25px; border-bottom: 1px solid #313244; padding-bottom: 15px; display: flex; justify-content: space-between; align-items: center; }" +
        "h1 { color: #89b4fa; font-size: 24px; font-weight: 600; display: flex; align-items: center; gap: 10px; }" +
        ".badge-status { background: #313244; color: #a6adc8; padding: 6px 14px; border-radius: 20px; font-size: 13px; font-weight: 600; }" +
        "table { width: 100%; border-collapse: collapse; margin-top: 15px; background: #1e1e2e; border-radius: 8px; overflow: hidden; }" +
        "th { background: #313244; color: #cdd6f4; text-align: left; padding: 14px; font-size: 13px; text-transform: uppercase; letter-spacing: 0.5px; border-bottom: 2px solid #45475a; }" +
        "td { padding: 12px 14px; border-bottom: 1px solid #313244; font-size: 14px; font-family: 'Consolas', monospace; }" +
        "tr:nth-child(even) { background-color: #181825; }" +
        "tr:hover { background-color: #2a2b3d; transition: background 0.2s ease; }" +
        ".badge { padding: 4px 10px; border-radius: 6px; font-size: 12px; font-weight: bold; text-transform: uppercase; display: inline-block; }" +
        ".badge-lex { background: rgba(243, 139, 168, 0.2); color: #f38ba8; border: 1px solid #f38ba8; }" +
        ".badge-sin { background: rgba(250, 179, 135, 0.2); color: #fab387; border: 1px solid #fab387; }" +
        ".badge-sem { background: rgba(249, 226, 175, 0.2); color: #f9e2af; border: 1px solid #f9e2af; }" +
        ".badge-sim { background: rgba(137, 180, 250, 0.2); color: #89b4fa; border: 1px solid #89b4fa; }" +
        ".ok-card { background: rgba(166, 227, 161, 0.1); border: 1px solid #a6e3a1; color: #a6e3a1; padding: 20px; border-radius: 8px; text-align: center; font-size: 16px; margin-top: 15px; }" +
        "ul.tree { list-style: none; padding-left: 0; margin-top: 15px; }" +
        "ul.tree ul { padding-left: 20px; border-left: 2px solid #45475a; margin-left: 10px; margin-top: 5px; margin-bottom: 5px; }" +
        "ul.tree li { margin: 6px 0; font-family: 'Consolas', monospace; font-size: 14px; }" +
        ".nodo-title { color: #89b4fa; font-weight: bold; }" +
        ".nodo-attr { color: #a6adc8; }" +
        ".nodo-val { color: #a6e3a1; }" +
        "</style>";

    public static void generarReporteErrores(List<ErrorLexico> errLex, List<ErrorSintactico> errSin, List<ErrorSemantico> errSem, String rutaSalida) throws IOException {
        StringBuilder sb = new StringBuilder();
        int totalErrores = errLex.size() + errSin.size() + errSem.size();

        sb.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
        sb.append("<title>Reporte de Errores - PromptZal</title>").append(CSS_BASE).append("</head><body>");
        sb.append("<div class='container'><header><h1>Reporte de Errores de Compilación</h1>");
        
        if (totalErrores == 0) {
            sb.append("<span class='badge-status' style='color:#a6e3a1; border:1px solid #a6e3a1;'>ESTADO: LIMPIO</span></header>");
            sb.append("<div class='ok-card'> El código compiló correctamente sin errores léxicos, sintácticos ni semánticos.</div>");
        } else {
            sb.append("<span class='badge-status' style='color:#f38ba8; border:1px solid #f38ba8;'>TOTAL ERRORES: ").append(totalErrores).append("</span></header>");
            sb.append("<table><thead><tr><th>Tipo</th><th>Fila</th><th>Columna</th><th>Descripción / Detalle</th></tr></thead><tbody>");

            for (ErrorLexico e : errLex) {
                sb.append("<tr><td><span class='badge badge-lex'>Léxico</span></td><td>")
                  .append(e.getFila()).append("</td><td>").append(e.getColumna())
                  .append("</td><td>Lexema no reconocido: <span style='color:#f38ba8;'>'").append(e.getLexema()).append("'</span></td></tr>");
            }
            for (ErrorSintactico e : errSin) {
                sb.append("<tr><td><span class='badge badge-sin'>Sintáctico</span></td><td>")
                  .append(e.getFila()).append("</td><td>").append(e.getColumna())
                  .append("</td><td>Encontrado: <span style='color:#fab387;'>'").append(e.getEncontrado())
                  .append("'</span> | Se esperaba: <span style='color:#a6e3a1;'>'").append(e.getEsperando()).append("'</span></td></tr>");
            }
            for (ErrorSemantico e : errSem) {
                sb.append("<tr><td><span class='badge badge-sem'>Semántico</span></td><td>")
                  .append(e.getFila()).append("</td><td>-</td><td>")
                  .append(e.getDescripcion()).append("</td></tr>");
            }
            sb.append("</tbody></table>");
        }
        sb.append("</div></body></html>");

        escribirArchivo(rutaSalida, sb.toString());
    }

    public static void generarReporteSimbolos(TablaSimbolos tabla, String rutaSalida) throws IOException {
        StringBuilder sb = new StringBuilder();
        int totalSimbolos = (tabla != null && tabla.getSimbolos() != null) ? tabla.getSimbolos().size() : 0;

        sb.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
        sb.append("<title>Tabla de Símbolos - PromptZal</title>").append(CSS_BASE).append("</head><body>");
        sb.append("<div class='container'><header><h1> Tabla de Símbolos</h1>");
        sb.append("<span class='badge-status'>SÍMBOLOS REGISTRADOS: ").append(totalSimbolos).append("</span></header>");
        sb.append("<table><thead><tr><th>Nombre</th><th>Tipo</th><th>Ámbito</th><th>Fila</th></tr></thead><tbody>");

        if (tabla != null && tabla.getSimbolos() != null) {
            for (Simbolo s : tabla.getSimbolos()) {
                sb.append("<tr><td><span style='color:#cba6f7; font-weight:bold;'>").append(s.getNombre()).append("</span></td>")
                  .append("<td><span class='badge badge-sim'>").append(s.getTipo()).append("</span></td>")
                  .append("<td><span style='color:#89dceb;'>").append(s.getAmbito()).append("</span></td>")
                  .append("<td>").append(s.getFila()).append("</td></tr>");
            }
        }
        sb.append("</tbody></table></div></body></html>");

        escribirArchivo(rutaSalida, sb.toString());
    }

    public static void generarReporteAST(Programa programa, String rutaSalida) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
        sb.append("<title>Reporte de AST - PromptZal</title>").append(CSS_BASE).append("</head><body>");
        sb.append("<div class='container'><header><h1>🌳 Árbol de Sintaxis Abstracta (AST)</h1></header>");

        if (programa != null) {
            sb.append("<ul class='tree'><li><span class='nodo-title'> Root: Programa</span>");
            sb.append("<ul>");
            
            // Directivas
            sb.append("<li><span class='nodo-title'> Directivas</span> <span class='nodo-attr'>(").append(programa.getDirectivas().size()).append(")</span></li>");
            
            // Agentes
            sb.append("<li><span class='nodo-title'>🤖 Agentes</span>");
            sb.append("<ul>");
            for (Agente a : programa.getAgentes()) {
                sb.append("<li><span class='nodo-title'>Agente:</span> <span class='nodo-val'>'").append(a.getNombre()).append("'</span>");
                sb.append("<ul>");
                sb.append("<li><span class='nodo-attr'>Contexto:</span> <span class='nodo-val'>\"").append(a.getContexto()).append("\"</span></li>");
                sb.append("<li><span class='nodo-attr'>Variables (").append(a.getVariables().size()).append(")</span></li>");
                sb.append("<li><span class='nodo-attr'>Comandos (").append(a.getComandos().size()).append(")</span></li>");
                sb.append("</ul></li>");
            }
            sb.append("</ul></li>");
            
            sb.append("<li><span class='nodo-title'> Ejecuciones:</span> <span class='nodo-val'>").append(programa.getEjecuciones()).append("</span></li>");
            sb.append("<li><span class='nodo-title'> Exportaciones:</span> <span class='nodo-val'>").append(programa.getExportaciones()).append("</span></li>");
            
            sb.append("</ul></li></ul>");
        } else {
            sb.append("<div class='ok-card' style='border-color:#f38ba8; color:#f38ba8;'>No se generó el AST debido a errores sintácticos.</div>");
        }
        sb.append("</div></body></html>");

        escribirArchivo(rutaSalida, sb.toString());
    }

    private static void escribirArchivo(String ruta, String contenido) throws IOException {
        try (FileWriter writer = new FileWriter(new File(ruta))) {
            writer.write(contenido);
        }
    }
}