package qismlar;

import javafx.scene.image.ImageView;


public class Yol {

    // int usti = 108, pasti = 35, chap = 320, ong = 146;
    // int eni = 900, boyi = 625;

    private final int uzunlik = 100;
    protected final int balandlik = 90;

    protected final int qatorlar = 5;
    private final int ustunlar = 9;

    protected int[] x = {320, 420, 520, 620, 725, 825, 930, 1030, 1130};
    protected int[] y = {110, 235, 360, 485, 610};
    protected int[] kx = x;

    private final boolean[][] ishlatildi = new boolean[x.length][y.length];

    public Yol() {
        for (int i = 0; i < ustunlar; i++) {
            kx[i] = x[i] + (int) (uzunlik * 0.25);
        }
    }

    public boolean joyBor(ImageView osimlik) {
        for (int i = 0; i < ustunlar; i++) {
            for (int j = 0; j < qatorlar; j++) {
                if (!ishlatildi[i][j] &&
                        osimlik.getTranslateX() + osimlik.getFitWidth() / 2 > x[i] &&
                        osimlik.getTranslateX() + osimlik.getFitWidth() / 2 < x[i] + uzunlik &&
                        osimlik.getTranslateY() + osimlik.getFitHeight() / 2 > y[j] &&
                        osimlik.getTranslateY() + osimlik.getFitHeight() / 2 < y[j] + balandlik) {

                    osimlik.setTranslateX(kx[i]);
                    osimlik.setTranslateY(y[j]);

                    ishlatildi[i][j] = true;
                    return true;
                }
            }
        }

        return false;
    }
}
