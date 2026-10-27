/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelos;

/**
 *
 * @author fernan
 */
public enum TipoToken {
   
    // Directivas
    DIRECTIVA,
    // Palabras reservadas de estructura
    AGENTE,
    CONTEXTO,
    VARIABLE,
    EJECUTAR,
    EXPORTAR,
    // Comandos de IA
    COMANDO_IA,
    // Función del sistema
    CARGAR,
    // Conectores
    CONECTOR,
    // Operadores
    IGUAL,
    MAS,
    FLECHA,
    // Delimitadores
    LLAVE_A,
    LLAVE_C,
    PAR_A,
    PAR_C,
    COMA,
    // Literales e identificadores
    ID,
    CADENA,
    NUMERO,
    // Fin  del archivo
    EOF
}
