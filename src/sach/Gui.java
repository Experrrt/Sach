package sach;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Gui extends JPanel {
    JFrame jFrame;
    private BufferedImage image;

    // private Figurka[][] hraciePole;

    HraciePole hraciePole = new HraciePole();

    public Gui() {
        jFrame = new JFrame();

        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        setPreferredSize(new Dimension(800, 800));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                hraciePole.skontrolujKliknutie(e);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                hraciePole.premiestniFigurku(e);
                repaint();
            }

        });
        jFrame.add(this, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Števo Šarišký");
        jFrame.pack();
        jFrame.setVisible(true);
        // loadImage();
        repaint();
    }

    // public void loadImage() {
    // try {
    // image = ImageIO.read(getClass()
    // .getResourceAsStream("/Chessboard Background.png"));

    // } catch (Exception e) {
    // System.out.println("Image failed to laod");

    // }

    // }

    // public void repaintniPole(Figurka[][] pole) {
    // hraciePole = pole;
    // repaint();
    // }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
        for (int i = 0; i < hraciePole.pole.length; i++) {
            for (int j = 0; j < hraciePole.pole[i].length; j++) {
                if (hraciePole.pole[i][j] == null) {
                    if ((i + j) % 2 != 0) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                } else {
                    g.setColor(hraciePole.pole[i][j].color);
                }
                g.fillRect(j * 100, i * 100, 100, 100);
            }
        }
    }
}