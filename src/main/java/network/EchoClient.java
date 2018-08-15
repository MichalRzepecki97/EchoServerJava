package network;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) {

        Exception e = null;
        try (Socket socket = new Socket(InetAddress.getByName("localhost"), 8080)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)) {
                System.out.println("Odczytanie wszystkiego co serwer nam prześle");

                System.out.println("SERVER> " + reader.readLine());
                System.out.println("SERVER> " + reader.readLine());
                Scanner scanner = new Scanner(System.in, "UTF-8");
                String line = null;
                do {
                    System.out.println("JA> ");
                    line = scanner.nextLine();
                    writer.println(line);
                    System.out.println("SERVER> " + reader.readLine());
                } while (line != null && !line.equalsIgnoreCase("KONIEC"));
            }
        }
        catch (ConnectException ce) {
            System.err.println("Błąd połączenia z serwerem!");
            e = ce;
        }
        catch (SocketException se) {
            System.err.println("Błąd komunikacji sieciowe");
            e = se;
        }
        catch (IOException ioe) {
            System.err.println("Błąd systemu wejścia/wyjścia");
            e = ioe;
        }

        if (e != null) {
            e.printStackTrace();
        }

    }
}
