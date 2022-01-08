package sach;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import sach.Enums.VyhraEnum;

public class Gui extends JPanel implements Runnable {
    private JFrame jFrame = new JFrame();
    private HraciePole hraciePole;
    private MainMenuGui mainMenu;
    private Thread GuiThread = new Thread(this);
    private int offSetY = 150, offSetX = 0, vyska = 800, sirka = 800;

    public Gui(String cau) {
        // mainMenu = new MainMenuGui(sirka, vyska,
        // offSetX, offSetY);
        if (cau == "S") {
            hraciePole = new HraciePoleGui(new ArrayList<>(Arrays.asList(new Server())), sirka, vyska, offSetX,
                    offSetY);
        } else {
            hraciePole = new HraciePoleGui(new ArrayList<>(Arrays.asList(new InyClient())), sirka, vyska, offSetX,
                    offSetY);
        }

        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setPreferredSize(new Dimension(2 * offSetX + sirka, 2 * offSetY + vyska - 50));

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mainMenu != null) {
                    mainMenu.skontrolujHover(new Vec2(e.getX(), e.getY()));
                }
                try {
                    Thread.sleep(50L);
                } catch (Exception ee) {
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (hraciePole != null) {
                    hraciePole.skontrolujKliknutie((e.getX() - offSetX) / 100,
                            (e.getY() - offSetY) / 100);
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (hraciePole != null) {
                    hraciePole.skontrolujOdkliknutie((e.getX() - offSetX) / 100, (e.getY() - offSetY) / 100);
                } else {
                    hraciePole = mainMenu
                            .skontrolujOdkliknutie(new Vec2(e.getX(), e.getY()));
                    spustiHru();
                }
                // repaint();
            }

        });
        jFrame.setResizable(false);
        jFrame.add(this, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle(cau);
    }

    public void spustiGui() {
        jFrame.pack();
        jFrame.setVisible(true);
        GuiThread.start();
    }

    private void spustiHru() {
        if (hraciePole != null) {
            hraciePole.spravTah();
            hraciePole.spustiStopky();
        }
    }

    @Override
    public void run() {
        do {
            repaint();
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
                System.out.println("Neni dobre");
            }
        } while (mainMenu != null || (hraciePole != null && hraciePole.vysledokHry == VyhraEnum.NESKONCILA));
        // repaint();
        try {
            GuiThread.join();
        } catch (Exception e) {
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
        if (hraciePole != null) {
            hraciePole.paint(g2d);
        } else {
            mainMenu.paint(g2d);
        }
    }
}