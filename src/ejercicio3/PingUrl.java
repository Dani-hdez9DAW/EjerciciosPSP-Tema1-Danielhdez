package ejercicio3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PingUrl {
    private static final int NUMERO_PING = 4;
    private static Scanner sc;

    private static final String URL_REGEX =
            "^(https?://)?" +
                    "([\\w\\-]+\\.)+[\\w\\-]{2,}" +
                    "(:\\d+)?(/.*)?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public static boolean urlValida(String url) {
        return URL_PATTERN.matcher(url).matches();
    }

    private static void listadoUrl() {
        System.out.println("Ejemplos de URL:");
        System.out.println("-----------------");
        System.out.println("1. www.google.com");
        System.out.println("2. www.facebook.com");
        System.out.println("3. www.youtube.com");
        System.out.println("4. www.twitter.com");
        System.out.println("5. www.instagram.com");
        System.out.println("-----------------");
    }

    private static String comandosRed() {
        System.out.println("Seleccione un comando:");
        System.out.println("-----------");
        System.out.println("1. Ping");
        System.out.println("2. Ipconfig");
        System.out.println("3. Tracert www.facebook.com");
        System.out.println("4. Netstat -a");
        System.out.println("5. Salir");
        System.out.println("------------");

        try {
            int comandoElegido = Integer.parseInt(sc.nextLine());
            switch (comandoElegido) {
                case 1:
                    return "ping";
                case 2:
                    return "ipconfig";
                case 3:
                    return "tracert www.facebook.com";
                case 4:
                    return "netstat -a";
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Comando no válido, intente de nuevo.");
                    return comandosRed();
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida, intente de nuevo.");
            return comandosRed();
        }
    }

    private static void hacerPing(String comando) {
        listadoUrl();
        System.out.println("Escriba una URL de la lista o una personalizada:");
        String url = sc.nextLine();
        while (!urlValida(url)) {
            System.out.println("URL no válida, intente de nuevo:");
            url = sc.nextLine();
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(comando, url);
            Process proceso = builder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                int contador = 0;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                    contador++;
                    if (contador == NUMERO_PING) {
                        break;
                    }
                }
            }
            int exitCode = proceso.waitFor();
            System.out.println("Código de salida: " + exitCode);
        } catch (SecurityException e) {
            System.out.println("No se tiene permiso para ejecutar el comando de ping.");
        } catch (Exception e) {
            System.out.println("Se ha producido un error: " + e.getMessage());
        }
    }

    private static String obtenerSo() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "ping";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return "ping"; // Para UNIX/Linux/MacOS el comando es "ping".
        } else {
            return ""; // Sistema operativo no soportado.
        }
    }

    private static void ejecutarComando(String comando) {
        try {
            ProcessBuilder builder = new ProcessBuilder(comando.split(" "));
            Process proceso = builder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }
            int exitCode = proceso.waitFor();
            System.out.println("Código de salida: " + exitCode);
        } catch (Exception e) {
            System.out.println("Error al ejecutar el comando: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        String comando = obtenerSo();
        if (comando.equals("")) {
            System.out.println("Sistema operativo no soportado.");
            System.exit(1);
        } else {
            while (true) {
                String comandoRed = comandosRed();
                if (comandoRed.equals("ping")) {
                    hacerPing(comando);
                } else {
                    ejecutarComando(comandoRed);
                }
            }
        }
    }
}