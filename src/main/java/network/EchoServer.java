package network;

import java.net.InetAddress;
import java.net.ServerSocket;

import java.io.*;
import java.net.*;

public class EchoServer {


    public static void main(String[] args) {

        Exception e = null;
        try (ServerSocket server = new ServerSocket(8080, 0, InetAddress.getByName("localhost"))) {
            System.out.println("Utworzono gniazdo sieciowe typu serwer");
            while (true) {
                Socket client = server.accept();
                //EchoRunnable r = new EchoRunnable();
                //r.setClient(client);
                Thread t = new Thread(new EchoRunnable(client));
                t.start();
                //Thread.sleep(500);
                //r.setClient(null);
            }
        }
        catch (BindException be) {
            System.err.println("Błąd utworzenia gniazda sieciowego typu serwer. Nie można przypiąć do adresu/portu");
            e = be;
        }
        catch (UnknownHostException uhe) {
            System.err.println("Błąd utworzenia gniazda sieciowego typu serwer. Nie można rozpoznać nazwy hosta");
            e = uhe;
        }
        catch (SocketException se) {
            System.err.println("Błąd połączenia z gniazdem sieciowym");
            e = se;
        }
        catch (IOException ioe) {
            System.err.println("Błąd systemu wejścia/wyjścia");
            e = ioe;
        }

        if (e != null) {
            e.printStackTrace();
            System.err.println("Wystąpił wyjątek, więc będziemy kończyć...");
        }

    }
}

