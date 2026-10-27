/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.practica1lenguajes;

import Analizador.AnalizadorLexico;
import Interfaz.IDEPrincipal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import modelos.Reportes;

/**
 *
 * @author fernan
 */
public class Practica1Lenguajes {

    public static void main(String[] args) {
        
         java.awt.EventQueue.invokeLater(() -> {

            IDEPrincipal  ventana = new IDEPrincipal();

            ventana.setVisible(true);
        });
    }
}
