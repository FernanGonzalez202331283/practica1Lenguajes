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
    
    /** Resultado de intentar generar la imagen. */
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

    /** Construye el código DOT completo del AFD de PromptZal (q0..q17 y qE). */
    public static String generarDOT() {
        StringBuilder dot = new StringBuilder();
        dot.append("digraph AFD_PromptZal {\n");
        dot.append("  rankdir=LR;\n");
        dot.append("  fontname=\"Helvetica\";\n");
        dot.append("  node [fontname=\"Helvetica\", fontsize=11];\n");
        dot.append("  edge [fontname=\"Helvetica\", fontsize=10];\n\n");

        dot.append("  __inicio [shape=point, style=invis];\n");
        dot.append("  __inicio -> q0;\n\n");

        String[] aceptacion = {"q1", "q3", "q4", "q6", "q8", "q9", "q10", "q11", "q13"};
        for (String s : aceptacion) {
            dot.append("  ").append(s)
               .append(" [shape=circle, peripheries=2, style=filled, fillColor=\"#d5e8d4\", color=\"#82b366\"];\n");
        }
        dot.append("  q0 [shape=circle, style=filled, fillColor=\"#dae8fc\", color=\"#6c8ebf\"];\n");
        String[] intermedios = {"q2", "q5", "q7", "q12", "q14"};
        for (String s : intermedios) {
            dot.append("  ").append(s)
               .append(" [shape=circle, style=filled, fillColor=\"#ffe6cc\", color=\"#d79b00\"];\n");
        }
        String[] comentarios = {"q15", "q16", "q17"};
        for (String s : comentarios) {
            dot.append("  ").append(s)
               .append(" [shape=circle, style=filled, fillColor=\"#fff2cc\", color=\"#d6b656\"];\n");
        }
        dot.append("  qE [shape=circle, style=filled, fillColor=\"#f8cecc\", color=\"#b85450\"];\n\n");

        dot.append("  q0 -> q1  [label=\"L, _\"];\n");
        dot.append("  q0 -> q2  [label=\"@\"];\n");
        dot.append("  q0 -> q4  [label=\"D\"];\n");
        dot.append("  q0 -> q7  [label=\"\\\"\"];\n");
        dot.append("  q0 -> q9  [label=\"=\"];\n");
        dot.append("  q0 -> q10 [label=\"+\"];\n");
        dot.append("  q0 -> q11 [label=\"{ } ( ) ,\"];\n");
        dot.append("  q0 -> q12 [label=\"-\"];\n");
        dot.append("  q0 -> q14 [label=\"/\"];\n");
        dot.append("  q0 -> q0  [label=\"esp, \\\\n\"];\n");
        dot.append("  q0 -> qE  [label=\"otro\", color=\"#b85450\", fontcolor=\"#b85450\"];\n\n");

        dot.append("  q1 -> q1 [label=\"L, D, _\"];\n");
        dot.append("  q2 -> q3 [label=\"L\"];\n");
        dot.append("  q2 -> qE [label=\"D, _, otro\", color=\"#b85450\", fontcolor=\"#b85450\"];\n");
        dot.append("  q3 -> q3 [label=\"L, D, _\"];\n\n");

        dot.append("  q4 -> q4 [label=\"D\"];\n");
        dot.append("  q4 -> q5 [label=\".\"];\n");
        dot.append("  q5 -> q6 [label=\"D\"];\n");
        dot.append("  q5 -> qE [label=\"otro\", color=\"#b85450\", fontcolor=\"#b85450\"];\n");
        dot.append("  q6 -> q6 [label=\"D\"];\n\n");

        dot.append("  q7 -> q7 [label=\"cualquiera != \\\", != \\\\n (incluye esp)\"];\n");
        dot.append("  q7 -> q8 [label=\"\\\"\"];\n");
        dot.append("  q7 -> qE [label=\"\\\\n\", color=\"#b85450\", fontcolor=\"#b85450\"];\n\n");

        dot.append("  q12 -> q13 [label=\">\"];\n");
        dot.append("  q12 -> qE  [label=\"otro\", color=\"#b85450\", fontcolor=\"#b85450\"];\n\n");

        dot.append("  q14 -> q15 [label=\"/\"];\n");
        dot.append("  q14 -> q16 [label=\"*\"];\n");
        dot.append("  q14 -> qE  [label=\"otro\", color=\"#b85450\", fontcolor=\"#b85450\"];\n");
        dot.append("  q15 -> q15 [label=\"!= \\\\n\"];\n");
        dot.append("  q15 -> q0  [label=\"\\\\n (sin emitir token)\", style=dashed];\n");
        dot.append("  q16 -> q16 [label=\"!= *\"];\n");
        dot.append("  q16 -> q17 [label=\"*\"];\n");
        dot.append("  q17 -> q16 [label=\"!= /, != *\"];\n");
        dot.append("  q17 -> q17 [label=\"*\"];\n");
        dot.append("  q17 -> q0  [label=\"/ (sin emitir token)\", style=dashed];\n\n");

        dot.append("  qE -> q0 [label=\"cualquiera (reporta error y reinicia)\", color=\"#b85450\", fontcolor=\"#b85450\"];\n");
        dot.append("}\n");
        return dot.toString();
    }

    /** Escribe el código DOT en la ruta indicada (crea carpetas si hacen falta). */
    public static void guardarDOT(String rutaDot) throws IOException {
        Path path = Path.of(rutaDot);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        Files.writeString(path, generarDOT(), StandardCharsets.UTF_8);
    }

    /**
     * Genera el .dot y lo renderiza a imagen invocando el ejecutable "dot" de Graphviz.
     * @param rutaDot    ej. "salida/afd.dot"
     * @param rutaImagen ej. "salida/afd.png"
     */
    public static ResultadoRender generarImagen(String rutaDot, String rutaImagen) {
        try {
            guardarDOT(rutaDot);

            String formato = rutaImagen.substring(rutaImagen.lastIndexOf('.') + 1);
            ProcessBuilder pb = new ProcessBuilder("dot", "-T" + formato, rutaDot, "-o", rutaImagen);
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            String salida = new String(proceso.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            int codigo = proceso.waitFor();

            if (codigo != 0) {
                return new ResultadoRender(false, null, "Graphviz terminó con código " + codigo + ":\n" + salida);
            }
            if (!new File(rutaImagen).exists()) {
                return new ResultadoRender(false, null, "Graphviz no reportó error pero no se generó la imagen.");
            }
            return new ResultadoRender(true, rutaImagen, null);

        } catch (IOException e) {
            return new ResultadoRender(false, null,
                    "No se pudo ejecutar Graphviz. ¿Está instalado y en el PATH? Verifica con 'dot -V'.\nDetalle: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ResultadoRender(false, null, "El proceso de Graphviz fue interrumpido.");
        }
    }
}
