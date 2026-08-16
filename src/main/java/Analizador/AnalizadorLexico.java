/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import modelos.ErrorLexico;
import modelos.TipoToken;
import modelos.Token;

/**
 *
 * @author fernan
 */
public class AnalizadorLexico {

    private String entrada;
    private int posicion;
    private int fila;
    private int columna;
    private int numeroToken;
    
    private Token[] tokens;
    private ErrorLexico[] errores;
    private boolean cadenaCerrada;
    private int cantidadTokens;
    private int cantidadErrores;

    public AnalizadorLexico(String entrada) {
        this.entrada = entrada;
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.numeroToken = 1;

        this.tokens = new Token[10];
        this.errores = new ErrorLexico[10];
        
        this.cantidadTokens = 0;
        this.cantidadErrores = 0;
    }

    private boolean hayCaracteres() {
        return posicion < entrada.length();
    }

    private char caracterActual() {
        return entrada.charAt(posicion);
    }

    public void analizar() {
        while (hayCaracteres()) {
            char actual = caracterActual();

            if (esEspacio(actual)) {
                avanzar();
                continue;
            }
             if (esDigito(actual)) {

                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = leerNumero();

                TipoToken tipo = clasificarNumero(lexema);

                Token token = new Token(
                        numeroToken,
                        lexema,
                        tipo,
                        filaInicial,
                        columnaInicial
                );

                agregarToken(token);

                numeroToken++;

                continue;
            }
             if (actual == '"') {
                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = leerCadena();

                if (cadenaCerrada) {

                    Token token = new Token(
                            numeroToken,
                            lexema,
                            TipoToken.LITERAL_CADENA,
                            filaInicial,
                            columnaInicial
                    );

                    agregarToken(token);

                    numeroToken++;

                } else {

                    ErrorLexico error = new ErrorLexico(
                            lexema,
                            "Cadena sin cerrar",
                            filaInicial,
                            columnaInicial
                    );

                    agregarError(error);
                }

                continue;
            }
            
             if (actual == '=') {

                int filaInicial = fila;
                int columnaInicial = columna;

                avanzar();

                Token token = new Token(
                        numeroToken,
                        "=",
                        TipoToken.OPERADOR_ASIGNACION,
                        filaInicial,
                        columnaInicial
                );

                agregarToken(token);

                numeroToken++;

                continue;
            }
            if (actual == '-') {

                int filaInicial = fila;
                int columnaInicial = columna;

                avanzar();

                if (hayCaracteres() && caracterActual() == '>') {

                    avanzar();

                    Token token = new Token(
                            numeroToken,
                            "->",
                            TipoToken.CONECTOR,
                            filaInicial,
                            columnaInicial
                    );

                    agregarToken(token);

                    numeroToken++;

                } else {

                    ErrorLexico error = new ErrorLexico(
                            "-",
                            "Operador o conector no reconocido",
                            filaInicial,
                            columnaInicial
                    );

                    agregarError(error);
                }

                continue;
            }
            if (actual == '+') {

                int filaInicial = fila;
                int columnaInicial = columna;

                avanzar();

                Token token = new Token(
                        numeroToken,
                        "+",
                        TipoToken.OPERADOR_CONCATENACION,
                        filaInicial,
                        columnaInicial
                );

                agregarToken(token);

                numeroToken++;

                continue;
            }
            
            if (esDelimitador(actual)) {

                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = leerDelimitador();

                Token token = new Token(
                        numeroToken,
                        lexema,
                        TipoToken.DELIMITADOR,
                        filaInicial,
                        columnaInicial
                );

                agregarToken(token);

                numeroToken++;

                continue;
            }
            
            if (esLetra(actual)) {
                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = leerIdentificador();

                TipoToken tipo = clasificarPalabra(lexema);

                Token token = new Token(
                        numeroToken,
                        lexema,
                        tipo,
                        filaInicial,
                        columnaInicial
                );
                agregarToken(token);

                numeroToken++;

                continue;
            }
            
            avanzar();
        }
    }

    private void agregarToken(Token token) {

        aumentarEspacioTokens();

        tokens[cantidadTokens] = token;
        cantidadTokens++;
    }

    private void agregarError(ErrorLexico error) {
        aumentarEspacioErrores();

        errores[cantidadErrores] = error;
        cantidadErrores++;
    }

