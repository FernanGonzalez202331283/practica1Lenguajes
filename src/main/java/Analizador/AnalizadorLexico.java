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

    private int cantidadTokens;
    private int cantidadErrores;

    private boolean cadenaCerrada;

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

        this.cadenaCerrada = false;
    }

    public void analizar() {

        while (hayCaracteres()) {

            if (esEspacio(caracterActual())) {
                avanzar();
                continue;
            }

            // Comentario de línea
            if (esComentarioLinea()) {
                ignorarComentarioLinea();
                continue;
            }

            // Comentario de bloque
            if (esComentarioBloque()) {
                ignorarComentarioBloque();
                continue;
            }

            // Directivas
            if (caracterActual() == '@') {

                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = leerDirectiva();

                if (esDirectiva(lexema)) {

                    agregarToken(
                            lexema,
                            TipoToken.DIRECTIVA,
                            filaInicial,
                            columnaInicial
                    );

                } else {

                    agregarError(
                            lexema,
                            "Directiva no reconocida",
                            filaInicial,
                            columnaInicial
                    );
                }

                continue;
            }

            // Números
            if (esDigito(caracterActual())) {

                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = leerNumero();

                if (lexema.endsWith(".")) {

                    agregarError(
                            lexema,
                            "Número decimal incompleto",
                            filaInicial,
                            columnaInicial
                    );

                } else {

                    TipoToken tipo = clasificarNumero(lexema);

                    agregarToken(
                            lexema,
                            tipo,
                            filaInicial,
                            columnaInicial
                    );
                }

                continue;
            }

            // Cadenas
            if (caracterActual() == '"') {

                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = leerCadena();

                if (cadenaCerrada) {

                    agregarToken(
                            lexema,
                            TipoToken.LITERAL_CADENA,
                            filaInicial,
                            columnaInicial
                    );

                } else {

                    agregarError(
                            lexema,
                            "Cadena sin cerrar",
                            filaInicial,
                            columnaInicial
                    );
                }

                continue;
            }

            // Operador de asignación
            if (caracterActual() == '=') {

                int filaInicial = fila;
                int columnaInicial = columna;

                avanzar();

                agregarToken(
                        "=",
                        TipoToken.OPERADOR_ASIGNACION,
                        filaInicial,
                        columnaInicial
                );

                continue;
            }

            // Conector ->
            if (caracterActual() == '-') {

                int filaInicial = fila;
                int columnaInicial = columna;

                if (posicion + 1 < entrada.length()
                        && entrada.charAt(posicion + 1) == '>') {

                    avanzar();
                    avanzar();

                    agregarToken(
                            "->",
                            TipoToken.CONECTOR,
                            filaInicial,
                            columnaInicial
                    );

                } else {

                    agregarError(
                            String.valueOf(caracterActual()),
                            "Carácter no reconocido",
                            filaInicial,
                            columnaInicial
                    );

                    avanzar();
                }

                continue;
            }

            // Operador de concatenación
            if (caracterActual() == '+') {

                int filaInicial = fila;
                int columnaInicial = columna;

                avanzar();

                agregarToken(
                        "+",
                        TipoToken.OPERADOR_CONCATENACION,
                        filaInicial,
                        columnaInicial
                );

                continue;
            }

            // Delimitadores
            if (esDelimitador(caracterActual())) {

                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = String.valueOf(caracterActual());

                avanzar();

                agregarToken(
                        lexema,
                        TipoToken.DELIMITADOR,
                        filaInicial,
                        columnaInicial
                );

                continue;
            }

            if (esLetra(caracterActual())) {

                int filaInicial = fila;
                int columnaInicial = columna;

                String lexema = leerIdentificador();

                TipoToken tipo = clasificarPalabra(lexema);

                agregarToken(
                        lexema,
                        tipo,
                        filaInicial,
                        columnaInicial
                );

                continue;
            }

            int filaInicial = fila;
            int columnaInicial = columna;

            String lexema = String.valueOf(caracterActual());

            agregarError(
                    lexema,
                    "Carácter no reconocido",
                    filaInicial,
                    columnaInicial
            );

            avanzar();
        }
    }

    private boolean hayCaracteres() {
        return posicion < entrada.length();
    }

    private char caracterActual() {
        return entrada.charAt(posicion);
    }

    private boolean esDigito(char caracter) {
        return caracter >= '0' && caracter <= '9';
    }

    private boolean esLetra(char caracter) {
        return (caracter >= 'A' && caracter <= 'Z')
                || (caracter >= 'a' && caracter <= 'z')
                || caracter == '_';
    }

    private boolean esLetraDirectiva(char caracter) {
        return (caracter >= 'A' && caracter <= 'Z')
                || (caracter >= 'a' && caracter <= 'z');
    }

    private boolean esEspacio(char caracter) {
        return caracter == ' '
                || caracter == '\t'
                || caracter == '\n'
                || caracter == '\r';
    }

    private boolean esDelimitador(char caracter) {
        return caracter == '{'
                || caracter == '}'
                || caracter == '('
                || caracter == ')'
                || caracter == ','
                || caracter == ';';
    }

    private void avanzar() {

        if (!hayCaracteres()) {
            return;
        }

        if (caracterActual() == '\n') {
            fila++;
            columna = 1;
        } else {
            columna++;
        }

        posicion++;
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

    private String leerDirectiva() {

        String lexema = "";
        lexema += caracterActual();
        avanzar();

        if (hayCaracteres() && esLetraDirectiva(caracterActual())) {

            lexema += caracterActual();
            avanzar();

            while (hayCaracteres()
                    && (esLetraDirectiva(caracterActual())
                    || esDigito(caracterActual())
                    || caracterActual() == '_')) {

                lexema += caracterActual();
                avanzar();
            }
        }

        return lexema;
    }

    private boolean esDirectiva(String lexema) {

        return lexema.equals("@modelo")
                || lexema.equals("@rol")
                || lexema.equals("@formato");
    }

    private String leerNumero() {

        String lexema = "";
        while (hayCaracteres()
                && esDigito(caracterActual())) {

            lexema += caracterActual();
            avanzar();
        }

        if (hayCaracteres()
                && caracterActual() == '.') {

            lexema += caracterActual();
            avanzar();

            while (hayCaracteres()
                    && esDigito(caracterActual())) {

                lexema += caracterActual();
                avanzar();
            }
        }

        return lexema;
    }

    private TipoToken clasificarNumero(String lexema) {

        if (lexema.indexOf('.') >= 0) {
            return TipoToken.LITERAL_DECIMAL;
        }

        return TipoToken.LITERAL_ENTERO;
    }

    private String leerCadena() {

        String lexema = "";

        cadenaCerrada = false;
        lexema += caracterActual();
        avanzar();

        while (hayCaracteres()) {

            if (caracterActual() == '"') {

                lexema += caracterActual();
                avanzar();

                cadenaCerrada = true;

                return lexema;
            }

            if (caracterActual() == '\n') {
                return lexema;
            }

            lexema += caracterActual();
            avanzar();
        }

        return lexema;
    }

    private TipoToken clasificarPalabra(String lexema) {

        // Reservadas
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
                || lexema.equals("CODIFICAR")
                || lexema.equals("EXTRAER")) {

            return TipoToken.COMANDO_IA;
        }

        if (lexema.equals("CARGAR")) {
            return TipoToken.FUNCION;
        }

        if (lexema.equals("SOBRE")
                || lexema.equals("DESDE")
                || lexema.equals("EN")
                || lexema.equals("COMO")) {

            return TipoToken.CONECTOR;
        }
        return TipoToken.IDENTIFICADOR;
    }

    private boolean esComentarioLinea() {

        return posicion + 1 < entrada.length()
                && entrada.charAt(posicion) == '/'
                && entrada.charAt(posicion + 1) == '/';
    }

    private void ignorarComentarioLinea() {

        avanzar();
        avanzar();

        while (hayCaracteres()
                && caracterActual() != '\n') {

            avanzar();
        }
    }

    private boolean esComentarioBloque() {

        return posicion + 1 < entrada.length()
                && entrada.charAt(posicion) == '/'
                && entrada.charAt(posicion + 1) == '*';
    }

    private void ignorarComentarioBloque() {

        int filaInicial = fila;
        int columnaInicial = columna;

        avanzar();
        avanzar();

        while (hayCaracteres()) {

            if (caracterActual() == '*'
                    && posicion + 1 < entrada.length()
                    && entrada.charAt(posicion + 1) == '/') {

                avanzar();
                avanzar();

                return;
            }

            avanzar();
        }
        agregarError(
                "/*",
                "Comentario de bloque sin cerrar",
                filaInicial,
                columnaInicial
        );
    }

    private void agregarToken(
            String lexema,
            TipoToken tipo,
            int fila,
            int columna) {

        aumentarTokensSiEsNecesario();

        tokens[cantidadTokens] = new Token(
                numeroToken,
                lexema,
                tipo,
                fila,
                columna
        );

        cantidadTokens++;
        numeroToken++;
    }

    private void agregarError(
            String lexema,
            String tipoError,
            int fila,
            int columna) {

        aumentarErroresSiEsNecesario();

        errores[cantidadErrores] = new ErrorLexico(
                lexema,
                tipoError,
                fila,
                columna
        );

        cantidadErrores++;
    }

    private void aumentarTokensSiEsNecesario() {

        if (cantidadTokens >= tokens.length) {

            Token[] nuevosTokens
                    = new Token[tokens.length * 2];

            for (int i = 0; i < tokens.length; i++) {
                nuevosTokens[i] = tokens[i];
            }

            tokens = nuevosTokens;
        }
    }

    private void aumentarErroresSiEsNecesario() {

        if (cantidadErrores >= errores.length) {

            ErrorLexico[] nuevosErrores
                    = new ErrorLexico[errores.length * 2];

            for (int i = 0; i < errores.length; i++) {
                nuevosErrores[i] = errores[i];
            }

            errores = nuevosErrores;
        }
    }

    public Token[] getTokens() {

        Token[] resultado = new Token[cantidadTokens];

        for (int i = 0; i < cantidadTokens; i++) {
            resultado[i] = tokens[i];
        }

        return resultado;
    }

    public ErrorLexico[] getErrores() {

        ErrorLexico[] resultado
                = new ErrorLexico[cantidadErrores];

        for (int i = 0; i < cantidadErrores; i++) {
            resultado[i] = errores[i];
        }

        return resultado;
    }

    public int getCantidadTokens() {
        return cantidadTokens;
    }

    public int getCantidadErrores() {
        return cantidadErrores;
    }

    public int getFilaActual() {
        return fila;
    }
}
