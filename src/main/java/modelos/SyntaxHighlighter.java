/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author fernan
 */
public class SyntaxHighlighter {
    public static void aplicarHighlight(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        String texto = textPane.getText();
        StyleContext sc = StyleContext.getDefaultStyleContext();
       
        AttributeSet estiloNormal = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(220, 220, 220));
        AttributeSet estiloDirectiva = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(197, 134, 192)); // Morado
        AttributeSet estiloReservada = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(86, 156, 214));  // Azul brillante
        AttributeSet estiloComando = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(220, 220, 170));   // Amarillo verdoso
        AttributeSet estiloCadena = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(206, 145, 120));   // Naranja / Salmón
        AttributeSet estiloComentario = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(106, 153, 85)); // Verde opaco

        SwingUtilities.invokeLater(() -> {
            doc.setCharacterAttributes(0, texto.length(), estiloNormal, true);
            marcarPatron(doc, texto, "@[a-zA-Z_]+", estiloDirectiva);

            String reservadas = "\\b(AGENTE|contexto|variable|EJECUTAR|EXPORTAR|SOBRE|DESDE|EN|COMO|CARGAR)\\b";
            marcarPatron(doc, texto, reservadas, estiloReservada);
            String comandos = "\\b(PREGUNTAR|GENERAR|RESUMIR|ANALIZAR|TRADUCIR|CLASIFICAR|EXTRAER)\\b";
            marcarPatron(doc, texto, comandos, estiloComando);
            marcarPatron(doc, texto, "\"([^\"\\\r\n]|\\[^\r\n])*\"", estiloCadena);
            marcarPatron(doc, texto, "//[^\r\n]*", estiloComentario);
        });
    }

    private static void marcarPatron(StyledDocument doc, String texto, String regex, AttributeSet estilo) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            doc.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), estilo, false);
        }
    }
}
