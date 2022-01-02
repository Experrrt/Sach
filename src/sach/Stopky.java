package sach;

public class Stopky implements Runnable {
    int cas = 0;
    private Boolean running = false;
    private Thread t;

    public Stopky() {
        t = new Thread(this);
    }

    @Override
    public void run() {
        while (running) {
            // System.out.print("\rTime: " + cas);
            cas++;
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                System.out.println("Neni dobre");
            }
        }
    }

    public String getFormatovanyCas() {
        int minuty = cas / 60;
        int sekundy = cas % 60;
        return ((minuty < 10) ? "0" : "") + (minuty) + ((sekundy < 10) ? " : 0" : " : ") + (sekundy);
    }

    public void start() {
        t.start();
        running = true;
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public void stop() {
        running = false;
        try {
            t.join();
        } catch (Exception e) {

        }
    }
}
