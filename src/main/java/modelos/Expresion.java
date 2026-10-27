/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fernan
 */
public class Expresion {
    
    private final List<Termino> items = new ArrayList<>();
    private boolean valida = true;

    private static String quitarComillas(String s) {
        if (s != null && s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    public void agregarCadena(String lexema, int fila) {
        items.add(new Termino(Termino.Tipo.CADENA, quitarComillas(lexema), fila));
    }

    public void agregarNumero(String texto, int fila) {
        items.add(new Termino(Termino.Tipo.NUMERO, texto, fila));
    }

    public void agregarCarga(String lexemaArchivo, int fila) {
        items.add(new Termino(Termino.Tipo.CARGAR, quitarComillas(lexemaArchivo), fila));
    }

    public void agregarTipoContenido(String nombre, int fila) {
        items.add(new Termino(Termino.Tipo.TIPO_CONTENIDO, nombre, fila));
    }

    public void agregarVariable(String nombre, int fila) {
        items.add(new Termino(Termino.Tipo.VARIABLE, nombre, fila));
    }

    public List<Termino> getItems() {
        return items;
    }

    public List<String> getTerminos() {
        List<String> valores = new ArrayList<>();
        for (Termino t : items) {
            valores.add(t.getValor());
        }
        return valores;
    }

    public boolean esVariable(int posicion) {
        return items.get(posicion).getTipo() == Termino.Tipo.VARIABLE;
    }

    public int getFila(int posicion) {
        return items.get(posicion).getFila();
    }

    public String obtenerTexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sb.append(" + ");
            Termino t = items.get(i);
            switch (t.getTipo()) {
                case CADENA: sb.append("\"").append(t.getValor()).append("\""); break;
                case CARGAR: sb.append("CARGAR(\"").append(t.getValor()).append("\")"); break;
                default:     sb.append(t.getValor());
            }
        }
        return sb.toString();
    }

    public boolean isValida() { return valida; }
    public void setValida(boolean valida) { this.valida = valida; }
}