/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author fernan
 */
public class Reportes {
    private static final String CSS_BASE = 
        "<style>" +
        "* { box-sizing: border-box; margin: 0; padding: 0; font-family: 'JetBrains Mono', 'Fira Code', 'Consolas', monospace; }" +
        "body { background-color: #1e1e2e; color: #cdd6f4; padding: 30px; display: flex; justify-content: center; }" +
        ".container { width: 100%; max-width: 1100px; background: #181825; padding: 30px 40px; border-radius: 12px; box-shadow: 0 10px 30px rgba(0,0,0,0.5); border: 1px solid #313244; }" +
        "header { margin-bottom: 25px; border-bottom: 2px solid #313244; padding-bottom: 15px; display: flex; justify-content: space-between; align-items: center; }" +
        "h1 { color: #89b4fa; font-size: 22px; font-weight: 700; font-family: 'Segoe UI', sans-serif; }" +
        ".badge-status { background: #313244; color: #a6adc8; padding: 6px 14px; border-radius: 20px; font-size: 13px; font-weight: 600; font-family: 'Segoe UI', sans-serif; }" +
        "table { width: 100%; border-collapse: collapse; margin-top: 15px; background: #1e1e2e; border-radius: 8px; overflow: hidden; }" +
        "th { background: #313244; color: #cdd6f4; text-align: left; padding: 14px; font-size: 13px; text-transform: uppercase; letter-spacing: 0.5px; border-bottom: 2px solid #45475a; font-family: 'Segoe UI', sans-serif; }" +
        "td { padding: 12px 14px; border-bottom: 1px solid #313244; font-size: 14px; }" +
        "tr:nth-child(even) { background-color: #181825; }" +
        "tr:hover { background-color: #2a2b3d; transition: background 0.2s ease; }" +
        ".badge { padding: 4px 10px; border-radius: 6px; font-size: 12px; font-weight: bold; text-transform: uppercase; display: inline-block; font-family: 'Segoe UI', sans-serif; }" +
        ".badge-lex { background: rgba(243, 139, 168, 0.2); color: #f38ba8; border: 1px solid #f38ba8; }" +
        ".badge-sin { background: rgba(250, 179, 135, 0.2); color: #fab387; border: 1px solid #fab387; }" +
        ".badge-sem { background: rgba(249, 226, 175, 0.2); color: #f9e2af; border: 1px solid #f9e2af; }" +
        ".badge-sim { background: rgba(137, 180, 250, 0.2); color: #89b4fa; border: 1px solid #89b4fa; }" +
        ".ok-card { background: rgba(166, 227, 161, 0.1); border: 1px solid #a6e3a1; color: #a6e3a1; padding: 20px; border-radius: 8px; text-align: center; font-size: 16px; margin-top: 15px; font-family: 'Segoe UI', sans-serif; }" +
        
        "/* Estilos para el Árbol AST */" +
        ".tree-wrapper { background: #11111b; padding: 25px; border-radius: 10px; border: 1px solid #313244; margin-top: 10px; }" +
        "ul.tree, ul.tree ul { list-style: none; padding-left: 24px; position: relative; }" +
        "ul.tree ul { border-left: 2px dotted #45475a; margin-left: 12px; margin-top: 6px; margin-bottom: 6px; }" +
        "ul.tree li { margin: 8px 0; position: relative; font-size: 14px; line-height: 1.6; }" +
        "ul.tree ul li::before { content: '├─ '; position: absolute; left: -22px; color: #585b70; font-weight: bold; }" +
        "ul.tree ul li:last-child::before { content: '└─ '; }" +
        ".ast-tag { padding: 3px 8px; border-radius: 4px; font-size: 11px; font-weight: bold; text-transform: uppercase; margin-right: 6px; letter-spacing: 0.5px; font-family: 'Segoe UI', sans-serif; }" +
        ".tag-root { background: #89b4fa; color: #11111b; }" +
        ".tag-section { background: #cba6f7; color: #11111b; }" +
        ".tag-agent { background: #a6e3a1; color: #11111b; }" +
        ".tag-exec { background: #fab387; color: #11111b; }" +
        ".nodo-title { font-weight: bold; font-size: 15px; }" +
        ".nodo-attr { color: #89dceb; font-weight: 600; }" +
        ".nodo-val { color: #f9e2af; background: #1e1e2e; padding: 2px 8px; border-radius: 4px; border: 1px solid #313244; display: inline-block; }" +
        ".count-pill { font-size: 11px; background: #313244; color: #a6adc8; padding: 2px 7px; border-radius: 10px; font-weight: bold; margin-left: 5px; }" +
        "</style>";

