package sach;

import java.util.Scanner;
import java.util.zip.ZipEntry;
import sach.Gui;

public class Hra {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String inpu = sc.nextLine();

        if (inpu.equals("s")) {
            new Gui("S").spustiGui();
        } else {
            new Gui("C").spustiGui();
        }

    }
}

// class Hra3 implements Runnable {
// Thread t = new Thread(this);

// @Override
// public void run() {
// new Gui("C").spustiGui();

// }
// }

// class Hra2 implements Runnable {
// Thread t = new Thread(this);

// @Override
// public void run() {
// new Gui("S").spustiGui();

// }
// }
