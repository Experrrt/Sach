package sach.Figurky;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import sach.Vec2;
import java.lang.Math;

public class Figurka {
    public int x, y, team, hodnota;
    public String typ;
    public BufferedImage obrazokFigurkyC, obrazokFigurkyB;
    public ArrayList<Vec2> deathZone;
    protected ArrayList<int[]> kombinacie;
    public Boolean maMat;
    public ArrayList<Vec2> pesiakPohyb;

    public Figurka(int x, int y, int team) {
        this.y = y;
        this.x = x;
        this.team = team;
        deathZone = new ArrayList<Vec2>();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean skontrolujPohyb(int newX, int newY, Figurka[][] hraciePole) {
        for (int i = 0; i < deathZone.size(); i++) {
            if (deathZone.get(i).getY() == newY && deathZone.get(i).getX() == newX) {
                return true;
            }
        }
        return false;
    }

    public void vypocitajDeathZone(Figurka[][] hraciePole) {
    }

    protected final BufferedImage nacitajObrazok(String menoObrazku) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass()
                    .getResourceAsStream("/sach/images/" + menoObrazku));

        } catch (Exception e) {
            System.out.println("Image failed to laod");

        }
        return image;
    }

    protected final ArrayList<int[]> generateKomb(int[] komb, boolean opakujuSa) {
        ArrayList<int[]> arrayList = new ArrayList<int[]>();
        for (int i = 0; i < komb.length; i++) {
            for (int j = 0; j < komb.length; j++) {
                if (!opakujuSa && Math.abs(komb[i]) == Math.abs(komb[j])) {
                    continue;
                }
                int[] combination = { komb[i], komb[j] };
                arrayList.add(combination);
            }
        }
        return arrayList;

    }
}
