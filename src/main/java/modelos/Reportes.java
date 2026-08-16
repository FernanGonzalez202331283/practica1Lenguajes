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
            archivo.write("table { border-collapse: collapse; width: 100%; }");
            archivo.write("th, td { border: 1px solid black; padding: 8px; }");
            archivo.write("th { background-color: #dddddd; }");
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
        archivo.write("table { border-collapse: collapse; width: 100%; }");
        archivo.write("th, td { border: 1px solid black; padding: 8px; }");
        archivo.write("th { background-color: #dddddd; }");
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
