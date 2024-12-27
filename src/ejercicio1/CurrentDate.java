package ejercicio1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
//@Author: Daniel Hernandez
//Ejercicio 1 c)La fecha de hoy
public class CurrentDate {
    private static String[] obtenerSo() {
        String[] comand;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            comand = new String[]{"cmd", "/c", "date /t"};
        } else {
            comand = new String[]{"date"};
        }
        return comand;
    }

    private static void fechaActual(String[] comand) {

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(comand);
            Process proceso = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
            String lineaErrores;
            while ((lineaErrores = errorReader.readLine()) != null) {
                System.out.println(lineaErrores);
            }
            int processExitCode = proceso.exitValue();
            System.out.println("Codigo de salida: " + processExitCode);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        String[] comand = obtenerSo();
        if (comand != null) {
            fechaActual(comand);
        }

    }
}
