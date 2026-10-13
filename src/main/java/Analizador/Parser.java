/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import java.util.ArrayList;
import java.util.List;
import modelos.Agente;
import modelos.Comando;
import modelos.Conector;
import modelos.ErrorSintactico;
import modelos.Expresion;
import modelos.Programa;
import modelos.TipoToken;
import modelos.Token;
import modelos.Variable;

/**
 *
 * @author fernan
 */
public class Parser {

   
    private List<Token> tokens;
    private int posicion;
    private Token tokenActual;

    private List<ErrorSintactico> errores = new ArrayList<>();
    private Programa programa;
    public Parser(List<Token> tokens) {

        this.tokens = tokens;
        this.posicion = 0;
        programa = new Programa();
        if (tokens != null && !tokens.isEmpty()) {
            this.tokenActual = tokens.get(0);
        }
    }

    public List<ErrorSintactico> getErrores() {
        return errores;
    }
    
    public Programa getPrograma() {
        return programa;
    }

    private void avanzar() {

        if (posicion < tokens.size() - 1) {
            posicion++;
            tokenActual = tokens.get(posicion);
        }
    }

    private boolean consumir(TipoToken tipoEsperado) {
        if (tokenActual.getTipo() == tipoEsperado) {
            avanzar();
            return true;

        } else {

            registrarError(tipoEsperado.toString());
            return false;
        }
    }

    private void registrarError(String esperado) {

        ErrorSintactico error = new ErrorSintactico(
                tokenActual.getLexema(),
                esperado,
                tokenActual.getFila(),
                tokenActual.getColumna()
        );

        errores.add(error);
    }

    private boolean esSincronizacion(TipoToken tipo) {

        return tipo == TipoToken.AGENTE
                || tipo == TipoToken.LLAVE_C
                || tipo == TipoToken.COMANDO_IA
                || tipo == TipoToken.VARIABLE
                || tipo == TipoToken.EJECUTAR
                || tipo == TipoToken.EXPORTAR;
    }
    
    

    private void recuperar() {

        while (tokenActual.getTipo() != TipoToken.EOF
                && !esSincronizacion(tokenActual.getTipo())) {

            avanzar();
        }
    }

    public void analizar() {

        programa();
    }

    private void programa() {

        directivas();
        agentes();
        ejecuciones();
        exportar();
        consumir(TipoToken.EOF);
    }

    private void directivas() {

        if (tokenActual.getTipo() == TipoToken.DIRECTIVA) {

            consumir(TipoToken.DIRECTIVA);
            consumir(TipoToken.CADENA);

            directivas();
        }
    }

    private void agentes() {

        agente();
        masAgentes();
    }

    private void masAgentes() {

        if (tokenActual.getTipo() == TipoToken.AGENTE) {

            agente();
            masAgentes();
        }
    }

    private void agente() {

        consumir(TipoToken.AGENTE);

        String nombre = tokenActual.getLexema();
        consumir(TipoToken.ID);

        Agente agente = new Agente(nombre);

        consumir(TipoToken.LLAVE_A);

        contexto(agente);
        variables(agente);
        comandos(agente);

        consumir(TipoToken.LLAVE_C);

        programa.agregarAgente(agente);
    }

    private void contexto(Agente agente) {

        consumir(TipoToken.CONTEXTO);

        if (!consumir(TipoToken.IGUAL)) {

            recuperarContexto();
            return;
        }

        consumir(TipoToken.CADENA);
    }

    private void recuperarContexto() {

        while (tokenActual.getTipo() != TipoToken.EOF
                && tokenActual.getTipo() != TipoToken.VARIABLE
                && tokenActual.getTipo() != TipoToken.COMANDO_IA
                && tokenActual.getTipo() != TipoToken.LLAVE_C) {

            avanzar();
        }
    }
    
    private void variables(Agente agente) {

        if (tokenActual.getTipo() == TipoToken.VARIABLE) {

            consumir(TipoToken.VARIABLE);

            String nombre = tokenActual.getLexema();
            consumir(TipoToken.ID);

            consumir(TipoToken.IGUAL);

            Expresion expresion = expresion();

            Variable variable = new Variable(nombre, expresion);

            agente.agregarVariables(variable);

            variables(agente);
        }
    }

    private void comandos(Agente agente) {
        
        comando(agente);
        masComandos(agente);
    }

