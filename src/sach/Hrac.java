package sach;

import sach.Figurky.*;
import java.util.ArrayList;
import sach.Enums.VyhraEnum;

public interface Hrac {
    public abstract Figurka spravTah(Figurka[][] hraciePole, Vec2 newS, ArrayList<Figurka> figurkyNaPoly,
            ArrayList<Figurka> vypadnuteFigurky, VyhraEnum vysledokHry);
}
