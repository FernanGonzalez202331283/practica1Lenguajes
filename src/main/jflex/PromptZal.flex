package Analizador;

import modelos.Token;
import modelos.TipoToken;
import modelos.ErrorLexico;

import java.util.ArrayList;
import java.util.List;

%%

%public
%class Lexer
%unicode
%line
%column
%type Token
%eofval{
    return crearTokenEOF();
%eofval}

%{

    private int numeroToken = 1;

    private List<Token> tokens = new ArrayList<>();
    private List<ErrorLexico> errores = new ArrayList<>();

    public List<Token> getTokens() {
        return tokens;
    }

    public List<ErrorLexico> getErrores() {
        return errores;
    }

    private Token crearToken(TipoToken tipo) {

        Token token = new Token(
            numeroToken++,
            yytext(),
            tipo,
            yyline + 1,
            yycolumn + 1
        );

        tokens.add(token);
        return token;
    }

    private Token crearTokenEOF() {

        Token token = new Token(
            numeroToken++,
            "EOF",
            TipoToken.EOF,
            yyline + 1,
            yycolumn + 1
        );

        tokens.add(token);
        return token;
    }

%}

// DEFINICIONES

DIGITO = [0-9]

LETRA = [a-zA-Z_]

ID = {LETRA}({LETRA}|{DIGITO})*

NUMERO = {DIGITO}+(\.{DIGITO}+)?

CADENA = \"([^\"\\]|\\.)*\"

ESPACIOS = [ \t\r\n]+

COMENTARIO_LINEA = "//"[^\r\n]*

COMENTARIO_BLOQUE = "/*"([^*]|\*+[^*/])*\*+"/"


// REGLAS
%%


// ESPACIOS
{ESPACIOS}
{
    /* Ignorar espacios */
}


// COMENTARIOS
{COMENTARIO_LINEA}
{
    /* Ignorar comentario de una línea */
}

{COMENTARIO_BLOQUE}
{
    /* Ignorar comentario de bloque */
}


// DIRECTIVAS

"@"{ID}
{
    return crearToken(TipoToken.DIRECTIVA);
}


// PALABRAS RESERVADAS
"AGENTE"
{
    return crearToken(TipoToken.AGENTE);
}

"contexto"
{
    return crearToken(TipoToken.CONTEXTO);
}

"variable"
{
    return crearToken(TipoToken.VARIABLE);
}

"EJECUTAR"
{
    return crearToken(TipoToken.EJECUTAR);
}

"EXPORTAR"
{
    return crearToken(TipoToken.EXPORTAR);
}


// COMANDOS DE IA
"PREGUNTAR"|"GENERAR"|"RESUMIR"|"ANALIZAR"|"TRADUCIR"|"CLASIFICAR"|"EXTRAER"
{
    return crearToken(TipoToken.COMANDO_IA);
}


// CONECTORES
"SOBRE"|"DESDE"|"EN"|"COMO"
{
    return crearToken(TipoToken.CONECTOR);
}


// FUNCIONES DEL SISTEMA
"CARGAR"
{
    return crearToken(TipoToken.CARGAR);
}

// CADENAS
{CADENA}
{
    return crearToken(TipoToken.CADENA);
}


// NUMEROS
{NUMERO}
{
    return crearToken(TipoToken.NUMERO);
}

// IDENTIFICADORES
{ID}
{
    return crearToken(TipoToken.ID);
}

// OPERADORES
"="
{
    return crearToken(TipoToken.IGUAL);
}

"->"
{
    return crearToken(TipoToken.FLECHA);
}

"+"
{
    return crearToken(TipoToken.MAS);
}

// DELIMITADORES
"{"
{
    return crearToken(TipoToken.LLAVE_A);
}

"}"
{
    return crearToken(TipoToken.LLAVE_C);
}

"("
{
    return crearToken(TipoToken.PAR_A);
}

")"
{
    return crearToken(TipoToken.PAR_C);
}

","
{
    return crearToken(TipoToken.COMA);
}

// ERROR LEXICO
.
{
    errores.add(
        new ErrorLexico(
            yytext(),
            "Caracter no reconocido",
            yyline + 1,
            yycolumn + 1
        )
    );
}

