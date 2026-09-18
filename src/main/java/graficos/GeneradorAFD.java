/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author fernan
 */
public class GeneradorAFD {
    public static class ResultadoRender {

        public final boolean exito;
        public final String rutaImagen;
        public final String mensajeError;

        ResultadoRender(boolean exito, String rutaImagen, String mensajeError) {
            this.exito = exito;
            this.rutaImagen = rutaImagen;
            this.mensajeError = mensajeError;
        }
    }

    public static String generarDOT() {
        StringBuilder dot = new StringBuilder();
        // CONFIGURACIÓN DEL GRAFO
        dot.append("digraph AFD_PromptZal {\n");
        dot.append("  rankdir=LR;\n");
        dot.append("  fontname=\"Helvetica\";\n");
        dot.append("  node [fontname=\"Helvetica\", fontsize=11];\n");
        dot.append("  edge [fontname=\"Helvetica\", fontsize=10];\n\n");
        // ESTADO INICIAL
        dot.append("  __inicio [shape=point, style=invis];\n");
        dot.append("  __inicio -> q0;\n\n");
        // ESTADOS DE ACEPTACIÓN
        String[] aceptacion = {
            "q1",
            "q3",
            "q4",
            "q6",
            "q8",
            "q9",
            "q10",
            "q11",
            "q13"
        };

        for (String estado : aceptacion) {
            dot.append("  ")
               .append(estado)
               .append(" [shape=circle, peripheries=2, style=filled, ")
               .append("fillColor=\"#d5e8d4\", color=\"#82b366\"];\n");
        }
        // ESTADO INICIAL q0
        dot.append("  q0 [shape=circle, style=filled, ")
           .append("fillColor=\"#dae8fc\", color=\"#6c8ebf\"];\n");

        // ESTADOS INTERMEDIOS
        String[] intermedios = {
            "q2",
            "q5",
            "q7",
            "q12",
            "q14"
        };

        for (String estado : intermedios) {
            dot.append("  ")
               .append(estado)
               .append(" [shape=circle, style=filled, ")
               .append("fillColor=\"#ffe6cc\", color=\"#d79b00\"];\n");
        }
        // ESTADOS DE COMENTARIOS
        String[] comentarios = {
            "q15",
            "q16",
            "q17"
        };

        for (String estado : comentarios) {
            dot.append("  ")
               .append(estado)
               .append(" [shape=circle, style=filled, ")
               .append("fillColor=\"#fff2cc\", color=\"#d6b656\"];\n");
        }

        // ESTADO DE ERROR
        dot.append("  qE [shape=circle, style=filled, ")
           .append("fillColor=\"#f8cecc\", color=\"#b85450\"];\n\n");
        // DESDE q0

        // Identificadores
        dot.append("  q0 -> q1 [label=\"L, _\"];\n");

        // Directivas
        dot.append("  q0 -> q2 [label=\"@\"];\n");

        // Números
        dot.append("  q0 -> q4 [label=\"D\"];\n");

        // Cadenas
        dot.append("  q0 -> q7 [label=\"\\\"\"];\n");

        // Operador de asignación
        dot.append("  q0 -> q9 [label=\"=\"];\n");

        // Operador de concatenación
        dot.append("  q0 -> q10 [label=\"+\"];\n");

        // Delimitadores
        dot.append("  q0 -> q11 [label=\"{ } ( ) , ;\"];\n");

        // Posible conector ->
        dot.append("  q0 -> q12 [label=\"-\"];\n");

        // Posibles comentarios
        dot.append("  q0 -> q14 [label=\"/\"];\n");

        // Espacios
        dot.append("  q0 -> q0 [label=\"espacio, tab, \\\\r, \\\\n\"];\n");

        // Cualquier otro carácter
        dot.append("  q0 -> qE [label=\"otro\", ")
           .append("color=\"#b85450\", fontcolor=\"#b85450\"];\n\n");

        // IDENTIFICADORES
        // q1 = identificador
        dot.append("  q1 -> q1 [label=\"L, D, _\"];\n\n");
        
        // DIRECTIVAS
        // Después de @ debe venir una letra
        dot.append("  q2 -> q3 [label=\"L\"];\n");
        
        // Si viene número, _, espacio u otro carácter:
        dot.append("  q2 -> qE [label=\"D, _, otro\", ")
           .append("color=\"#b85450\", fontcolor=\"#b85450\"];\n");

        // Continúa leyendo el nombre de la directiva
        dot.append("  q3 -> q3 [label=\"L, D, _\"];\n\n");

        // NÚMEROS
        // q4 = parte entera
        dot.append("  q4 -> q4 [label=\"D\"];\n");

        // Punto decimal
        dot.append("  q4 -> q5 [label=\".\"];\n");

        // Después del punto DEBE venir un dígito
        dot.append("  q5 -> q6 [label=\"D\"];\n");

        // Si después del punto no viene dígito, error
        dot.append("  q5 -> qE [label=\"otro\", ")
           .append("color=\"#b85450\", fontcolor=\"#b85450\"];\n");

        // Continúan los dígitos decimales
        dot.append("  q6 -> q6 [label=\"D\"];\n\n");

        // CADENAS
        dot.append("  q7 -> q7 [label=\"cualquiera excepto \\\", \\\\n\"];\n");

        // Cierre de cadena
        dot.append("  q7 -> q8 [label=\"\\\"\"];\n");

        // significa cadena sin cerrar
        dot.append("  q7 -> qE [label=\"\\\\n\", ")
           .append("color=\"#b85450\", fontcolor=\"#b85450\"];\n\n");
        
        // CONECTOR ->
        dot.append("  q12 -> q13 [label=\">\"];\n");

        // Cualquier otro carácter produce error
        dot.append("  q12 -> qE [label=\"otro\", ")
           .append("color=\"#b85450\", fontcolor=\"#b85450\"];\n\n");
       
        // COMENTARIOS
        // para comentario de línea
        dot.append("  q14 -> q15 [label=\"/\"];\n");

        // para comentario de bloque
        dot.append("  q14 -> q16 [label=\"*\"];\n");

        // Cualquier otro carácter después de /
        dot.append("  q14 -> qE [label=\"otro\", ")
           .append("color=\"#b85450\", fontcolor=\"#b85450\"];\n");

        // COMENTARIO DE LÍNEA
        // Mientras no llegue salto de línea
        dot.append("  q15 -> q15 [label=\"cualquiera excepto \\\\n\"];\n");

        // termina el comentario y regresa a q0
        dot.append("  q15 -> q0 [label=\"\\\\n (sin emitir token)\", ")
           .append("style=dashed];\n");

        // COMENTARIO DE BLOQUE
        dot.append("  q16 -> q16 [label=\"cualquiera excepto *\"];\n");
        dot.append("  q16 -> q17 [label=\"*\"];\n");

        dot.append("  q17 -> q16 [label=\"cualquiera excepto /, *\"];\n");

        // Si vuelve a aparecer *, permanecemos en q17
        dot.append("  q17 -> q17 [label=\"*\"];\n");

        // Si aparece /, termina el comentario
        dot.append("  q17 -> q0 [label=\"/ (sin emitir token)\", ")
           .append("style=dashed];\n\n");

        // ESTADO DE ERROR

        dot.append("  qE -> q0 [label=\"cualquiera ")
           .append("(reporta error y reinicia)\", ")
           .append("color=\"#b85450\", fontcolor=\"#b85450\"];\n");

        dot.append("}\n");

        return dot.toString();
    }

