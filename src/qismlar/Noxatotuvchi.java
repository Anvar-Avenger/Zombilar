package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import markaz.Tizim;
import qismlar.qoidalar.IOsimlik;

import java.util.ArrayList;


public class Noxatotuvchi extends Osimlik implements IOsimlik {

    private int jon = Tizim.NOXATOTUVCHI_JON;
    private int oraliq = 0;

    private final Image harakatda = new Image("zaxira/rasmlar/qismlar/noxatotuvchi.gif");
    private final Image erkin = new Image("zaxira/rasmlar/qismlar/noxatotuvchi-erkin.gif");

    private Timeline t;

    // Holatlar
    private boolean ekilgan = false;
    private boolean jangovor = false;
    private boolean tanladi = false; // This status check this flower select its enemy

    // Tashqi
    private Zombi zombi;
    private ArrayList<Zombi> zombilar;


    public Noxatotuvchi() {
        setTranslateX(510);
        setTranslateY(joylashuvY());

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

                    double nX = getTranslateX() + getFitWidth() * 3 / 4;
                    double nY = getTranslateY() + 17;

                    // For case when one more pie shooters shoot one zombie
                    Noxat noxat = new Noxat(nX, nY, zombi, () -> {
                        if (zombi != null) {
                            zombi.zararlanish();
                        }
                    });

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

    @Override
    public int ekildi() {
        ekilgan = true;

        return Tizim.NOXATOTUVCHI_NARX;
    }

    @Override
    public void zararlanish() {
        jon--;
    }

    @Override
    public boolean qutordi() {
        return jon < 1;
    }

    @Override
    public int joylashuvY() {
        return 10;
    }
}
