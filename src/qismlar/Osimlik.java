package qismlar;

import javafx.scene.image.ImageView;

public class Osimlik extends ImageView {

    int x, y;

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
