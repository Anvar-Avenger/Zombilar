package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import markaz.Tizim;

import java.util.ArrayList;


public class Noxatotuvchi extends Osimlik {

    private final Image harakatda = new Image("zaxira/rasmlar/qismlar/noxatotuvchi.gif");
    private final Image erkin = new Image("zaxira/rasmlar/qismlar/noxatotuvchi-erkin.gif");

    private Timeline t;

    // Tashqi
    private Zombi zombi;
    private ArrayList<Zombi> zombilar;

    private int oraliq = 0;
    private int jon = Tizim.NOXATOTUVCHI_JON;

    // Holatlar
    private boolean ekilgan = false;
    private boolean jangovor = false;

    // This status check this flower select its enemy
    private boolean tanladi = false;

    public Noxatotuvchi() {
        setTranslateX(510);
        setTranslateY(y());

        setFitWidth(94);
        setFitHeight(90);

        setImage(erkin);

        harakatlanish();
    }

    public void zombilarBiriktirish(ArrayList<Zombi> zombilar) {
        this.zombilar = zombilar;
    }

    private void harakatlanish() {

        jangovor = true;

        t = new Timeline(new KeyFrame(Duration.millis(20), jarayon -> {
            if (!ekilgan) {
                return;
            }

            // Find enemy which it is walking flower's line
            if (!tanladi) {
                zombilar.forEach(zombi -> {
                    if (getTranslateY() + getFitHeight() == zombi.getTranslateY() + zombi.getFitHeight() &&
                            zombi.bor() && zombi.getTranslateX() > getTranslateX() + getFitWidth() / 2) {
                        this.zombi = zombi;

                        tanladi = true;
                        jangovor = true;
                    }
                });
            }

            if (tanladi) {
                oraliq++;

                if (oraliq == 15 && getTranslateX() + getFitWidth() / 2 < zombi.getTranslateX()) {
                    if (jangovor) {
                        setImage(harakatda);
                        jangovor = false;
                    }

                    Noxat noxat = new Noxat(getTranslateX() + getFitWidth() * 3 / 4, getTranslateY() + 17, zombi);
                    AnchorPane qobiq = (AnchorPane) this.getParent();
                    qobiq.getChildren().add(noxat);
                }

                if (zombi.getTranslateX() < getTranslateX() + getFitWidth() / 2 && tanladi) {
                    tanladi = false;
                    setImage(erkin);
                }

                if (oraliq == 60)
                    oraliq = 1;

                if (zombi.qutordi()) {
                    setImage(erkin);

                    zombilar.removeIf(jism -> jism == zombi);
                    zombi = null;

                    tanladi = false;
                }
            }

            if (qutordi()) {
                t.stop();

                // Remove Pie-shooter after it die
                AnchorPane qobiq = (AnchorPane) this.getParent();
                qobiq.getChildren().remove(this);
            }
        }));

        t.setCycleCount(-1);
        t.play();
    }

    protected void zararlanish() {
        jon--;
    }

    public void ekildi() {
        ekilgan = true;
    }

    protected boolean qutordi() {
        return jon < 1;
    }

    public int y() {
        return 10;
    }
}
