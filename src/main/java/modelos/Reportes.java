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
    
    public void generarReporteTokens(Token[] tokens) {

        try {

            FileWriter archivo = new FileWriter("reporte_tokens.html");

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
            archivo.write("max-width: 1100px;");
            archivo.write("margin: auto;");
            archivo.write("background-color: white;");
            archivo.write("padding: 30px;");
            archivo.write("border-radius: 12px;");
            archivo.write("box-shadow: 0 5px 15px rgba(0,0,0,0.12);");
            archivo.write("}");

            archivo.write("h1 {");
            archivo.write("text-align: center;");
            archivo.write("margin-top: 0;");
            archivo.write("margin-bottom: 10px;");
            archivo.write("color: #2c3e50;");
            archivo.write("font-size: 28px;");
            archivo.write("}");

            archivo.write(".descripcion {");
            archivo.write("text-align: center;");
            archivo.write("color: #7f8c8d;");
            archivo.write("margin-bottom: 25px;");
            archivo.write("}");

            archivo.write("table {");
            archivo.write("width: 100%;");
            archivo.write("border-collapse: collapse;");
            archivo.write("overflow: hidden;");
            archivo.write("border-radius: 8px;");
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
            archivo.write("transition: 0.2s;");
            archivo.write("}");

            archivo.write("td:nth-child(2) {");
            archivo.write("font-weight: bold;");
            archivo.write("color: #34495e;");
            archivo.write("}");

            archivo.write(".pie {");
            archivo.write("margin-top: 20px;");
            archivo.write("text-align: right;");
            archivo.write("font-size: 13px;");
            archivo.write("color: #7f8c8d;");
            archivo.write("}");

            archivo.write("</style>");

            archivo.write("</head>");
            archivo.write("<body>");

            archivo.write("<h1>Reporte de Tokens</h1>");

            archivo.write("<table>");

            archivo.write("<tr>");
            archivo.write("<th>No.</th>");
            archivo.write("<th>Lexema</th>");
            archivo.write("<th>Tipo</th>");
            archivo.write("<th>Fila</th>");
            archivo.write("<th>Columna</th>");
            archivo.write("</tr>");

            for (int i = 0; i < tokens.length; i++) {

                archivo.write("<tr>");

                archivo.write("<td>");
                archivo.write(String.valueOf(tokens[i].getNumero()));
                archivo.write("</td>");

                archivo.write("<td>");
                archivo.write(tokens[i].getLexema());
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

            archivo.write("</body>");
            archivo.write("</html>");

            archivo.close();

            System.out.println("Reporte de tokens generado correctamente.");

        } catch (IOException e) {

            System.out.println(
                    "Error al generar el reporte de tokens: "
                    + e.getMessage()
            );
        }
    }
    
    public void generarReporteErrores(ErrorLexico[] errores) {

    try {

        FileWriter archivo = new FileWriter("reporte_errores.html");

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
        archivo.write("border-radius: 12px;");
        archivo.write("box-shadow: 0 5px 15px rgba(0,0,0,0.12);");
        archivo.write("}");

        archivo.write("h1 {");
        archivo.write("text-align: center;");
        archivo.write("margin-top: 0;");
        archivo.write("margin-bottom: 10px;");
        archivo.write("color: #922b21;");
        archivo.write("font-size: 28px;");
        archivo.write("}");

        archivo.write(".descripcion {");
        archivo.write("text-align: center;");
        archivo.write("color: #7f8c8d;");
        archivo.write("margin-bottom: 25px;");
        archivo.write("}");

        archivo.write("table {");
        archivo.write("width: 100%;");
        archivo.write("border-collapse: collapse;");
        archivo.write("overflow: hidden;");
        archivo.write("border-radius: 8px;");
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
        archivo.write("transition: 0.2s;");
        archivo.write("}");

        archivo.write("td:nth-child(2) {");
        archivo.write("font-weight: bold;");
        archivo.write("color: #922b21;");
        archivo.write("}");

        archivo.write(".pie {");
        archivo.write("margin-top: 20px;");
        archivo.write("text-align: right;");
        archivo.write("font-size: 13px;");
        archivo.write("color: #7f8c8d;");
        archivo.write("}");

        archivo.write(".sin-errores {");
        archivo.write("background-color: #eafaf1;");
        archivo.write("color: #27ae60;");
        archivo.write("border: 1px solid #a9dfbf;");
        archivo.write("padding: 15px;");
        archivo.write("border-radius: 8px;");
        archivo.write("text-align: center;");
        archivo.write("font-weight: bold;");
        archivo.write("}");

        archivo.write("</style>");

        archivo.write("</head>");
        archivo.write("<body>");

        archivo.write("<h1>Reporte de Errores Léxicos</h1>");

        if (errores.length == 0) {

            archivo.write("<p>No se encontraron errores léxicos.</p>");

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
                archivo.write(errores[i].getLexema());
                archivo.write("</td>");

                archivo.write("<td>");
                archivo.write(errores[i].getTipoError());
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
    
}
