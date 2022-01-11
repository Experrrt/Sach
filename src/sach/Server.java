package sach;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import sach.Figurky.Figurka;
import java.io.*;
import java.io.IOException;
import sach.Enums.VyhraEnum;

public class Server implements Hrac, Runnable {
    ServerSocket ss;
    Socket s;
    InputStream in;
    OutputStream out;
    ObjectInputStream objInStream;
    ObjectOutputStream objOutSteam;
    boolean update = false;
    ServerSprava sSparava;

    public void start() throws IOException, ClassNotFoundException {
        ss = new ServerSocket(4999);

        System.out.println("awaiting connection");

        s = ss.accept();

        System.out.println("Connected " + s);

        in = s.getInputStream();
        objInStream = new ObjectInputStream(in);
        out = s.getOutputStream();
        objOutSteam = new ObjectOutputStream(out);
        new Thread(this).start();
    }

    public void posliPole(Figurka[][] pole, ArrayList<Figurka> vypadnuteFigurky, VyhraEnum vysledokHry) {
        try {
            objOutSteam.writeObject(new ServerSprava(pole, vypadnuteFigurky, vysledokHry));
        } catch (Exception e) {
            System.out.println("Nepodarilo sa poslat ani moc od serveru");
            e.printStackTrace();
        }
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public Figurka spravTah(Figurka[][] hraciePole, Vec2 newS, ArrayList<Figurka> figurkyNaPoly,
            ArrayList<Figurka> vypadnuteFigurky, VyhraEnum vysledokHry) {
        posliPole(hraciePole, vypadnuteFigurky, vysledokHry);
        return null;
    }

    public ServerSprava novePole() {
        if (update) {
            update = false;
            return sSparava;
        }
        sSparava = null;
        return null;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    this.wait();
                    this.sSparava = (ServerSprava) objInStream.readObject();
                    update = true;
                }
            }
            // System.out.println(ano);
        } catch (Exception e) {
        }
    }
}
