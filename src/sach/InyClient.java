package sach;

import java.net.Socket;
import java.util.ArrayList;

import sach.Figurky.Figurka;
import sach.Enums.VyhraEnum;
import java.io.*;

public class InyClient implements Hrac, Runnable {
    Socket s;
    OutputStream outputStream;
    InputStream in;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    boolean update = false;
    ServerSprava sSparava;

    public void start() throws IOException, ClassNotFoundException {
        s = new Socket("localhost", 4999);

        outputStream = s.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        in = s.getInputStream();
        objectInputStream = new ObjectInputStream(in);
        new Thread(this).start();
    }

    public void posliPole(Figurka[][] hraciePole, ArrayList<Figurka> vypadnuteFigurky, VyhraEnum vysledokHry) {
        try {
            objectOutputStream.writeObject(new ServerSprava(hraciePole, vypadnuteFigurky, vysledokHry));
        } catch (Exception e) {
            System.out.println("Nepodarilo sa poslat ani moc");
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
        } else {
            return null;
        }
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    if (sSparava != null) {
                        this.wait();
                    }
                    sSparava = (ServerSprava) objectInputStream.readObject();
                    update = true;
                }
            }
        } catch (

        Exception e) {
        }
    }
}
