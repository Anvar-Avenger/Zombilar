package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.ArrayList;


public class Noxatotuvchi extends ImageView {

    private final Image rasm = new Image("zaxira/rasmlar/qismlar/noxatotuvchi.gif");
    private final Image erkin = new Image("zaxira/rasmlar/qismlar/noxatotuvchi-erkin.gif");

    private Timeline t;

    // Tashqi
    private Zombi zombi;
    private ArrayList<Zombi> zombilar;

    private int i = 0;
    private int jon = 2;
    private boolean jangovor = false;
    private boolean belgilandi = false;
    private boolean ochirish = false;
    private boolean ruxsat = false;

    public Noxatotuvchi() {
        setTranslateX(510);
        setTranslateY(y());
        setFitWidth(94);
        setFitHeight(90);

        setImage(erkin);

        jangovor();
    }

    public void Zombi(ArrayList<Zombi> zombilar) {
        this.zombilar = zombilar;
    }

    void jangovor() {
        jangovor = true;
        t = new Timeline(new KeyFrame(
                Duration.millis(20),
                jarayon -> {

                    if (!belgilandi) {
                        zombilar.forEach(ushbu -> {
                            if (getTranslateY() + getFitHeight() == ushbu.getTranslateY() + ushbu.getFitHeight() &&
                                    ushbu.bor() && ushbu.getTranslateX() > getTranslateX() + getFitWidth() / 2) {
                                zombi = ushbu;
                                belgilandi = true;
                                jangovor = true;
                            }
                        });
                    }

                    if (zombi != null && ruxsat) {
                        i++;
                        if (i == 15 && getTranslateX() + getFitWidth() / 2 < zombi.getTranslateX()) {

                            if (jangovor) {
                                setImage(rasm);
                                jangovor = false;
                            }

                            ((AnchorPane) this.getParent()).getChildren().add(new Noxat(getTranslateX() + getFitWidth() * 3 / 4,
                                    getTranslateY() + 17, zombi));
                        }

                        if (zombi.getTranslateX() < getTranslateX() + getFitWidth() / 2 && belgilandi) {
                            belgilandi = false;
                            setImage(erkin);
                        }

                        if (i == 60)
                            i = 1;

                        if (zombi.oldi()) {
                            ochirish = true;
                            setImage(erkin);
                        }
                    }

                    if (ochirish) {
                        zombilar.removeIf(ochirishga -> ochirishga == zombi);
                        zombi = null;
                        belgilandi = false;
                        ochirish = false;
                    }

                    if (oldi()) {
                        t.stop();
                        ((AnchorPane) this.getParent()).getChildren().remove(this);
                    }
                }
        ));
        t.setCycleCount(-1);
        t.play();
    }

    void zararlanish() {
        jon--;
    }

    public void ruxsat() {
        ruxsat = true;
    }

    boolean oldi() {
        return jon < 1;
    }

    public int y() {
        return 10;
    }
}
