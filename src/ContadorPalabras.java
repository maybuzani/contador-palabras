import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

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
                        System.out.println(palabra);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}