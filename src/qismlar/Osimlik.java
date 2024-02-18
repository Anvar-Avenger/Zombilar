package qismlar;

import javafx.scene.image.ImageView;
import qismlar.qoidalar.IOsimlik;

public abstract class Osimlik extends ImageView implements IOsimlik {

    private int x, y;

    public void nuqtaX(int x) {
        this.x = x;
    }

    public void nuqtaY(int y) {
        this.y = y;
    }

    public int nuqtaX() {
        return x;
    }

    public int nuqtaY() {
        return y;
    }
}
