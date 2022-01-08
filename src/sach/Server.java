package sach;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import sach.Figurky.Figurka;
import java.io.*;
import java.io.IOException;

public class Server implements Hrac, Runnable {
    ServerSocket ss;
    Socket s;
    InputStream in;
    OutputStream out;
    ObjectInputStream objInStream;
    ObjectOutputStream objOutSteam;
    boolean cakaj = false;
    Thread t = new Thread(this);
    private static HraciePole daco;
    Figurka[][] pole;

    public void startServer(HraciePole obj) throws IOException, ClassNotFoundException {
        ss = new ServerSocket(4999);

        System.out.println("awaiting connection");

        s = ss.accept();

        System.out.println("Connected " + s);

        in = s.getInputStream();
        objInStream = new ObjectInputStream(in);
        out = s.getOutputStream();
        objOutSteam = new ObjectOutputStream(out);
        t.start();
        daco = obj;
        // while (true) {
        // String ano = (String) objectInputStream.readObject();
        // System.out.println(ano);
        // }

        // int[] cisla = { 1, 2, 3, 4 };

        // int[] cisla = (int[]) objInStream.readObject();

        // for (int i : cisla) {
        // System.out.print(i + " ");
        // }
    }

    public void serverPosliPole(Figurka[][] pole) {
        this.pole = pole;
        if (cakaj)
            return;
        try {
            objOutSteam.writeObject(pole);
        } catch (Exception e) {
            System.out.println("Nepodarilo sa poslat ani moc od serveru");
            e.printStackTrace();
        }
        cakaj = true;
        new Thread(this).start();
    }

    @Override
    public Figurka spravTah(Figurka[][] hraciePole, Vec2 newS, ArrayList<Figurka> figurkyNaPoly) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void run() {
        if (cakaj) {
            try {
                pole = (Figurka[][]) objInStream.readObject();
                // System.out.println(ano);
                synchronized (pole) {
                    pole.notify();
                    pole = new Figurka[8][8];
                }
            } catch (Exception e) {
            }
            cakaj = false;
        }

    }
}
