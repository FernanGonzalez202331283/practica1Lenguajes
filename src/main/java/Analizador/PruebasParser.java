/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import java.io.FileReader;
import java.util.List;
import modelos.ErrorLexico;
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

            // Ejecutamos el lexer completo
            Token token;

            do {

                token = lexer.yylex();

            } while (token != null
                    && token.getTipo() != modelos.TipoToken.EOF);

            // Obtenemos todos los tokens
            List<Token> tokens = lexer.getTokens();

            System.out.println("===== TOKENS =====");

            for (Token t : tokens) {
                System.out.println(t);
            }

            // Errores lexicos
            List<ErrorLexico> errores = lexer.getErrores();

            System.out.println("\n===== ERRORES LEXICOS =====");

            if (errores.isEmpty()) {

                System.out.println("No se encontraron errores lexicos.");

            } else {

                for (ErrorLexico error : errores) {
                    System.out.println(error);
                }
            }

            // Parser
            System.out.println("\n===== ANALISIS SINTACTICO =====");

            Parser parser = new Parser(tokens);

            parser.analizar();
            System.out.println("\n===== ERRORES SINTACTICOS =====");

            List<ErrorSintactico> erroresSintacticos = parser.getErrores();

            if (erroresSintacticos.isEmpty()) {

                System.out.println("No se encontraron errores sintacticos.");

            } else {

                for (ErrorSintactico error : erroresSintacticos) {
                    System.out.println(error);
                }
            }

            System.out.println("\nAnalisis sintactico terminado.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