    public void mostrarTokens() {
        for (int i = 0; i < cantidadTokens; i++) {
            System.out.println(tokens[i]);
        }
    }
    
    public void mostrarErrores() {

        for (int i = 0; i < cantidadErrores; i++) {
            System.out.println(errores[i]);
        }
    }

    private void aumentarEspacioTokens() {
        if (cantidadTokens == tokens.length) {

            Token[] nuevoArreglo = new Token[tokens.length * 2];

            for (int i = 0; i < tokens.length; i++) {
                nuevoArreglo[i] = tokens[i];
            }

            tokens = nuevoArreglo;
        }
    }

    private void aumentarEspacioErrores() {
        if (cantidadErrores == errores.length) {
            ErrorLexico[] nuevoArreglo
                    = new ErrorLexico[errores.length * 2];
            for (int i = 0; i < errores.length; i++) {
                nuevoArreglo[i] = errores[i];
            }

            errores = nuevoArreglo;
        }
    }

    private boolean esLetra(char caracter) {
        return (caracter >= 'A' && caracter <= 'Z')
                || (caracter >= 'a' && caracter <= 'z')
                || caracter == '_';
    }

    private boolean esDigito(char caracter) {
        return caracter >= '0' && caracter <= '9';
    }

    private boolean esEspacio(char caracter) {

        return caracter == ' '
                || caracter == '\t'
                || caracter == '\r';
    }

    private void avanzar() {
        if (caracterActual() == '\n') {

            posicion++;
            fila++;
            columna = 1;

        } else {

            posicion++;
            columna++;
        }
    }

    private String leerIdentificador() {
        String lexema = "";
        while (hayCaracteres()
                && (esLetra(caracterActual())
                || esDigito(caracterActual()))) {

            lexema += caracterActual();
            avanzar();
        }

        return lexema;
    }

    private TipoToken clasificarPalabra(String lexema) {

        if (lexema.equals("AGENTE")
                || lexema.equals("contexto")
                || lexema.equals("variable")
                || lexema.equals("EJECUTAR")
                || lexema.equals("EXPORTAR")) {

            return TipoToken.RESERVADA;
        }

        if (lexema.equals("PREGUNTAR")
                || lexema.equals("GENERAR")
                || lexema.equals("RESUMIR")
                || lexema.equals("ANALIZAR")
                || lexema.equals("TRADUCIR")
                || lexema.equals("CLASIFICAR")
                || lexema.equals("EXTRAER")) {

            return TipoToken.COMANDO_IA;
        }

        if (lexema.equals("SOBRE")
                || lexema.equals("DESDE")
                || lexema.equals("EN")
                || lexema.equals("COMO")) {

            return TipoToken.CONECTOR;
        }

        if (lexema.equals("CARGAR")) {

            return TipoToken.FUNCION;
        }

        return TipoToken.IDENTIFICADOR;
    }
    
     private String leerNumero() {
         
        String lexema = "";

        while (hayCaracteres() && esDigito(caracterActual())) {

            lexema += caracterActual();
            avanzar();
        }

        if (hayCaracteres() && caracterActual() == '.') {

            lexema += caracterActual();
            avanzar();

            while (hayCaracteres() && esDigito(caracterActual())) {

                lexema += caracterActual();
                avanzar();
            }
        }

        return lexema;
    }
     
    private TipoToken clasificarNumero(String lexema) {
        
        for (int i = 0; i < lexema.length(); i++) {

            if (lexema.charAt(i) == '.') {
                return TipoToken.LITERAL_DECIMAL;
            }
        }

        return TipoToken.LITERAL_ENTERO;
    }
    
    private String leerCadena() {

        String lexema = "";

        cadenaCerrada = false;

        lexema += caracterActual();
        avanzar();

        while (hayCaracteres()
                && caracterActual() != '"'
                && caracterActual() != '\n') {

            lexema += caracterActual();
            avanzar();
        }

        if (hayCaracteres() && caracterActual() == '"') {

            lexema += caracterActual();
            avanzar();

            cadenaCerrada = true;
        }

        return lexema;
    }
    
    private String leerDelimitador() {

        String lexema = "";

        lexema += caracterActual();
        avanzar();

        return lexema;
    }
     
    private boolean esDelimitador(char caracter) {

        return caracter == '{'
                || caracter == '}'
                || caracter == '('
                || caracter == ')'
                || caracter == ',';
    }
}
