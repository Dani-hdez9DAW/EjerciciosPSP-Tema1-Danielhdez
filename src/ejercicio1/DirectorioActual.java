package ejercicio1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//@Author: Daniel Hernandez
//Ejercicio 1 a)Directorio actual
public class DirectorioActual {
    private static String[] obtenerSo() {
        String[] comand;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            comand = new String[]{"cmd.exe", "/c", "cd"};
        } else {
            comand = new String[]{"sh", "-c", "pwd"};
        }
        return comand;
    }

    private static void directorioActual(String[] comand) {
        Process proceso = null;
        try {
            ProcessBuilder builder = new ProcessBuilder(comand);
            proceso = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Directorio actual: " + linea);
            }
            int exitCode = proceso.waitFor();
            System.out.println("Codigo de salida: " + exitCode);
        } catch (Exception e) {
            if (proceso != null) {
                System.out.println("Error: " + proceso.exitValue());
            } else {
                System.out.println("Error: No se pudo iniciar el proceso.");
            }
        }
    }

    public static void main(String[] args) {
        String[] comand = obtenerSo();
        if (comand != null) {
            directorioActual(comand);
        }
    }
}