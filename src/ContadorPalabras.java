import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ContadorPalabras {
    public static void main(String[] args) {
        // Validar que se reciba exactamente un argumento
        if (args.length != 1) {
            System.out.println("Uso: java ContadorPalabras <archivo>");
            return;
        }

        // Paso 6: Trabajar con Path
        Path archivo = Path.of(args[0]);
        System.out.println("Archivo: " + archivo.getFileName());
        System.out.println("Ruta: " + archivo.toAbsolutePath());

        // Paso 7: Verificar el archivo con Files.exists()
        if (!Files.exists(archivo)) {
            System.err.println("El archivo no existe: " + archivo);
            return;
        }

        System.out.println("El archivo existe y está listo para ser procesado.");

        // Parte VI - Paso 11: Inicializar el Map para guardar las frecuencias
        Map<String, Integer> frecuencias = new HashMap<>();

        // Parte IV y V: Lectura, normalización y separación de palabras
        try (BufferedReader lector = Files.newBufferedReader(archivo)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Paso 10: Normalización (minúsculas y eliminación de signos)
                linea = linea.toLowerCase();
                linea = linea.replaceAll(
                        "[^\\p{L}\\p{N}\\s]",
                        ""
                );

                // Paso 9: Separar palabras por espacios en blanco
                String[] palabras = linea.trim().split("\\s+");

                for (String palabra : palabras) {
                    if (!palabra.isEmpty()) {
                        // Parte VI - Paso 12: Actualizar frecuencias con getOrDefault
                        frecuencias.put(
                                palabra,
                                frecuencias.getOrDefault(palabra, 0) + 1
                        );
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        // Mostrar las frecuencias contabilizadas
        System.out.println("\n--- RESULTADO DE FRECUENCIAS ---");
        for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
    }
}