/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.practica1lenguajes;

import Analizador.AnalizadorLexico;
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
        Scanner scanner = new Scanner(System.in);

        int opcion = 0;

        do {

            System.out.println("=========================");
            System.out.println("   ANALIZADOR PROMPTZAL  ");
            System.out.println("=========================");
            System.out.println("1. Analizar archivo .pz");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opcion: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println();
                System.out.println(
                        "Debe ingresar una opcion numerica."
                );
                scanner.nextLine();
                continue;
            }

            if (opcion == 1) {

                System.out.print("Ingrese la ruta del archivo .pz: ");
                String ruta = scanner.nextLine();

                if (!ruta.endsWith(".pz")) {

                    System.out.println();
                    System.out.println(
                            "El archivo debe tener extension .pz"
                    );

                    continue;
                }
                
                
                File archivo = new File(ruta);

                if (!archivo.exists()) {

                    System.out.println();
                    System.out.println(
                            "El archivo no existe."
                    );

                    continue;
                }

                if (!archivo.isFile()) {

                    System.out.println();
                    System.out.println(
                            "La ruta indicada no corresponde a un archivo."
                    );

                    continue;
                }


                String entrada = "";

                try {

                    BufferedReader lector = new BufferedReader(
                            new FileReader(ruta)
                    );

                    String linea;

                    while ((linea = lector.readLine()) != null) {

                        entrada += linea;
                        entrada += "\n";
                    }

                    lector.close();

                    System.out.println();
                    System.out.println(
                            "Archivo cargado correctamente."
                    );

                } catch (IOException e) {

                    System.out.println();
                    System.out.println(
                            "No se pudo leer el archivo."
                    );

                    continue;
                }

                AnalizadorLexico analizador
                        = new AnalizadorLexico(entrada);

                analizador.analizar();

                System.out.println();
                System.out.println("===== TOKENS =====");

                analizador.mostrarTokens();

                System.out.println();
                System.out.println("===== ERRORES =====");

                analizador.mostrarErrores();

                Reportes reporte = new Reportes();

                reporte.generarReporteTokens(
                        analizador.getTokens()
                );

                reporte.generarReporteErrores(
                        analizador.getErrores()
                );

                System.out.println();
                System.out.println(
                        "Reportes generados correctamente."
                );

            } else if (opcion == 2) {

                System.out.println();
                System.out.println("Saliendo del Analizador PromptZal...");

            } else {

                System.out.println();
                System.out.println("Opcion no valida.");
            }

        } while (opcion != 2);

        scanner.close();
    }
}