    public static void generarReporteErrores(List<ErrorLexico> errLex, List<ErrorSintactico> errSin, List<ErrorSemantico> errSem, String rutaSalida) throws IOException {
        StringBuilder sb = new StringBuilder();
        int totalErrores = errLex.size() + errSin.size() + errSem.size();

        sb.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
        sb.append("<title>Reporte de Errores - PromptZal</title>").append(CSS_BASE).append("</head><body>");
        sb.append("<div class='container'><header><h1>Reporte de Errores de Compilación</h1>");
        
        if (totalErrores == 0) {
            sb.append("<span class='badge-status' style='color:#a6e3a1; border:1px solid #a6e3a1;'>ESTADO: LIMPIO</span></header>");
            sb.append("<div class='ok-card'>El código compiló correctamente sin errores léxicos, sintácticos ni semánticos.</div>");
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
        sb.append("<div class='container'><header><h1>Tabla de Símbolos</h1>");
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
        sb.append("<div class='container'><header><h1>Árbol de Sintaxis Abstracta (AST)</h1>");
        
        if (programa != null) {
            sb.append("<span class='badge-status' style='color:#89b4fa; border:1px solid #89b4fa;'>ESTADO: GENERADO</span></header>");
            sb.append("<div class='tree-wrapper'>");
            sb.append("<ul class='tree'><li><span class='ast-tag tag-root'>ROOT</span><span class='nodo-title' style='color:#89b4fa;'>Programa</span>");
            sb.append("<ul>");
            
            // Directivas
            sb.append("<li><span class='ast-tag tag-section'>BLOQUE</span><span class='nodo-title' style='color:#cba6f7;'>Directivas</span>")
              .append("<span class='count-pill'>").append(programa.getDirectivas().size()).append("</span></li>");
            
            // Agentes
            sb.append("<li><span class='ast-tag tag-section'>BLOQUE</span><span class='nodo-title' style='color:#cba6f7;'>Agentes Declarados</span>");
            sb.append("<ul>");
            for (Agente a : programa.getAgentes()) {
                sb.append("<li><span class='ast-tag tag-agent'>AGENTE</span><span class='nodo-val'>'").append(a.getNombre()).append("'</span>");
                sb.append("<ul>");
                sb.append("<li><span class='nodo-attr'>Contexto:</span> <span class='nodo-val'>\"").append(a.getContexto()).append("\"</span></li>");
                sb.append("<li><span class='nodo-attr'>Variables Definis:</span> <span class='count-pill'>").append(a.getVariables().size()).append("</span></li>");
                sb.append("<li><span class='nodo-attr'>Comandos Ejecutables:</span> <span class='count-pill'>").append(a.getComandos().size()).append("</span></li>");
                sb.append("</ul></li>");
            }
            sb.append("</ul></li>");
            
            // Ejecuciones y Exportaciones
            sb.append("<li><span class='ast-tag tag-exec'>ACCION</span><span class='nodo-attr'>Ejecuciones:</span> <span class='nodo-val'>").append(programa.getEjecuciones()).append("</span></li>");
            sb.append("<li><span class='ast-tag tag-exec'>ACCION</span><span class='nodo-attr'>Exportaciones:</span> <span class='nodo-val'>").append(programa.getExportaciones()).append("</span></li>");
            
            sb.append("</ul></li></ul></div>");
        } else {
            sb.append("<span class='badge-status' style='color:#f38ba8; border:1px solid #f38ba8;'>ESTADO: FALLIDO</span></header>");
            sb.append("<div class='ok-card' style='border-color:#f38ba8; color:#f38ba8;'>No se generó el AST debido a errores sintácticos.</div>");
        }
        sb.append("</div></body></html>");

        escribirArchivo(rutaSalida, sb.toString());
    }

    private static void escribirArchivo(String ruta, String contenido) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(new File(ruta)), StandardCharsets.UTF_8)) {
            writer.write(contenido);
        }
    }
}