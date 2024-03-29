package qismlar;

public class Yol {

    public final int uzunlik = 100; // int top (usti) = 108, bottom (pasti) = 35, chap = 320, ong = 146;
    public final int balandlik = 90; // int height = 900, width = 625;

    private final int ustunlar;
    private final int qatorlar;

    protected int[] x = {320, 420, 520, 620, 725, 825, 930, 1030, 1130};
    protected int[] y = {110, 235, 360, 485, 610};
    protected int[] kx = x;

    private final boolean[][] joylar = new boolean[x.length][y.length];

    public Yol() {
        ustunlar = x.length; // 9
        qatorlar = y.length; // 5

        for (int i = 0; i < ustunlar; i++) {
            kx[i] = x[i] + (int) (uzunlik * 1. / 4);
        }
    }

    public boolean joyBor(Osimlik osimlik) {
        for (int j = 0; j < qatorlar; j++) {
            for (int i = 0; i < ustunlar; i++) {
                if (!this.band(i, j) &&
                        osimlik.getTranslateX() + osimlik.getFitWidth() / 2 >= x[i] &&
                        osimlik.getTranslateX() + osimlik.getFitWidth() / 2 <= x[i] + uzunlik &&
                        osimlik.getTranslateY() + osimlik.getFitHeight() / 2 > y[j] &&
                        osimlik.getTranslateY() + osimlik.getFitHeight() / 2 < y[j] + balandlik) {

                    osimlik.setTranslateX(kx[i]);
                    osimlik.setTranslateY(y[j]);

                    osimlik.nuqtaX(i);
                    osimlik.nuqtaY(j);

                    bandQilish(i, j);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean band(int x, int y) {
        return joylar[x][y];
    }

    public void bandQilish(int x, int y) {
        joylar[x][y] = true;
    }

    public void joyOchish(int x, int y) {
        joylar[x][y] = false;
    }
}
