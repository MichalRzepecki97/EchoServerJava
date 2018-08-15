package network;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class EchoRunnable implements Runnable {

    private final Socket client;

    public EchoRunnable(Socket client) {
        this.client = client;
    }

    @Override

    public void run() {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter
                        (client.getOutputStream(), "UTF-8"), true);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(
                             client.getInputStream(), "UTF-8"))) {
            System.out.println("Połączono z klientem " + client.getInetAddress().getHostAddress());
            writer.println("Witaj w serwerze ECHO\nWpisz KONIEC aby zakończyć");
            String clientLine = null;
            do {
                clientLine = reader.readLine();
                System.out.println("Od klienta: " + clientLine);
                writer.println(clientLine);
            } while (clientLine != null && !clientLine.equalsIgnoreCase("KONIEC"));
            System.out.println("Zakończono połączenie z klientem " + client.getInetAddress().getHostAddress());
        }
        catch (SocketException se) {
            System.err.println("Błąd połączenia z klientem");
            se.printStackTrace();
        }
        catch (IOException ioe) {
            System.err.println("Błąd systemu wejścia/wyjścia w obsłudze klienta");
            ioe.printStackTrace();
        }
        finally {
            if (this.client != null) {
                if (!this.client.isClosed()) {
                    try {
                        this.client.close();
                        System.out.println("Zamknięto klienta " + this.client.getInetAddress());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
