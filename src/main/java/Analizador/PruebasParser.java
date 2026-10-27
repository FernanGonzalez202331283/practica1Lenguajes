/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 */
package Analizador;

import java.io.FileReader;
import java.util.List;
import modelos.ErrorLexico;
import modelos.ErrorSemantico;
import modelos.ErrorSintactico;
import modelos.Token;

/**
 *
 * @author fernan
 */
public class PruebasParser {

    public static void main(String[] args) {

        try {
            FileReader archivo = new FileReader("prueba.pz");

            Lexer lexer = new Lexer(archivo);

            Token token;

            do {
                token = lexer.yylex();

            } while (token != null
                    && token.getTipo() != modelos.TipoToken.EOF);

            List<Token> tokens = lexer.getTokens();

            System.out.println("===== TOKENS =====");

            for (Token t : tokens) {
                System.out.println(t);
            }

            List<ErrorLexico> erroresLexicos = lexer.getErrores();

            System.out.println("\n===== ERRORES LEXICOS =====");

            if (erroresLexicos.isEmpty()) {

                System.out.println("No se encontraron errores lexicos.");

            } else {

                for (ErrorLexico error : erroresLexicos) {
                    System.out.println(error);
                }
            }
            System.out.println("\n===== ANALISIS SINTACTICO =====");

            Parser parser = new Parser(tokens);

            parser.analizar();

            List<ErrorSintactico> erroresSintacticos =
                    parser.getErrores();

            if (erroresSintacticos.isEmpty()) {

                System.out.println("No se encontraron errores sintacticos.");

            } else {

                for (ErrorSintactico error : erroresSintacticos) {
                    System.out.println(error);
                }
            }

            System.out.println("\n===== TABLA DE SIMBOLOS =====");

            for (modelos.Simbolo simbolo
                    : parser.getTablaSimbolos().getSimbolos()) {

                System.out.println(
                        "Nombre: " + simbolo.getNombre()
                        + " | Tipo: " + simbolo.getTipo()
                        + " | Ambito: " + simbolo.getAmbito()
                        + " | Fila: " + simbolo.getFila()
                );
            }

            System.out.println("\n===== ARBOL DE SINTAXIS ABSTRACTA =====");

            ImprimirAST.imprimir(parser.getPrograma());

            System.out.println("\n===== ANALISIS SEMANTICO =====");

            boolean sintaxisCorrecta = erroresSintacticos.isEmpty();
            boolean lexicoCorrecto = erroresLexicos.isEmpty();

            boolean semanticaCorrecta = false;

            if (sintaxisCorrecta && lexicoCorrecto) {

                AnalizadorSemantico semantico =
                        new AnalizadorSemantico(
                                parser.getPrograma(),
                                parser.getTablaSimbolos()
                        );

                semantico.analizar();

                if (semantico.getErrores().isEmpty()) {

                    System.out.println(
                            "No se encontraron errores semanticos."
                    );

                    semanticaCorrecta = true;

                } else {

                   for (ErrorSemantico error : semantico.getErrores()) {
                        System.out.println(error);
                    }

                }

            } else {

                System.out.println(
                        "No se realiza el analisis semantico porque "
                        + "existen errores lexicos o sintacticos."
                );

            }

            System.out.println("\n===== GENERACION DEL PROMPT =====");

            if (lexicoCorrecto
                    && sintaxisCorrecta
                    && semanticaCorrecta) {

             String promptGenerado =
                GeneradorPromptZal.generar(parser.getPrograma());

                System.out.println("\n===== PROMPT GENERADO =====");

                System.out.println(promptGenerado);

            } else {

                System.out.println(
                        "No se puede generar el prompt porque "
                        + "el programa contiene errores."
                );

            }

            System.out.println("\n===== FIN DE LAS PRUEBAS =====");

            archivo.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}