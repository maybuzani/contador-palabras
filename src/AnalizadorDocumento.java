import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AnalizadorDocumento {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java AnalizadorDocumento <archivo>");
            return;
        }

        Path archivo = Path.of(args[0]);
        if (!Files.exists(archivo)) {
            System.err.println("El archivo no existe: " + archivo);
            return;
        }
        Map<String, Integer> frecuencias = new HashMap<>();
        int totalLineas = 0;
        int totalPalabras = 0;

        try (BufferedReader lector = Files.newBufferedReader(archivo)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                totalLineas++;

                linea = linea.toLowerCase();
                linea = linea.replaceAll("[^\\p{L}\\p{N}\\s]", "");

                if (linea.isBlank()) {
                    continue;
                }

                String[] palabras = linea.trim().split("\\s+");
                for (String palabra : palabras) {
                    if (!palabra.isEmpty()) {
                        frecuencias.put(
                                palabra,
                                frecuencias.getOrDefault(palabra, 0) + 1
                        );
                        totalPalabras++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
            return;
        }
        Map<String, Integer> ordenadas = new TreeMap<>(frecuencias);

        String palabraMasFrecuente = "";
        int frecuenciaMaxima = 0;
        for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
            if (entrada.getValue() > frecuenciaMaxima) {
                palabraMasFrecuente = entrada.getKey();
                frecuenciaMaxima = entrada.getValue();
            }
        }
        try {
            Path archivoSalida = Path.of("reporte-documento.txt");

            try (PrintWriter escritor = new PrintWriter(Files.newBufferedWriter(archivoSalida))) {
                escritor.println("Analisis del docuemnto");
                escritor.println("======================");
                escritor.println("Archivo: " + archivo.getFileName());
                escritor.println("Total de líneas: " + totalLineas);
                escritor.println("Total de palabras: " + totalPalabras);
                escritor.println("Palabras diferentes: " + frecuencias.size());
                escritor.println("Palabra más frecuente: " + palabraMasFrecuente + " (" + frecuenciaMaxima + ")");
                escritor.println();
                escritor.println("Frecuencia de palabras");
                escritor.println("----------------------");

                for (Map.Entry<String, Integer> entrada : ordenadas.entrySet()) {
                    escritor.printf(
                            "%-20s %d%n",
                            entrada.getKey(),
                            entrada.getValue()
                    );
                }
            }
            System.out.println("Reporte generado exitosamente en: " + archivoSalida.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}