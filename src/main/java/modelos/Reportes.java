/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author fernan
 */
public class Reportes {

    public void generarReporteTokens(Token[] tokens, String rutaArchivo) {

        try {

            FileWriter archivo =
                    new FileWriter(rutaArchivo);

            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Reporte de Tokens</title>");

            archivo.write("<style>");

            archivo.write("* {");
            archivo.write("box-sizing: border-box;");
            archivo.write("}");

            archivo.write("body {");
            archivo.write("font-family: Arial, sans-serif;");
            archivo.write("background: linear-gradient(135deg, #eef2f3, #dfe6e9);");
            archivo.write("margin: 0;");
            archivo.write("padding: 40px;");
            archivo.write("color: #2c3e50;");
            archivo.write("}");

            archivo.write(".contenedor {");
            archivo.write("max-width: 1150px;");
            archivo.write("margin: auto;");
            archivo.write("background-color: white;");
            archivo.write("padding: 30px;");
            archivo.write("border-radius: 14px;");
            archivo.write("box-shadow: 0 5px 18px rgba(0,0,0,0.15);");
            archivo.write("}");

            archivo.write("h1 {");
            archivo.write("text-align: center;");
            archivo.write("margin-top: 0;");
            archivo.write("margin-bottom: 8px;");
            archivo.write("color: #2c3e50;");
            archivo.write("font-size: 30px;");
            archivo.write("}");

            archivo.write(".descripcion {");
            archivo.write("text-align: center;");
            archivo.write("color: #7f8c8d;");
            archivo.write("margin-bottom: 25px;");
            archivo.write("}");

            archivo.write(".contador {");
            archivo.write("text-align: center;");
            archivo.write("background-color: #f8f9fa;");
            archivo.write("padding: 12px;");
            archivo.write("border-radius: 8px;");
            archivo.write("margin-bottom: 25px;");
            archivo.write("font-weight: bold;");
            archivo.write("}");

            archivo.write("table {");
            archivo.write("width: 100%;");
            archivo.write("border-collapse: collapse;");
            archivo.write("border-radius: 8px;");
            archivo.write("overflow: hidden;");
            archivo.write("}");

            archivo.write("th {");
            archivo.write("background: linear-gradient(135deg, #34495e, #2c3e50);");
            archivo.write("color: white;");
            archivo.write("padding: 13px;");
            archivo.write("text-align: center;");
            archivo.write("font-size: 15px;");
            archivo.write("}");

            archivo.write("td {");
            archivo.write("padding: 11px;");
            archivo.write("border-bottom: 1px solid #e0e0e0;");
            archivo.write("text-align: center;");
            archivo.write("}");

            archivo.write("tr:nth-child(even) {");
            archivo.write("background-color: #f8f9fa;");
            archivo.write("}");

            archivo.write("tr:hover {");
            archivo.write("background-color: #eaf2f8;");
            archivo.write("}");

            archivo.write(".lexema {");
            archivo.write("font-weight: bold;");
            archivo.write("padding: 5px 9px;");
            archivo.write("border-radius: 6px;");
            archivo.write("display: inline-block;");
            archivo.write("}");

            archivo.write(".directiva {");
            archivo.write("color: #8e44ad;");
            archivo.write("background-color: #f4ecf7;");
            archivo.write("}");

            archivo.write(".reservada {");
            archivo.write("color: #2980b9;");
            archivo.write("background-color: #ebf5fb;");
            archivo.write("}");

            archivo.write(".comando-ia {");
            archivo.write("color: #27ae60;");
            archivo.write("background-color: #eafaf1;");
            archivo.write("}");

            archivo.write(".funcion {");
            archivo.write("color: #d35400;");
            archivo.write("background-color: #fdf2e9;");
            archivo.write("}");

            archivo.write(".conector {");
            archivo.write("color: #16a085;");
            archivo.write("background-color: #e8f8f5;");
            archivo.write("}");

            archivo.write(".identificador {");
            archivo.write("color: #34495e;");
            archivo.write("background-color: #f2f3f4;");
            archivo.write("}");

            archivo.write(".literal-cadena {");
            archivo.write("color: #795548;");
            archivo.write("background-color: #efebe9;");
            archivo.write("}");

            archivo.write(".literal-entero {");
            archivo.write("color: #b7950b;");
            archivo.write("background-color: #fcf3cf;");
            archivo.write("}");

            archivo.write(".literal-decimal {");
            archivo.write("color: #ca6f1e;");
            archivo.write("background-color: #fbeee6;");
            archivo.write("}");

            archivo.write(".operador-asignacion {");
            archivo.write("color: #c0392b;");
            archivo.write("background-color: #fadbd8;");
            archivo.write("}");

            archivo.write(".operador-concatenacion {");
            archivo.write("color: #e67e22;");
            archivo.write("background-color: #fef5e7;");
            archivo.write("}");

            archivo.write(".delimitador {");
            archivo.write("color: #616a6b;");
            archivo.write("background-color: #eaeded;");
            archivo.write("}");

            archivo.write(".leyenda {");
            archivo.write("margin-top: 25px;");
            archivo.write("padding: 18px;");
            archivo.write("background-color: #f8f9fa;");
            archivo.write("border-radius: 8px;");
            archivo.write("}");

            archivo.write(".leyenda h3 {");
            archivo.write("margin-top: 0;");
            archivo.write("text-align: center;");
            archivo.write("}");

            archivo.write(".leyenda-contenido {");
            archivo.write("display: flex;");
            archivo.write("flex-wrap: wrap;");
            archivo.write("gap: 10px;");
            archivo.write("justify-content: center;");
            archivo.write("}");

            archivo.write(".leyenda span {");
            archivo.write("padding: 7px 11px;");
            archivo.write("border-radius: 6px;");
            archivo.write("font-weight: bold;");
            archivo.write("font-size: 13px;");
            archivo.write("}");

            archivo.write("</style>");

            archivo.write("</head>");
            archivo.write("<body>");

            archivo.write("<div class='contenedor'>");

            archivo.write("<h1>Reporte de Tokens</h1>");

            archivo.write("<p class='descripcion'>");
            archivo.write("Tokens reconocidos por el analizador léxico de PromptZal");
            archivo.write("</p>");

            archivo.write("<div class='contador'>");
            archivo.write("Total de tokens encontrados: ");
            archivo.write(String.valueOf(tokens.length));
            archivo.write("</div>");

            archivo.write("<table>");

            archivo.write("<tr>");
            archivo.write("<th>No.</th>");
            archivo.write("<th>Lexema</th>");
            archivo.write("<th>Tipo</th>");
            archivo.write("<th>Fila</th>");
            archivo.write("<th>Columna</th>");
            archivo.write("</tr>");

            for (int i = 0; i < tokens.length; i++) {

                String clase = obtenerClaseToken(tokens[i].getTipo());

                archivo.write("<tr>");

                archivo.write("<td>");
                archivo.write(String.valueOf(tokens[i].getNumero()));
                archivo.write("</td>");

                archivo.write("<td>");
                archivo.write("<span class='lexema " + clase + "'>");
                archivo.write(tokens[i].getLexema());
                archivo.write("</span>");
                archivo.write("</td>");

                archivo.write("<td>");
                archivo.write(tokens[i].getTipo().toString());
                archivo.write("</td>");

                archivo.write("<td>");
                archivo.write(String.valueOf(tokens[i].getFila()));
                archivo.write("</td>");

                archivo.write("<td>");
                archivo.write(String.valueOf(tokens[i].getColumna()));
                archivo.write("</td>");

                archivo.write("</tr>");
            }

            archivo.write("</table>");

            archivo.write("<div class='leyenda'>");
            archivo.write("<h3>Tipos de Token</h3>");

            archivo.write("<div class='leyenda-contenido'>");

            archivo.write("<span class='directiva'>DIRECTIVA</span>");
            archivo.write("<span class='reservada'>RESERVADA</span>");
            archivo.write("<span class='comando-ia'>COMANDO IA</span>");
            archivo.write("<span class='funcion'>FUNCIÓN</span>");
            archivo.write("<span class='conector'>CONECTOR</span>");
            archivo.write("<span class='identificador'>IDENTIFICADOR</span>");
            archivo.write("<span class='literal-cadena'>LITERAL CADENA</span>");
            archivo.write("<span class='literal-entero'>LITERAL ENTERO</span>");
            archivo.write("<span class='literal-decimal'>LITERAL DECIMAL</span>");
            archivo.write("<span class='operador-asignacion'>ASIGNACIÓN</span>");
            archivo.write("<span class='operador-concatenacion'>CONCATENACIÓN</span>");
            archivo.write("<span class='delimitador'>DELIMITADOR</span>");

            archivo.write("</div>");
            archivo.write("</div>");

            archivo.write("</div>");

            archivo.write("</body>");
            archivo.write("</html>");

            archivo.close();

            System.out.println(
                    "Reporte de tokens generado correctamente."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error al generar el reporte de tokens: "
                    + e.getMessage()
            );
        }
    }

