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
import modelos.Directiva;
import modelos.ErrorSintactico;
import modelos.Expresion;
import modelos.Programa;
import modelos.Simbolo;
import modelos.TablaSimbolos;
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
    private TablaSimbolos tablaSimbolos;

    public Parser(List<Token> tokens) {

        this.tokens = tokens;
        this.posicion = 0;

        programa = new Programa();
        tablaSimbolos = new TablaSimbolos();

        if (tokens != null && !tokens.isEmpty()) {
            this.tokenActual = tokens.get(0);
        } else {
            this.tokenActual = null;
        }
    }

    public List<ErrorSintactico> getErrores() {
        return errores;
    }

    public Programa getPrograma() {
        return programa;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
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
        if (tokenActual == null) {
            return;
        }
        programa();
    }

    private void programa() {
        directivas();
        agentes();
        // Analizar la sección obligatoria de ejecuciones
        if (tokenActual.getTipo() == TipoToken.EJECUTAR) {

            ejecuciones();

        } else {

            registrarError(TipoToken.EJECUTAR.toString());

            if (tokenActual.getTipo() != TipoToken.EOF) {
                recuperarNivelSuperior();
            }

            if (tokenActual.getTipo() == TipoToken.EJECUTAR) {
                ejecuciones();
            }
        }

        // Analizar la sección obligatoria de exportación
        if (tokenActual.getTipo() == TipoToken.EXPORTAR) {

            exportar();

        } else {

            registrarError(TipoToken.EXPORTAR.toString());

            if (tokenActual.getTipo() != TipoToken.EOF) {
                recuperarNivelSuperior();
            }

            if (tokenActual.getTipo() == TipoToken.EXPORTAR) {
                exportar();
            }
        }

        // Verificar el final del archivo
        consumir(TipoToken.EOF);
    }

    private void directivas() {

        if (tokenActual.getTipo() == TipoToken.DIRECTIVA) {

            String nombre = tokenActual.getLexema();
            int fila = tokenActual.getFila();

            consumir(TipoToken.DIRECTIVA);

            if (tokenActual.getTipo() == TipoToken.CADENA) {

                String valor = tokenActual.getLexema();
                valor = valor.substring(1, valor.length() - 1);
                consumir(TipoToken.CADENA);

                programa.agregarDirectiva(new Directiva(nombre, valor, fila));

            } else {
                consumir(TipoToken.CADENA);  
            }

            directivas();
        }
    }

    private void agentes() {

        if (tokenActual.getTipo() != TipoToken.AGENTE) {
            registrarError(TipoToken.AGENTE.toString());

            while (tokenActual.getTipo() != TipoToken.EOF
                    && tokenActual.getTipo() != TipoToken.AGENTE
                    && tokenActual.getTipo() != TipoToken.EJECUTAR
                    && tokenActual.getTipo() != TipoToken.EXPORTAR) {
                avanzar();
            }

            if (tokenActual.getTipo() != TipoToken.AGENTE) {
                return;
            }
        }

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

        // Verificar que el agente tenga un nombre
        if (tokenActual.getTipo() != TipoToken.ID) {
            registrarError(TipoToken.ID.toString());

            // Recuperar el analisis
            while (tokenActual.getTipo() != TipoToken.EOF
                    && tokenActual.getTipo() != TipoToken.AGENTE
                    && tokenActual.getTipo() != TipoToken.EJECUTAR
                    && tokenActual.getTipo() != TipoToken.EXPORTAR) {

                avanzar();
            }

            return;
        }

        String nombre = tokenActual.getLexema();
        int fila = tokenActual.getFila();

        consumir(TipoToken.ID);

        if (!consumir(TipoToken.LLAVE_A)) {

            while (tokenActual.getTipo() != TipoToken.EOF
                    && tokenActual.getTipo() != TipoToken.AGENTE
                    && tokenActual.getTipo() != TipoToken.EJECUTAR
                    && tokenActual.getTipo() != TipoToken.EXPORTAR) {

                avanzar();
            }

            return;
        }
        Agente agente = new Agente(nombre, fila);
        programa.agregarAgente(agente);

        tablaSimbolos.agregarSimbolo(new Simbolo(nombre, "AGENTE", "GLOBAL", fila));

        if (!contexto(agente)) {
            recuperarContexto();
        }

        variables(agente);
        comandos(agente);

        if (!consumir(TipoToken.LLAVE_C)) {
            while (tokenActual.getTipo() != TipoToken.EOF
                    && tokenActual.getTipo() != TipoToken.AGENTE
                    && tokenActual.getTipo() != TipoToken.EJECUTAR
                    && tokenActual.getTipo() != TipoToken.EXPORTAR) {
                avanzar();
            }
        }

    }

    private boolean contexto(Agente agente) {

        // Verificar que exista la palabra CONTEXTO
        if (!consumir(TipoToken.CONTEXTO)) {
            return false;
        }

        // Verificar el signo igual
        if (!consumir(TipoToken.IGUAL)) {
            recuperarContexto();
            return false;
        }

        if (tokenActual.getTipo() != TipoToken.CADENA) {
            registrarError(TipoToken.CADENA.toString());
            recuperarContexto();
            return false;
        }

        String texto = tokenActual.getLexema();

        texto = texto.substring(1, texto.length() - 1);

        consumir(TipoToken.CADENA);
        agente.setContexto(texto);

        return true;
    }

    private void recuperarContexto() {
        while (tokenActual.getTipo() != TipoToken.EOF
                && tokenActual.getTipo() != TipoToken.VARIABLE
                && tokenActual.getTipo() != TipoToken.COMANDO_IA
                && tokenActual.getTipo() != TipoToken.LLAVE_C
                && tokenActual.getTipo() != TipoToken.AGENTE
                && tokenActual.getTipo() != TipoToken.EJECUTAR
                && tokenActual.getTipo() != TipoToken.EXPORTAR) {
            avanzar();
        }
    }

    private void variables(Agente agente) {

        if (tokenActual.getTipo() == TipoToken.VARIABLE) {

            consumir(TipoToken.VARIABLE);

            if (tokenActual.getTipo() != TipoToken.ID) {
                registrarError(TipoToken.ID.toString());
                recuperar();
                variables(agente);
                return;
            }

            String nombre = tokenActual.getLexema();
            int fila = tokenActual.getFila();
            consumir(TipoToken.ID);

            Expresion expresion;
            if (consumir(TipoToken.IGUAL)) {
                expresion = expresion();
            } else {
                expresion = new Expresion();
                expresion.setValida(false);
            }

            agente.agregarVariables(new Variable(nombre, expresion, fila));
            tablaSimbolos.agregarSimbolo(
                    new Simbolo(nombre, "VARIABLE", agente.getNombre(), fila));

            if (!expresion.isValida()) {
                recuperar();
            }

            variables(agente);
        }
    }

    private void comandos(Agente agente) {
        if (tokenActual.getTipo() == TipoToken.COMANDO_IA) {
            comando(agente);
            masComandos(agente);
        } else {
            registrarError(TipoToken.COMANDO_IA.toString());

            // No intentar recuperar si ya terminó el archivo.
            if (tokenActual.getTipo() == TipoToken.EOF) {
                return;
            }

            recuperarComando();

            if (tokenActual.getTipo() == TipoToken.COMANDO_IA) {
                comando(agente);
                masComandos(agente);
            }
        }
    }

    private void masComandos(Agente agente) {

        if (tokenActual.getTipo() == TipoToken.COMANDO_IA) {

            comando(agente);
            masComandos(agente);
        }
    }

    private void comando(Agente agente) {

        if (tokenActual.getTipo() != TipoToken.COMANDO_IA) {
            registrarError(TipoToken.COMANDO_IA.toString());
            return;
        }

        String tipo = tokenActual.getLexema();
        int filaComando = tokenActual.getFila();

        consumir(TipoToken.COMANDO_IA);
        Comando comando = new Comando(tipo);
        comando.setFila(filaComando);
        Expresion expresion;

        if (tipo.equals("GENERAR")) {
            expresion = expresion(true);
        } else {
            expresion = expresion(false);
        }

        if (!expresion.isValida() || expresion.getTerminos().isEmpty()) {
            recuperarComando();
            return;
        }

        comando.setExpresion(expresion);
        if (!conectores(comando)) {
            return;
        }

        if (!consumir(TipoToken.FLECHA)) {
            recuperarComando();
            return;
        }
        if (tokenActual.getTipo() != TipoToken.ID) {
            registrarError(TipoToken.ID.toString());
            recuperarComando();
            return;
        }

        String destino = tokenActual.getLexema();
        int filaDestino = tokenActual.getFila();

        consumir(TipoToken.ID);

        comando.setDestino(destino);
        comando.setFilaDestino(filaDestino);
        agente.agregarComando(comando);

        Simbolo simbolo = new Simbolo(
                destino,
                "RESULTADO_COMANDO",
                agente.getNombre(),
                filaDestino
        );

        tablaSimbolos.agregarSimbolo(simbolo);
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

    private boolean conectores(Comando comando) {
        if (tokenActual.getTipo() == TipoToken.CONECTOR) {
            String tipo = tokenActual.getLexema();
            consumir(TipoToken.CONECTOR);

            Expresion expresion = expresion();

            if (!expresion.isValida() || expresion.getTerminos().isEmpty()) {
                recuperarComando();
                return false;
            }

            Conector conector = new Conector(tipo, expresion);
            comando.agregarConector(conector);

            return conectores(comando);
        }

        return true;
    }

    private Expresion expresion() {
        return expresion(false);
    }

    private Expresion expresion(boolean primerIdEsTipoContenido) {

        Expresion expresion = new Expresion();

        termino(expresion, primerIdEsTipoContenido);

        masTerminos(expresion);

        return expresion;
    }

    private void masTerminos(Expresion expresion) {

        if (tokenActual.getTipo() == TipoToken.MAS) {

            consumir(TipoToken.MAS);

            // Verificar que exista un termino despues de MAS.
            if (tokenActual.getTipo() != TipoToken.CADENA
                    && tokenActual.getTipo() != TipoToken.ID
                    && tokenActual.getTipo() != TipoToken.NUMERO
                    && tokenActual.getTipo() != TipoToken.CARGAR) {

                registrarError("un termino despues de MAS");
                expresion.setValida(false);

                // Recuperar sin consumir la siguiente instruccion valida.
                if (tokenActual.getTipo() != TipoToken.VARIABLE
                        && tokenActual.getTipo() != TipoToken.COMANDO_IA
                        && tokenActual.getTipo() != TipoToken.LLAVE_C
                        && tokenActual.getTipo() != TipoToken.EJECUTAR
                        && tokenActual.getTipo() != TipoToken.EXPORTAR
                        && tokenActual.getTipo() != TipoToken.EOF) {

                    recuperar();
                }

                return;
            }

            termino(expresion, false);

            masTerminos(expresion);
        }
    }

    private void termino(Expresion expresion, boolean primerIdEsTipoContenido) {

        if (tokenActual.getTipo() == TipoToken.CADENA) {

            expresion.agregarCadena(tokenActual.getLexema(), tokenActual.getFila());
            consumir(TipoToken.CADENA);

        } else if (tokenActual.getTipo() == TipoToken.ID) {

            String nombre = tokenActual.getLexema();
            int fila = tokenActual.getFila();

            if (primerIdEsTipoContenido) {
                expresion.agregarTipoContenido(nombre, fila);
            } else {
                expresion.agregarVariable(nombre, fila);
            }
            consumir(TipoToken.ID);

        } else if (tokenActual.getTipo() == TipoToken.NUMERO) {

            int fila = tokenActual.getFila();
            String numero = tokenActual.getLexema();
            consumir(TipoToken.NUMERO);

            String unidad = unidad();
            if (unidad != null) {
                numero += " " + unidad;
            }
            expresion.agregarNumero(numero, fila);

        } else if (tokenActual.getTipo() == TipoToken.CARGAR) {

            int fila = tokenActual.getFila();
            String archivo = cargar();

            if (archivo != null) {
                expresion.agregarCarga(archivo, fila);
            } else {
                expresion.setValida(false);
            }

        } else {

            registrarError("CADENA, ID, NUMERO o CARGAR");

            expresion.setValida(false);

            if (tokenActual.getTipo() != TipoToken.FLECHA
                    && tokenActual.getTipo() != TipoToken.LLAVE_C
                    && tokenActual.getTipo() != TipoToken.COMANDO_IA
                    && tokenActual.getTipo() != TipoToken.EJECUTAR
                    && tokenActual.getTipo() != TipoToken.EXPORTAR
                    && tokenActual.getTipo() != TipoToken.EOF) {

                recuperar();
            }
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

        consumir(TipoToken.CARGAR);

        if (!consumir(TipoToken.PAR_A)) {
            return null;
        }

        if (tokenActual.getTipo() != TipoToken.CADENA) {
            registrarError(TipoToken.CADENA.toString());
            return null;
        }

        String archivo = tokenActual.getLexema();
        consumir(TipoToken.CADENA);

        if (!consumir(TipoToken.PAR_C)) {
            return null;
        }

        return archivo;
    }

    private void ejecuciones() {

        if (tokenActual.getTipo() != TipoToken.EJECUTAR) {
            registrarError(TipoToken.EJECUTAR.toString());
            recuperarNivelSuperior();
            return;
        }

        consumir(TipoToken.EJECUTAR);

        if (tokenActual.getTipo() != TipoToken.ID) {
            registrarError(TipoToken.ID.toString());
            recuperarNivelSuperior();
            return;
        }

        String nombre = tokenActual.getLexema();
        int fila = tokenActual.getFila();
        consumir(TipoToken.ID);

        programa.agregarEjecucion(nombre, fila);

        masEjecuciones();
    }

    private void masEjecuciones() {
        if (tokenActual.getTipo() == TipoToken.EJECUTAR) {
            consumir(TipoToken.EJECUTAR);
            if (tokenActual.getTipo() != TipoToken.ID) {
                registrarError(TipoToken.ID.toString());
                return;
            }
            String nombre = tokenActual.getLexema();
            int fila = tokenActual.getFila();
            consumir(TipoToken.ID);
            programa.agregarEjecucion(nombre, fila);
            masEjecuciones();
        }
    }

    private void exportar() {

        if (tokenActual.getTipo() != TipoToken.EXPORTAR) {
            registrarError(TipoToken.EXPORTAR.toString());
            recuperarNivelSuperior();
            return;
        }

        consumir(TipoToken.EXPORTAR);

        if (tokenActual.getTipo() != TipoToken.ID) {
            registrarError(TipoToken.ID.toString());
            recuperarNivelSuperior();
            return;
        }

        String nombre = tokenActual.getLexema();
        int fila = tokenActual.getFila();
        consumir(TipoToken.ID);

        programa.agregarExportacion(nombre, fila);

        masIds();
    }

    private void recuperarNivelSuperior() {
        while (tokenActual.getTipo() != TipoToken.EJECUTAR
                && tokenActual.getTipo() != TipoToken.EXPORTAR
                && tokenActual.getTipo() != TipoToken.EOF) {

            avanzar();
        }
    }

    private void masIds() {
        if (tokenActual.getTipo() == TipoToken.COMA) {
            consumir(TipoToken.COMA);
            if (tokenActual.getTipo() != TipoToken.ID) {
                registrarError(TipoToken.ID.toString());
                return;
            }
            String nombre = tokenActual.getLexema();
            int fila = tokenActual.getFila();
            consumir(TipoToken.ID);
            programa.agregarExportacion(nombre, fila);
            masIds();
        }
    }
}
