package ejercicio2;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

//@Author: Daniel Hernandez
//Ejercicio 2 b)(Abrir desde EjerciciosPSP-Tema1-Danielhdez,para que el fichero se guarde en la carpeta ficheros)
public class InstalledProgramsToFile {
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

        try (FileWriter fr = new FileWriter("Ficheros/ejecutables.txt");
             PrintWriter pw = new PrintWriter(fr)) {
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.canExecute()) {
                        pw.println(file.getName() + " - " + file.getAbsolutePath());
                    }
                }
                System.out.println("Se ha creado el fichero ejecutables.txt con los ejecutables del directorio.");
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