    private void masComandos(Agente agente) {

        if (tokenActual.getTipo() == TipoToken.COMANDO_IA) {

            comando(agente);
            masComandos(agente);
        }
    }

    private void comando(Agente agente) {
        String tipo = tokenActual.getLexema();
        consumir(TipoToken.COMANDO_IA);
        
        Comando  comando = new Comando(tipo);
        
        Expresion expresion = expresion();
        comando.setExpresion(expresion);
        conectores(comando);
       
        if (!consumir(TipoToken.FLECHA)) {

            recuperarComando();
            return;
        }
        String destino = tokenActual.getLexema();
        consumir(TipoToken.ID);
        comando.setDestino(destino);
        agente.agregarComando(comando);
    }
    
    private void recuperarComando() {
        while (tokenActual.getTipo() != TipoToken.EOF
                && tokenActual.getTipo() != TipoToken.COMANDO_IA
                && tokenActual.getTipo() != TipoToken.LLAVE_C
                && tokenActual.getTipo() != TipoToken.EJECUTAR
                && tokenActual.getTipo() != TipoToken.EXPORTAR) {

            avanzar();
        }
    }

    private void conectores(Comando comando) {
        if (tokenActual.getTipo() == TipoToken.CONECTOR) {

            String tipo = tokenActual.getLexema();

            consumir(TipoToken.CONECTOR);

            Expresion expresion = expresion();

            Conector conector = new Conector(tipo, expresion);

            comando.agragrConector(conector);

            conectores(comando);
        }
    }

    private Expresion expresion() {

        Expresion expresion = new Expresion();

        termino(expresion);

        masTerminos(expresion);

        return expresion;
    }

    private void masTerminos(Expresion expresion) {

        if (tokenActual.getTipo() == TipoToken.MAS) {

            consumir(TipoToken.MAS);

            termino(expresion);

            masTerminos(expresion);
        }
    }

    private void termino(Expresion expresion) {

        if (tokenActual.getTipo() == TipoToken.CADENA) {

            expresion.agregarTermino(tokenActual.getLexema());

            consumir(TipoToken.CADENA);

        } else if (tokenActual.getTipo() == TipoToken.ID) {

            expresion.agregarTermino(tokenActual.getLexema());

            consumir(TipoToken.ID);

        } else if (tokenActual.getTipo() == TipoToken.NUMERO) {

            String numero = tokenActual.getLexema();

            consumir(TipoToken.NUMERO);

            String unidad = unidad();

            if (unidad != null) {
                numero += " " + unidad;
            }

            expresion.agregarTermino(numero);

        } else if (tokenActual.getTipo() == TipoToken.CARGAR) {

            String cargar = cargar();

            expresion.agregarTermino(cargar);

        } else {

            registrarError("CADENA, ID, NUMERO o CARGAR");
            recuperar();
        }
    }

    private String unidad() {

        if (tokenActual.getTipo() == TipoToken.ID) {

            String unidad = tokenActual.getLexema();

            consumir(TipoToken.ID);

            return unidad;
        }

        return null;
    }

    private String cargar() {

        String resultado = tokenActual.getLexema();

        consumir(TipoToken.CARGAR);

        consumir(TipoToken.PAR_A);

        if (tokenActual.getTipo() == TipoToken.CADENA) {

            resultado += "(";
            resultado += tokenActual.getLexema();

            consumir(TipoToken.CADENA);

            resultado += ")";

        } else {

            consumir(TipoToken.CADENA);
        }

        consumir(TipoToken.PAR_C);

        return resultado;
    }

    private void ejecuciones() {

        consumir(TipoToken.EJECUTAR);
        consumir(TipoToken.ID);
        masEjecuciones();
    }

    private void masEjecuciones() {

        if (tokenActual.getTipo() == TipoToken.EJECUTAR) {

            consumir(TipoToken.EJECUTAR);
            consumir(TipoToken.ID);
            masEjecuciones();
        }
    }

    private void exportar() {

        consumir(TipoToken.EXPORTAR);
        consumir(TipoToken.ID);
        masIds();
    }

    private void masIds() {

        if (tokenActual.getTipo() == TipoToken.COMA) {

            consumir(TipoToken.COMA);
            consumir(TipoToken.ID);
            masIds();
        }
    }
}
