package sach;

import java.net.Socket;
import java.util.ArrayList;

import sach.Figurky.Figurka;

import java.io.*;

public class InyClient implements Hrac, Runnable {
    Socket s;
    OutputStream outputStream;
    InputStream in;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    boolean cakaj = true;
    Figurka[][] pole;
    HraciePole daco;

    public void zapniClienta(HraciePole celepole) throws IOException, ClassNotFoundException {
        s = new Socket("localhost", 4999);

        outputStream = s.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        in = s.getInputStream();
        objectInputStream = new ObjectInputStream(in);
        new Thread(this).start();
        // int[] cisla = { 1, 2, 3, 4, 5 };
        daco = celepole;

        // objectOutputStream.writeObject(cisla);
        // cakajnanieco();
    }

    public void posliPole(Figurka[][] hraciePole, HraciePole celepole) {
        daco = celepole;
        if (cakaj)
            return;
        try {
            objectOutputStream.writeObject(hraciePole);
        } catch (Exception e) {
            System.out.println("Nepodarilo sa poslat ani moc");
        }
        cakaj = true;
        new Thread(this).start();
    }

    @Override
    public Figurka spravTah(Figurka[][] hraciePole, Vec2 newS, ArrayList<Figurka> figurkyNaPoly) {
        // TODO Auto-generated method stub
        return null;
    }

    public Figurka[][] getNovePole() {
        return pole;
    }

    @Override
    public void run() {
        if (cakaj) {
            try {
                pole = (Figurka[][]) objectInputStream.readObject();
                synchronized (daco) {
                    daco.notify();
                }
            } catch (Exception e) {
            }
            cakaj = false;
        }
    }
}