    private String obtenerClaseToken(TipoToken tipo) {

        switch (tipo) {

            case DIRECTIVA:
                return "directiva";

            case RESERVADA:
                return "reservada";

            case COMANDO_IA:
                return "comando-ia";

            case FUNCION:
                return "funcion";

            case CONECTOR:
                return "conector";

            case IDENTIFICADOR:
                return "identificador";

            case LITERAL_CADENA:
                return "literal-cadena";

            case LITERAL_ENTERO:
                return "literal-entero";

            case LITERAL_DECIMAL:
                return "literal-decimal";

            case OPERADOR_ASIGNACION:
                return "operador-asignacion";

            case OPERADOR_CONCATENACION:
                return "operador-concatenacion";

            case DELIMITADOR:
                return "delimitador";

            default:
                return "";
        }
    }

    public void generarReporteErrores(
            ErrorLexico[] errores,
            String rutaArchivo) {

        try {

            FileWriter archivo =
                    new FileWriter(rutaArchivo);

            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Reporte de Errores Léxicos</title>");

            archivo.write("<style>");

            archivo.write("* {");
            archivo.write("box-sizing: border-box;");
            archivo.write("}");

            archivo.write("body {");
            archivo.write("font-family: Arial, sans-serif;");
            archivo.write("background: linear-gradient(135deg, #f5eeee, #eadede);");
            archivo.write("margin: 0;");
            archivo.write("padding: 40px;");
            archivo.write("color: #2c3e50;");
            archivo.write("}");

            archivo.write(".contenedor {");
            archivo.write("max-width: 1100px;");
            archivo.write("margin: auto;");
            archivo.write("background-color: white;");
            archivo.write("padding: 30px;");
            archivo.write("border-radius: 14px;");
            archivo.write("box-shadow: 0 5px 18px rgba(0,0,0,0.15);");
            archivo.write("}");

            archivo.write("h1 {");
            archivo.write("text-align: center;");
            archivo.write("margin-top: 0;");
            archivo.write("margin-bottom: 8px;");
            archivo.write("color: #922b21;");
            archivo.write("font-size: 30px;");
            archivo.write("}");

            archivo.write(".descripcion {");
            archivo.write("text-align: center;");
            archivo.write("color: #7f8c8d;");
            archivo.write("margin-bottom: 25px;");
            archivo.write("}");

            archivo.write(".contador {");
            archivo.write("text-align: center;");
            archivo.write("background-color: #fdf2f2;");
            archivo.write("border: 1px solid #f5b7b1;");
            archivo.write("color: #922b21;");
            archivo.write("padding: 14px;");
            archivo.write("border-radius: 8px;");
            archivo.write("margin-bottom: 25px;");
            archivo.write("font-weight: bold;");
            archivo.write("}");

            archivo.write("table {");
            archivo.write("width: 100%;");
            archivo.write("border-collapse: collapse;");
            archivo.write("border-radius: 8px;");
            archivo.write("overflow: hidden;");
            archivo.write("}");

            archivo.write("th {");
            archivo.write("background: linear-gradient(135deg, #c0392b, #922b21);");
            archivo.write("color: white;");
            archivo.write("padding: 13px;");
            archivo.write("text-align: center;");
            archivo.write("font-size: 15px;");
            archivo.write("}");

            archivo.write("td {");
            archivo.write("padding: 11px;");
            archivo.write("border-bottom: 1px solid #e0e0e0;");
            archivo.write("text-align: center;");
            archivo.write("}");

            archivo.write("tr:nth-child(even) {");
            archivo.write("background-color: #fdf2f2;");
            archivo.write("}");

            archivo.write("tr:hover {");
            archivo.write("background-color: #fbe9e7;");
            archivo.write("}");

            archivo.write(".lexema-error {");
            archivo.write("font-weight: bold;");
            archivo.write("color: #922b21;");
            archivo.write("background-color: #fadbd8;");
            archivo.write("padding: 5px 9px;");
            archivo.write("border-radius: 6px;");
            archivo.write("}");

            archivo.write(".tipo-error {");
            archivo.write("font-weight: bold;");
            archivo.write("color: #c0392b;");
            archivo.write("}");

            archivo.write(".sin-errores {");
            archivo.write("background-color: #eafaf1;");
            archivo.write("color: #27ae60;");
            archivo.write("border: 1px solid #a9dfbf;");
            archivo.write("padding: 18px;");
            archivo.write("border-radius: 8px;");
            archivo.write("text-align: center;");
            archivo.write("font-weight: bold;");
            archivo.write("font-size: 17px;");
            archivo.write("}");

            archivo.write("</style>");

            archivo.write("</head>");
            archivo.write("<body>");

            archivo.write("<div class='contenedor'>");

            archivo.write("<h1>Reporte de Errores Léxicos</h1>");

            archivo.write("<p class='descripcion'>");
            archivo.write("Errores encontrados durante el análisis léxico de PromptZal");
            archivo.write("</p>");

            archivo.write("<div class='contador'>");
            archivo.write("Total de errores encontrados: ");
            archivo.write(String.valueOf(errores.length));
            archivo.write("</div>");

            if (errores.length == 0) {

                archivo.write("<div class='sin-errores'>");
                archivo.write("✓ No se encontraron errores léxicos.");
                archivo.write("</div>");

            } else {

                archivo.write("<table>");

                archivo.write("<tr>");
                archivo.write("<th>No.</th>");
                archivo.write("<th>Lexema</th>");
                archivo.write("<th>Tipo de error</th>");
                archivo.write("<th>Fila</th>");
                archivo.write("<th>Columna</th>");
                archivo.write("</tr>");

                for (int i = 0; i < errores.length; i++) {

                    archivo.write("<tr>");

                    archivo.write("<td>");
                    archivo.write(String.valueOf(i + 1));
                    archivo.write("</td>");

                    archivo.write("<td>");
                    archivo.write("<span class='lexema-error'>");
                    archivo.write(errores[i].getLexema());
                    archivo.write("</span>");
                    archivo.write("</td>");

                    archivo.write("<td>");
                    archivo.write("<span class='tipo-error'>");
                    archivo.write(errores[i].getTipoError());
                    archivo.write("</span>");
                    archivo.write("</td>");

                    archivo.write("<td>");
                    archivo.write(String.valueOf(errores[i].getFila()));
                    archivo.write("</td>");

                    archivo.write("<td>");
                    archivo.write(String.valueOf(errores[i].getColumna()));
                    archivo.write("</td>");

                    archivo.write("</tr>");
                }

                archivo.write("</table>");
            }

            archivo.write("</div>");

            archivo.write("</body>");
            archivo.write("</html>");

            archivo.close();

            System.out.println(
                    "Reporte de errores generado correctamente."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error al generar el reporte de errores: "
                    + e.getMessage()
            );
        }
    }

