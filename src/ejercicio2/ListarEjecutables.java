package ejercicio2;

import java.io.File;

//@Author: Daniel Hernandez
//Ejercicio 2 a)ejecutables que estan instalados.
public class ListarEjecutables {
    private static String obtenerSo() {
        String directorio;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            directorio = "C:\\Program Files";
        } else {
            directorio = "/bin";
        }
        return directorio;
    }

    private static void listarEjecutables(String directorio) {


        File folder = new File(directorio);
        File[] listOfFiles = folder.listFiles();
        int codigoSalida = 0;

        try {
            if (listOfFiles != null) {
                System.out.println("Ejecutables encontrados:");
                int contador = 0;
                for (File file : listOfFiles) {
                    if (file.canExecute()) {
                        System.out.println(file.getName() + " - " + file.getAbsolutePath());
                        contador++;
                    }
                }
                System.out.println("Total de ejecutables encontrados: " + contador);
            } else {
                System.out.println("El directorio está vacío o no se pudo acceder.");
                codigoSalida = 1;
            }
        } catch (SecurityException e) {
            System.out.println("No se tiene permiso para acceder al directorio.");
            codigoSalida = 2;
        } catch (Exception e) {
            System.out.println("Se ha producido un error: " + e.getMessage());
            codigoSalida = 3;
        } finally {
            System.out.println("Código de salida: " + codigoSalida);
        }
    }

    public static void main(String[] args) {
        String sistemaOperativo = obtenerSo();
        if (sistemaOperativo != null) {
            listarEjecutables(sistemaOperativo);
        } else {
            System.out.println("No se pudo determinar el sistema operativo.");
        }
    }
}