    public static void guardarDOT(String rutaDot) throws IOException {

        Path path = Path.of(rutaDot);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        Files.writeString(
            path,
            generarDOT(),
            StandardCharsets.UTF_8
        );
    }

    public static ResultadoRender generarImagen(
            String rutaDot,
            String rutaImagen) {

        try {

            guardarDOT(rutaDot);
            String formato = rutaImagen.substring(
                rutaImagen.lastIndexOf('.') + 1
            );
            ProcessBuilder pb = new ProcessBuilder(
                "dot",
                "-T" + formato,
                rutaDot,
                "-o",
                rutaImagen
            );

            pb.redirectErrorStream(true);

            Process proceso = pb.start();

            // Leemos la salida de Graphviz
            String salida = new String(
                proceso.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
            );

            // Esperamos a que termine
            int codigo = proceso.waitFor();

            // Si Graphviz terminó con error
            if (codigo != 0) {

                return new ResultadoRender(
                    false,
                    null,
                    "Graphviz terminó con código "
                    + codigo
                    + ":\n"
                    + salida
                );
            }

            // Verificamos que la imagen realmente exista
            if (!new File(rutaImagen).exists()) {

                return new ResultadoRender(
                    false,
                    null,
                    "Graphviz no reportó error "
                    + "pero no se generó la imagen."
                );
            }

            // Todo salió correctamente
            return new ResultadoRender(
                true,
                rutaImagen,
                null
            );

        } catch (IOException e) {

            return new ResultadoRender(
                false,
                null,
                "No se pudo ejecutar Graphviz. "
                + "¿Está instalado y en el PATH? "
                + "Verifica con 'dot -V'.\n"
                + "Detalle: "
                + e.getMessage()
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return new ResultadoRender(
                false,
                null,
                "El proceso de Graphviz fue interrumpido."
            );
        }
    }
}