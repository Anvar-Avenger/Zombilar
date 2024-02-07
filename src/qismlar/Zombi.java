package qismlar;

import markaz.Tizim;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class Zombi extends ImageView {

    private Timeline t;

    private final AudioClip ovoz;
    private final AudioClip oldi;

    private final Yol yol = new Yol();

    protected int[] y = yol.y;
    private int jon = 10;
    private int x = 0;
    private int foiz = 0;

    private Kungaboqar kungaboqar;
    private Noxatotuvchi noxatotuvchi;
    private Yongoq yongoq;

    private ArrayList<Kungaboqar> kungaboqarlar;
    private ArrayList<Noxatotuvchi> noxatotuvchilar;
    private ArrayList<Yongoq> yongoqlar;

    private boolean qichqirish = true;
    private boolean belgilandi = false;

    public Zombi() {
        setImage(new Image("zaxira/rasmlar/qismlar/zombi.gif"));
        setFitWidth(80);
        setFitHeight(136);
        setTranslateX(1466 +
                new Random().nextInt(100) * 2 +
                new Random().nextInt(100) * 2 +
                new Random().nextInt(100) * 2 +
                new Random().nextInt(100) * 2 +
                new Random().nextInt(100) * 2);
        setTranslateY(y[new Random().nextInt(y.length)] - getFitHeight() + yol.balandlik);

        URL uOvoz = getClass().getResource("/zaxira/ovozli-fayllar/zombi.wav");
        URL uOldi = getClass().getResource("/zaxira/ovozli-fayllar/vox.wav");
        ovoz = new AudioClip(Objects.requireNonNull(uOvoz).toString());
        oldi = new AudioClip(Objects.requireNonNull(uOldi).toString());

        harakat();
    }

    public void Kungaboqar(ArrayList<Kungaboqar> kungaboqarlar) {
        this.kungaboqarlar = kungaboqarlar;
    }

    public void Noxatotuvchi(ArrayList<Noxatotuvchi> noxatotuvchilar) {
        this.noxatotuvchilar = noxatotuvchilar;
    }

    public void Yongoq(ArrayList<Yongoq> yongoqlar) {
        this.yongoqlar = yongoqlar;
    }

    private void harakat() {
        x = -1;
        t = new Timeline(new KeyFrame(
                Duration.millis(1000 / 30.), // 30 kard/s
                jarayon -> {
                    if (!belgilandi) {
                        kungaboqarlar.forEach(ushbu -> {
                            if (getTranslateX() <= ushbu.getTranslateX() + ushbu.getFitWidth() &&
                                    getTranslateY() + getFitHeight() == ushbu.getTranslateY() + ushbu.getFitHeight()) {
                                kungaboqar = ushbu;
                                belgilandi = true;
                            }
                        });
                        noxatotuvchilar.forEach(ushbu -> {
                            if (getTranslateX() <= ushbu.getTranslateX() + ushbu.getFitWidth() &&
                                    getTranslateY() + getFitHeight() == ushbu.getTranslateY() + ushbu.getFitHeight()) {
                                noxatotuvchi = ushbu;
                                belgilandi = true;
                            }
                        });
                        yongoqlar.forEach(ushbu -> {
                            if (getTranslateX() <= ushbu.getTranslateX() + ushbu.getFitWidth() &&
                                    getTranslateY() + getFitHeight() == ushbu.getTranslateY() + ushbu.getFitHeight()) {
                                yongoq = ushbu;
                                belgilandi = true;
                            }
                        });
                    }

                    /* qismlar.Kungaboqar uchun */
                    if (kungaboqar != null) {
                        if (getTranslateX() <= kungaboqar.getTranslateX() + kungaboqar.getFitWidth() - 10 &&
                                getTranslateX() > kungaboqar.getTranslateX()) {
                            foiz++;
                            if (foiz == 100) {
                                kungaboqar.zararlanish();
                                foiz = 0;
                            }
                            x = 0;
                        }

                        if (kungaboqar.qulagan()) {
                            belgilandi = false;
                            x = -1;
                            kungaboqarlar.removeIf(ochirishga -> ochirishga == kungaboqar);
                            kungaboqar = null;
                            foiz = 0;
                        }
                    }
                    /*~*/

                    /* qismlar.Noxatotuvchi uchun */
                    if (noxatotuvchi != null) {
                        if (getTranslateX() <= noxatotuvchi.getTranslateX() + noxatotuvchi.getFitWidth() - 10 &&
                                getTranslateX() > noxatotuvchi.getTranslateX()) {
                            foiz++;
                            if (foiz == 100) {
                                noxatotuvchi.zararlanish();
                                foiz = 0;
                            }
                            x = 0;
                        }

                        if (noxatotuvchi.oldi()) {
                            belgilandi = false;
                            x = -1;
                            noxatotuvchilar.removeIf(ochirishga -> ochirishga == noxatotuvchi);
                            noxatotuvchi = null;
                            foiz = 0;
                        }
                    }
                    /*~*/

                    /* Yong'oq uchun */
                    if (yongoq != null) {
                        if (getTranslateX() <= yongoq.getTranslateX() + yongoq.getFitWidth() - 10 &&
                                getTranslateX() > yongoq.getTranslateX()) {
                            foiz++;
                            if (foiz == 100) {
                                yongoq.zararlanish();
                                foiz = 0;
                            }
                            x = 0;
                        }

                        if (yongoq.qutordi()) {
                            belgilandi = false;
                            x = -1;
                            yongoqlar.removeIf(ochirishga -> ochirishga == yongoq);
                            yongoq = null;
                            foiz = 0;
                        }
                    }
                    /*~*/

                    setTranslateX(getTranslateX() + x);

                    if (getTranslateX() < 1200 && qichqirish) {
                        ovoz.play();
                        qichqirish = false;
                    }

                    if (getTranslateX() < 0 || qutordi()) {
                        oldi.play();
                        AnchorPane jism = (AnchorPane) this.getParent();
                        t.stop();
                        jism.getChildren().remove(this);
                    }
                }
        ));
        t.setDelay(Duration.seconds(30));
        t.setCycleCount(-1);
        t.play();
    }

    protected void zararlanish() {
        jon--;
    }

    protected boolean qutordi() {
        return jon < 1;
    }

    protected boolean bor() {
        return getTranslateX() + getFitWidth() / 2 < Tizim.SCREEN_WIDTH;
    }
}