    public void generarReporteEstadisticas(
            Token[] tokens,
            ErrorLexico[] errores,
            int totalLineas,
            String rutaArchivo) {

        try {

            FileWriter archivo =
                    new FileWriter(rutaArchivo);

            int[] cantidades =
                    new int[TipoToken.values().length];

            for (int i = 0; i < tokens.length; i++) {

                TipoToken tipo = tokens[i].getTipo();

                cantidades[tipo.ordinal()]++;
            }

            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Reporte de Estadísticas</title>");

            archivo.write("<style>");

            archivo.write("* {");
            archivo.write("box-sizing: border-box;");
            archivo.write("}");

            archivo.write("body {");
            archivo.write("font-family: Arial, sans-serif;");
            archivo.write("background: linear-gradient(135deg, #eef2f3, #dfe6e9);");
            archivo.write("margin: 0;");
            archivo.write("padding: 40px;");
            archivo.write("color: #2c3e50;");
            archivo.write("}");

            archivo.write(".contenedor {");
            archivo.write("max-width: 1000px;");
            archivo.write("margin: auto;");
            archivo.write("background-color: white;");
            archivo.write("padding: 30px;");
            archivo.write("border-radius: 14px;");
            archivo.write("box-shadow: 0 5px 18px rgba(0,0,0,0.15);");
            archivo.write("}");

            archivo.write("h1 {");
            archivo.write("text-align: center;");
            archivo.write("margin-top: 0;");
            archivo.write("color: #2c3e50;");
            archivo.write("font-size: 30px;");
            archivo.write("}");

            archivo.write(".descripcion {");
            archivo.write("text-align: center;");
            archivo.write("color: #7f8c8d;");
            archivo.write("margin-bottom: 30px;");
            archivo.write("}");

            archivo.write(".resumen {");
            archivo.write("display: flex;");
            archivo.write("gap: 15px;");
            archivo.write("justify-content: center;");
            archivo.write("flex-wrap: wrap;");
            archivo.write("margin-bottom: 30px;");
            archivo.write("}");

            archivo.write(".tarjeta {");
            archivo.write("flex: 1;");
            archivo.write("min-width: 200px;");
            archivo.write("padding: 20px;");
            archivo.write("border-radius: 10px;");
            archivo.write("text-align: center;");
            archivo.write("box-shadow: 0 3px 8px rgba(0,0,0,0.10);");
            archivo.write("}");

            archivo.write(".tarjeta h2 {");
            archivo.write("margin: 0;");
            archivo.write("font-size: 30px;");
            archivo.write("}");

            archivo.write(".tarjeta p {");
            archivo.write("margin: 8px 0 0 0;");
            archivo.write("font-weight: bold;");
            archivo.write("}");

            archivo.write(".tarjeta-tokens {");
            archivo.write("background-color: #eaf2f8;");
            archivo.write("color: #2874a6;");
            archivo.write("}");

            archivo.write(".tarjeta-lineas {");
            archivo.write("background-color: #eafaf1;");
            archivo.write("color: #239b56;");
            archivo.write("}");

            archivo.write(".tarjeta-errores {");
            archivo.write("background-color: #fdf2f2;");
            archivo.write("color: #c0392b;");
            archivo.write("}");

            archivo.write("table {");
            archivo.write("width: 100%;");
            archivo.write("border-collapse: collapse;");
            archivo.write("border-radius: 8px;");
            archivo.write("overflow: hidden;");
            archivo.write("}");

            archivo.write("th {");
            archivo.write("background: linear-gradient(135deg, #34495e, #2c3e50);");
            archivo.write("color: white;");
            archivo.write("padding: 13px;");
            archivo.write("text-align: center;");
            archivo.write("}");

            archivo.write("td {");
            archivo.write("padding: 11px;");
            archivo.write("text-align: center;");
            archivo.write("border-bottom: 1px solid #e0e0e0;");
            archivo.write("}");

            archivo.write("tr:nth-child(even) {");
            archivo.write("background-color: #f8f9fa;");
            archivo.write("}");

            archivo.write("tr:hover {");
            archivo.write("background-color: #eaf2f8;");
            archivo.write("}");

            archivo.write(".cantidad {");
            archivo.write("font-weight: bold;");
            archivo.write("font-size: 16px;");
            archivo.write("}");

            archivo.write(".pie {");
            archivo.write("margin-top: 25px;");
            archivo.write("text-align: center;");
            archivo.write("font-size: 13px;");
            archivo.write("color: #7f8c8d;");
            archivo.write("}");

            archivo.write("</style>");

            archivo.write("</head>");
            archivo.write("<body>");

            archivo.write("<div class='contenedor'>");

            archivo.write("<h1>Reporte de Estadísticas</h1>");

            archivo.write("<p class='descripcion'>");
            archivo.write("Resumen general del análisis léxico de PromptZal");
            archivo.write("</p>");

            archivo.write("<div class='resumen'>");

            archivo.write("<div class='tarjeta tarjeta-tokens'>");
            archivo.write("<h2>");
            archivo.write(String.valueOf(tokens.length));
            archivo.write("</h2>");
            archivo.write("<p>Total de Tokens</p>");
            archivo.write("</div>");

            archivo.write("<div class='tarjeta tarjeta-lineas'>");
            archivo.write("<h2>");
            archivo.write(String.valueOf(totalLineas));
            archivo.write("</h2>");
            archivo.write("<p>Total de Líneas</p>");
            archivo.write("</div>");

            archivo.write("<div class='tarjeta tarjeta-errores'>");
            archivo.write("<h2>");
            archivo.write(String.valueOf(errores.length));
            archivo.write("</h2>");
            archivo.write("<p>Total de Errores</p>");
            archivo.write("</div>");

            archivo.write("</div>");

            archivo.write("<table>");

            archivo.write("<tr>");
            archivo.write("<th>Tipo de Token</th>");
            archivo.write("<th>Cantidad</th>");
            archivo.write("</tr>");

            TipoToken[] tipos = TipoToken.values();

            for (int i = 0; i < tipos.length; i++) {

                archivo.write("<tr>");

                archivo.write("<td>");
                archivo.write(tipos[i].toString());
                archivo.write("</td>");

                archivo.write("<td class='cantidad'>");
                archivo.write(String.valueOf(cantidades[i]));
                archivo.write("</td>");

                archivo.write("</tr>");
            }

            archivo.write("</table>");

            archivo.write("<div class='pie'>");
            archivo.write("Reporte generado por el Analizador Léxico PromptZal");
            archivo.write("</div>");

            archivo.write("</div>");

            archivo.write("</body>");
            archivo.write("</html>");

            archivo.close();

            System.out.println(
                    "Reporte de estadísticas generado correctamente."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error al generar el reporte de estadísticas: "
                    + e.getMessage()
            );
        }
    }
}