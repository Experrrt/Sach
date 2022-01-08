package sach;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class NacitavacObrazkov {
    public BufferedImage nacitajObrazok(String menoObrazku) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass()
                    .getResourceAsStream("/sach/images/" + menoObrazku));

        } catch (Exception e) {
            System.out.println("Image failed to laod");
        }
        return image;
    }
}
