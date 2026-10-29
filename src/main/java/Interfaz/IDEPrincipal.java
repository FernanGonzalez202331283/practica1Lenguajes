/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaz;

import Analizador.AnalizadorSemantico;
import Analizador.GeneradorPromptZal;
import Analizador.Lexer;
import Analizador.Parser;
import java.io.StringReader;
import javax.swing.table.DefaultTableModel;
import modelos.ErrorLexico;
import modelos.ErrorSemantico;
import modelos.ErrorSintactico;
import modelos.NumeroLineas;
import modelos.Reportes;
import modelos.Simbolo;
import modelos.SyntaxHighlighter;
import modelos.TipoToken;
import modelos.Token;

/**
 *
 * @author fernan
 */
public class IDEPrincipal extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(IDEPrincipal.class.getName());
    private Lexer ultimoLexer;
    private Parser ultimoParser;
    private AnalizadorSemantico ultimoSemantico;

    /**
     * Creates new form IDEPrincipal
     */

    public IDEPrincipal() {
        initComponents();
        // Barra de herramientas con botón Compilar
        javax.swing.JToolBar barra = new javax.swing.JToolBar();
        barra.setFloatable(false);
        barra.setBackground(new java.awt.Color(37, 37, 38));

        javax.swing.JButton botonCompilar = new javax.swing.JButton("▶ Compilar");
        botonCompilar.setFocusPainted(false);
        botonCompilar.setBackground(new java.awt.Color(14, 99, 156));
        botonCompilar.setForeground(java.awt.Color.WHITE);
        botonCompilar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        botonCompilar.addActionListener(e -> compilar());

        barra.add(botonCompilar);
        getContentPane().add(barra, java.awt.BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        java.awt.Color fondoOscuro = new java.awt.Color(30, 30, 30);
        java.awt.Color fondoConsola = new java.awt.Color(18, 18, 18);

        java.awt.Font fuenteCode = new java.awt.Font("Consolas", java.awt.Font.PLAIN, 13);

        txtEditor.setFont(new java.awt.Font("Consolas", java.awt.Font.PLAIN, 14));
        txtEditor.setBackground(fondoOscuro);
        txtEditor.setForeground(new java.awt.Color(220, 220, 220));
        txtEditor.setCaretColor(java.awt.Color.WHITE);
        jScrollPane2.setRowHeaderView(new NumeroLineas(txtEditor, jScrollPane2));
        jScrollPane2.getViewport().setBackground(fondoOscuro);

        // --- CONSOLA ---
        txtConsola.setFont(fuenteCode);
        txtConsola.setBackground(fondoConsola);
        txtConsola.setForeground(new java.awt.Color(130, 220, 130));
        txtConsola.setCaretColor(java.awt.Color.WHITE);
        txtConsola.setMargin(new java.awt.Insets(8, 8, 8, 8));
        txtConsola.setEditable(false);

        Consola.setBackground(fondoConsola);
        jScrollPane1.setBackground(fondoConsola);
        jScrollPane1.getViewport().setBackground(fondoConsola);

        personalizarTabla(tblErrores, new java.awt.Color(255, 110, 110));
        personalizarTabla(tblSimbolo, new java.awt.Color(100, 210, 255));

        Errores.setBackground(fondoOscuro);
        tblError.setBackground(fondoOscuro);
        tblError.getViewport().setBackground(fondoOscuro);

        Simbolos.setBackground(fondoOscuro);
        tblSimbolos.setBackground(fondoOscuro);
        tblSimbolos.getViewport().setBackground(fondoOscuro);

        txtEditor.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                SyntaxHighlighter.aplicarHighlight(txtEditor);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                SyntaxHighlighter.aplicarHighlight(txtEditor);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
            }
        });

        Consola.setLayout(new java.awt.BorderLayout());
        Consola.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        Errores.setLayout(new java.awt.BorderLayout());
        Errores.add(tblError, java.awt.BorderLayout.CENTER);

        Simbolos.setLayout(new java.awt.BorderLayout());
        Simbolos.add(tblSimbolos, java.awt.BorderLayout.CENTER);

    }

    private void personalizarTabla(javax.swing.JTable tabla, java.awt.Color colorTextoFilas) {
        java.awt.Color fondoTabla = new java.awt.Color(30, 30, 30);

        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setRowHeight(24);
        tabla.setShowGrid(true);
        tabla.setGridColor(new java.awt.Color(50, 50, 50));
        tabla.setBackground(fondoTabla);
        tabla.setOpaque(true);

        tabla.setSelectionBackground(new java.awt.Color(50, 70, 100));
        tabla.setSelectionForeground(java.awt.Color.WHITE);

        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(new java.awt.Font("Consolas", java.awt.Font.PLAIN, 12));

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    c.setBackground(row % 2 == 0 ? new java.awt.Color(26, 26, 26) : new java.awt.Color(34, 34, 34));
                    c.setForeground(colorTextoFilas);
                }
                return c;
            }
        });

        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setBackground(new java.awt.Color(20, 20, 20));
        header.setForeground(new java.awt.Color(180, 180, 180));
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        header.setPreferredSize(new java.awt.Dimension(header.getPreferredSize().width, 28));

        if (tabla == tblErrores) {
            tabla.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        int filaSeleccionada = tblErrores.getSelectedRow();
                        if (filaSeleccionada != -1) {
                            Object valFila = tblErrores.getValueAt(filaSeleccionada, 1);
                            if (valFila != null) {
                                try {
                                    int numeroFila = Integer.parseInt(valFila.toString());
                                    irALinea(numeroFila);
                                } catch (NumberFormatException e) {
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void irALinea(int numeroFila) {
        if (numeroFila <= 0) {
            return;
        }

        try {
            javax.swing.text.Element root = txtEditor.getDocument().getDefaultRootElement();
            if (numeroFila <= root.getElementCount()) {
                javax.swing.text.Element elem = root.getElement(numeroFila - 1);
                int startOffset = elem.getStartOffset();
                int endOffset = elem.getEndOffset();

                txtEditor.requestFocusInWindow();
                txtEditor.setCaretPosition(startOffset);
                txtEditor.moveCaretPosition(Math.min(endOffset - 1, txtEditor.getDocument().getLength()));

                java.awt.Rectangle rect = txtEditor.modelToView2D(startOffset).getBounds();
                if (rect != null) {
                    txtEditor.scrollRectToVisible(rect);
                }
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "No se pudo navegar a la fila: " + numeroFila, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEditor = new javax.swing.JTextPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Consola = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConsola = new javax.swing.JTextArea();
        Errores = new javax.swing.JPanel();
        tblError = new javax.swing.JScrollPane();
        tblErrores = new javax.swing.JTable();
        Simbolos = new javax.swing.JPanel();
        tblSimbolos = new javax.swing.JScrollPane();
        tblSimbolo = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itemAbrir = new javax.swing.JMenuItem();
        itemGuardar = new javax.swing.JMenuItem();
        itemGuardarPrompt = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        itemRepErrores = new javax.swing.JMenuItem();
        itemRepSimbolos = new javax.swing.JMenuItem();
        itemRepAST = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PromptZal IDE");

        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.7);

        jScrollPane2.setViewportView(txtEditor);

        jSplitPane2.setTopComponent(jScrollPane2);

        txtConsola.setColumns(20);
        txtConsola.setRows(5);
        jScrollPane1.setViewportView(txtConsola);

        javax.swing.GroupLayout ConsolaLayout = new javax.swing.GroupLayout(Consola);
        Consola.setLayout(ConsolaLayout);
        ConsolaLayout.setHorizontalGroup(
            ConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsolaLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        ConsolaLayout.setVerticalGroup(
            ConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsolaLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Consola", Consola);

        tblErrores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblError.setViewportView(tblErrores);

        javax.swing.GroupLayout ErroresLayout = new javax.swing.GroupLayout(Errores);
        Errores.setLayout(ErroresLayout);
        ErroresLayout.setHorizontalGroup(
            ErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ErroresLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(tblError, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        ErroresLayout.setVerticalGroup(
            ErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ErroresLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(tblError, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Errores", Errores);

        tblSimbolo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSimbolos.setViewportView(tblSimbolo);

        javax.swing.GroupLayout SimbolosLayout = new javax.swing.GroupLayout(Simbolos);
        Simbolos.setLayout(SimbolosLayout);
        SimbolosLayout.setHorizontalGroup(
            SimbolosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SimbolosLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(tblSimbolos, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );
        SimbolosLayout.setVerticalGroup(
            SimbolosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SimbolosLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(tblSimbolos, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Simbolos", Simbolos);

        jSplitPane2.setRightComponent(jTabbedPane1);

        getContentPane().add(jSplitPane2, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Archivo");

        itemAbrir.setText("Abrir");
        itemAbrir.addActionListener(this::itemAbrirActionPerformed);
        jMenu1.add(itemAbrir);

        itemGuardar.setText("Guardar");
        itemGuardar.addActionListener(this::itemGuardarActionPerformed);
        jMenu1.add(itemGuardar);

        itemGuardarPrompt.setText("Guardar Prompt");
        itemGuardarPrompt.addActionListener(this::itemGuardarPromptActionPerformed);
        jMenu1.add(itemGuardarPrompt);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Reportes");

        itemRepErrores.setText("Reporte de Errores");
        itemRepErrores.addActionListener(this::itemRepErroresActionPerformed);
        jMenu3.add(itemRepErrores);

        itemRepSimbolos.setText("Reporte Tabla de Simbolos");
        itemRepSimbolos.addActionListener(this::itemRepSimbolosActionPerformed);
        jMenu3.add(itemRepSimbolos);

        itemRepAST.setText("Reporte de AST");
        itemRepAST.addActionListener(this::itemRepASTActionPerformed);
        jMenu3.add(itemRepAST);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAbrirActionPerformed
        // TODO add your handling code here:
        abrirArchivo();
    }//GEN-LAST:event_itemAbrirActionPerformed

    private void itemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarActionPerformed
        // TODO add your handling code here:
        guardarArchivoPz();
    }//GEN-LAST:event_itemGuardarActionPerformed

    private void itemGuardarPromptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarPromptActionPerformed
        // TODO add your handling code here:
        guardarPromptMD();
    }//GEN-LAST:event_itemGuardarPromptActionPerformed

    private void itemRepErroresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRepErroresActionPerformed
        // TODO add your handling code here:
        exportarReporte("errores");
    }//GEN-LAST:event_itemRepErroresActionPerformed

    private void itemRepSimbolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRepSimbolosActionPerformed
        // TODO add your handling code here:
        exportarReporte("simbolos");
    }//GEN-LAST:event_itemRepSimbolosActionPerformed

    private void itemRepASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRepASTActionPerformed
        // TODO add your handling code here:
        exportarReporte("ast");
    }//GEN-LAST:event_itemRepASTActionPerformed

    private void compilar() {
        txtConsola.setText("");

        DefaultTableModel modeloErrores = new DefaultTableModel(
                new String[]{"Tipo", "Fila", "Columna", "Descripción / Detalle"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            } // Hacer la tabla de solo lectura
        };
        tblErrores.setModel(modeloErrores);

        if (tblErrores.getColumnModel().getColumnCount() > 0) {
            tblErrores.getColumnModel().getColumn(0).setPreferredWidth(90);  // Tipo
            tblErrores.getColumnModel().getColumn(1).setPreferredWidth(50);  // Fila
            tblErrores.getColumnModel().getColumn(2).setPreferredWidth(65);  // Columna
            tblErrores.getColumnModel().getColumn(3).setPreferredWidth(600); // Descripción / Detalle
        }

        DefaultTableModel modeloSimbolos = new DefaultTableModel(
                new String[]{"Nombre", "Tipo", "Ámbito", "Fila"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            } // Hacer la tabla de solo lectura
        };
        tblSimbolo.setModel(modeloSimbolos);

        if (tblSimbolo.getColumnModel().getColumnCount() > 0) {
            tblSimbolo.getColumnModel().getColumn(0).setPreferredWidth(150); // Nombre
            tblSimbolo.getColumnModel().getColumn(1).setPreferredWidth(100); // Tipo
            tblSimbolo.getColumnModel().getColumn(2).setPreferredWidth(120); // Ámbito
            tblSimbolo.getColumnModel().getColumn(3).setPreferredWidth(60);  // Fila
        }

        String codigo = txtEditor.getText();
        if (codigo.trim().isEmpty()) {
            txtConsola.setText("[INFO] El editor está vacío.");
            return;
        }

        boolean hayErrores = false;

        Lexer lexer = new Lexer(new StringReader(codigo));
        ultimoLexer = lexer;
        try {
            Token token;
            while ((token = lexer.yylex()) != null) {
                if (token.getTipo() == TipoToken.EOF) {
                    break; // Salir al llegar al final del archivo
                }
            }
        } catch (Exception e) {
            txtConsola.setText("[ERROR LÉXICO]: No se pudo leer la entrada.\n" + e.getMessage());
            return;
        }

        for (ErrorLexico err : lexer.getErrores()) {
            String detalle = "Lexema no reconocido: '" + err.getLexema() + "' | " + err.getTipoError();
            modeloErrores.addRow(new Object[]{"Léxico", err.getFila(), err.getColumna(), detalle});
            hayErrores = true;
        }

        Parser parser = new Parser(lexer.getTokens());
        ultimoParser = parser;
        parser.analizar();

        for (ErrorSintactico err : parser.getErrores()) {
            String detalle = "Encontrado: '" + err.getEncontrado() + "' | Se esperaba: '" + err.getEsperando() + "'";
            modeloErrores.addRow(new Object[]{"Sintáctico", err.getFila(), err.getColumna(), detalle});
            hayErrores = true;
        }

        if (parser.getTablaSimbolos() != null) {
            for (Simbolo s : parser.getTablaSimbolos().getSimbolos()) {
                modeloSimbolos.addRow(new Object[]{s.getNombre(), s.getTipo(), s.getAmbito(), s.getFila()});
            }
        }

        AnalizadorSemantico semantico = new AnalizadorSemantico(parser.getPrograma(), parser.getTablaSimbolos());
        ultimoSemantico = semantico;
        semantico.analizar();

        for (ErrorSemantico err : semantico.getErrores()) {
            modeloErrores.addRow(new Object[]{"Semántico", err.getFila(), "-", err.getDescripcion()});
            hayErrores = true;
        }

        if (!hayErrores) {
            String prompt = GeneradorPromptZal.generar(parser.getPrograma());
            txtConsola.setText(prompt);
            jTabbedPane1.setSelectedIndex(0);
        } else {
            txtConsola.setText("La compilación finalizó con errores.\nRevisa el detalle en la pestaña 'Errores'.");
            jTabbedPane1.setSelectedIndex(1);
        }
    }

    // Metodo para Abrir Archivos .pz
    private void abrirArchivo() {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        if (fc.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fc.getSelectedFile()))) {
                StringBuilder sb = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                txtEditor.setText(sb.toString());
                // Aplicar colores al abrir
                SyntaxHighlighter.aplicarHighlight(txtEditor);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al abrir: " + e.getMessage());
            }
        }
    }

    // Metodo para Guardar Archivo .pz
    private void guardarArchivoPz() {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setSelectedFile(new java.io.File("programa.pz"));
        if (fc.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            try (java.io.FileWriter fw = new java.io.FileWriter(fc.getSelectedFile())) {
                fw.write(txtEditor.getText());
                javax.swing.JOptionPane.showMessageDialog(this, "Archivo .pz guardado exitosamente.");
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
            }
        }
    }

    // Método para Guardar Prompt (.md o .txt)
    private void guardarPromptMD() {
        if (txtConsola.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay ningún prompt generado para guardar.");
            return;
        }

        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();

        javax.swing.filechooser.FileNameExtensionFilter filterMd
                = new javax.swing.filechooser.FileNameExtensionFilter("Archivo Markdown (*.md)", "md");
        javax.swing.filechooser.FileNameExtensionFilter filterTxt
                = new javax.swing.filechooser.FileNameExtensionFilter("Archivo de Texto (*.txt)", "txt");

        fc.addChoosableFileFilter(filterMd);
        fc.addChoosableFileFilter(filterTxt);

        if (fc.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fc.getSelectedFile();
            String ruta = archivo.getAbsolutePath();

            // Si el usuario no escribió extensión, se le asigna la seleccionada en el filtro
            if (!ruta.endsWith(".md") && !ruta.endsWith(".txt")) {
                if (fc.getFileFilter() == filterTxt) {
                    archivo = new java.io.File(ruta + ".txt");
                } else {
                    archivo = new java.io.File(ruta + ".md");
                }
            }

            try (java.io.FileWriter fw = new java.io.FileWriter(archivo)) {
                fw.write(txtConsola.getText());
                javax.swing.JOptionPane.showMessageDialog(this, "Prompt guardado exitosamente en: " + archivo.getName());
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar el prompt: " + e.getMessage());
            }
        }
    }

    // Metodo Auxiliar para Exportar Reporte HTML
    private void exportarReporte(String tipo) {
        if (ultimoLexer == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debes compilar un programa antes de generar los reportes.");
            return;
        }
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setSelectedFile(new java.io.File("reporte_" + tipo + ".html"));
        if (fc.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fc.getSelectedFile().getAbsolutePath();
                if (tipo.equals("errores")) {
                    Reportes.generarReporteErrores(ultimoLexer.getErrores(), ultimoParser.getErrores(), ultimoSemantico.getErrores(), ruta);
                } else if (tipo.equals("simbolos")) {
                    Reportes.generarReporteSimbolos(ultimoParser.getTablaSimbolos(), ruta);
                } else if (tipo.equals("ast")) {
                    Reportes.generarReporteAST(ultimoParser.getPrograma(), ruta);
                }
                javax.swing.JOptionPane.showMessageDialog(this, "Reporte HTML generado en: " + ruta);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al crear el reporte: " + e.getMessage());
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Consola;
    private javax.swing.JPanel Errores;
    private javax.swing.JPanel Simbolos;
    private javax.swing.JMenuItem itemAbrir;
    private javax.swing.JMenuItem itemGuardar;
    private javax.swing.JMenuItem itemGuardarPrompt;
    private javax.swing.JMenuItem itemRepAST;
    private javax.swing.JMenuItem itemRepErrores;
    private javax.swing.JMenuItem itemRepSimbolos;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JScrollPane tblError;
    private javax.swing.JTable tblErrores;
    private javax.swing.JTable tblSimbolo;
    private javax.swing.JScrollPane tblSimbolos;
    private javax.swing.JTextArea txtConsola;
    private javax.swing.JTextPane txtEditor;
    // End of variables declaration//GEN-END:variables
}
