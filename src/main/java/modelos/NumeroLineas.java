package modelos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;

/**
 * Componente para mostrar números de línea perfectamente sincronizados con el scroll.
 */
public class NumeroLineas extends JComponent implements DocumentListener, ChangeListener {

    private final JTextPane textPane;
    private final JScrollPane scrollPane;
    private final Font font;

    public NumeroLineas(JTextPane textPane, JScrollPane scrollPane) {
        this.textPane = textPane;
        this.scrollPane = scrollPane;
        this.font = new Font("Consolas", Font.PLAIN, 14);

        this.textPane.getDocument().addDocumentListener(this);
        if (this.scrollPane != null && this.scrollPane.getViewport() != null) {
            this.scrollPane.getViewport().addChangeListener(this);
        }

        setPreferredSize(new Dimension(40, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(24, 24, 24));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(50, 50, 50));
        g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());

        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        Element root = textPane.getDocument().getDefaultRootElement();
        int lineCount = root.getElementCount();

        Point scrollPoint = scrollPane.getViewport().getViewPosition();

        g.setColor(new Color(110, 118, 129)); // Color de los números

        for (int i = 0; i < lineCount; i++) {
            Element line = root.getElement(i);
            int startOffset = line.getStartOffset();

            try {
                Rectangle r = textPane.modelToView2D(startOffset).getBounds();
                if (r != null) {
                    int yPos = r.y - scrollPoint.y + fm.getAscent();

                    if (yPos >= 0 && yPos <= getHeight() + fm.getHeight()) {
                        String number = String.valueOf(i + 1);
                        int stringWidth = fm.stringWidth(number);
                        int xPos = getWidth() - stringWidth - 8;

                        g.drawString(number, xPos, yPos);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        repaint();
    }

    @Override public void insertUpdate(DocumentEvent e) { revalidarYRepintar(); }
    @Override public void removeUpdate(DocumentEvent e) { revalidarYRepintar(); }
    @Override public void changedUpdate(DocumentEvent e) { revalidarYRepintar(); }

    private void revalidarYRepintar() {
        Element root = textPane.getDocument().getDefaultRootElement();
        int lineCount = root.getElementCount();
        int digits = String.valueOf(lineCount).length();
        int nuevoAncho = Math.max(40, 20 + (digits * 8));

        if (getPreferredSize().width != nuevoAncho) {
            setPreferredSize(new Dimension(nuevoAncho, 0));
            revalidate();
        }
        repaint();
    }
}