import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ContadorPalabras {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java ContadorPalabras <archivo>");
            return;
        }

        Path archivo = Path.of(args[0]);
        System.out.println("Archivo: " + archivo.getFileName());
        System.out.println("Ruta: " + archivo.toAbsolutePath());

        if (!Files.exists(archivo)) {
            System.err.println("El archivo no existe: " + archivo);
            return;
        }

        System.out.println("El archivo existe y está listo para ser procesado.");

        Map<String, Integer> frecuencias = new HashMap<>();

        try (BufferedReader lector = Files.newBufferedReader(archivo)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.toLowerCase();
                linea = linea.replaceAll("[^\\p{L}\\p{N}\\s]", "");

                if (linea.isBlank()) {
                    continue;
                }

                String[] palabras = linea.trim().split("\\s+");
                for (String palabra : palabras) {
                    frecuencias.put(
                            palabra,
                            frecuencias.getOrDefault(palabra, 0) + 1
                    );
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
        }

        System.out.println("\n-RESULTADOS OBTENIDOS-");
        for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
    }
}