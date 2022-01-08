package sach;

import sach.Figurky.*;
import java.util.ArrayList;

public interface Hrac {
    public abstract Figurka spravTah(Figurka[][] hraciePole, Vec2 newS, ArrayList<Figurka> figurkyNaPoly);
}
