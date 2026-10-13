
package Analizador;
import modelos.ErrorLexico;
import modelos.Token;
import modelos.TipoToken;

import java.io.FileReader;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fernan
 */
public class pruebasLexer {
    public static void main(String[] args) {

        try {

            FileReader archivo = new FileReader("prueba.pz");
            Lexer lexer = new Lexer(archivo);
            Token token;

            System.out.println("===== TOKENS =====");

            while (true) {

                token = lexer.yylex();

                if (token == null) {
                    break;
                }

                System.out.println(token);

                if (token.getTipo() == TipoToken.EOF) {
                    break;
                }
            }

            System.out.println("\n===== ERRORES LEXICOS =====");

            List<ErrorLexico> errores = lexer.getErrores();

            if (errores.isEmpty()) {

                System.out.println("No se encontraron errores lexicos.");

            } else {

                for (ErrorLexico error : errores) {
                    System.out.println(error);
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
