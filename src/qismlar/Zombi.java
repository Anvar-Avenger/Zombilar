package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import markaz.Tizim;
import qismlar.qoidalar.IDushman;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class Zombi extends ImageView implements IDushman {

    private Timeline t;

    private final AudioClip ovoz;
    private final AudioClip oldi;

    private int jon = Tizim.ZOMBI_JON;
    private int tezlik = 0;
    private int foiz = 0;
    private final int y;

    private Osimlik osimlik;

    private ArrayList<Kungaboqar> kungaboqarlar;
    private ArrayList<Noxatotuvchi> noxatotuvchilar;
    private ArrayList<Yongoq> yongoqlar;

    private boolean sahnagaChiqdi = true; // Sahna chiqdi
    private boolean belgilandi = false;

    private OsimlikYeyishTinglovchisi tinglovchi;
    private YakunlashTinglovchisi yakunlovchi;

    public Zombi() {
        // Set width and height
        setImage(new Image("zaxira/rasmlar/qismlar/zombi.gif"));
        setFitWidth(80);
        setFitHeight(136);

        final Yol yol = new Yol();
        int[] qatorlar = yol.y;

        int zX = Tizim.EKRAN_ENI + 10 +
                new Random().nextInt(100) * 2 +
                new Random().nextInt(100) * 2 +
                new Random().nextInt(100) * 2 +
                new Random().nextInt(100) * 2 +
                new Random().nextInt(100) * 2;

        y = new Random().nextInt(qatorlar.length);
        double zY = qatorlar[y] + (yol.balandlik - getFitHeight());

        setTranslateX(zX);
        setTranslateY(zY);

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

    public void yeyishTinglovchisiBiriktirish(OsimlikYeyishTinglovchisi listener) {
        this.tinglovchi = listener;
    }

    public void yakunlashTinglovchiBiriktirish(YakunlashTinglovchisi tinglovchi) {
        this.yakunlovchi = tinglovchi;
    }

    private void harakat() {
        tezlik = -1;

        // 30 kard/s
        t = new Timeline(new KeyFrame(Duration.millis(1000 / 30.), jarayon -> {
            if (!belgilandi) {
                tanlash();
            } else {
                yeyish();
            }

            // Move to x
            setTranslateX(getTranslateX() + tezlik);

            if (bor() && sahnagaChiqdi) {
                ovoz.play();
                sahnagaChiqdi = false;
            }

            if (getTranslateX() < Tizim.UY_ESHIGI && yakunlovchi != null) {
                yakunlovchi.uygaKirdi(this);
            }

            if (getTranslateX() < Tizim.UY_ESHIGI || qutordi()) {
                oldi.play();

                this.qulash();

                t.stop();
            }
        }));

        t.setDelay(Duration.seconds(30));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void qulash() {
        // Remove Zombie from view
        AnchorPane jism = (AnchorPane) this.getParent();
        jism.getChildren().remove(this);
    }

    private void tanlash() {
        kungaboqarlar.forEach(kungaboqar -> {
            // this.getTranslateY() + this.getFitHeight() == kungaboqar.getTranslateY() + kungaboqar.getFitHeight()
            if (this.getTranslateX() <= kungaboqar.getTranslateX() + kungaboqar.getFitWidth() &&
                    kungaboqar.nuqtaY() == this.y) {
                this.osimlik = kungaboqar; // this.kungaboqar = kungaboqar;
                belgilandi = true;
            }
        });

        if (belgilandi)
            return;
        noxatotuvchilar.forEach(noxatotuvchi -> {
            // this.getTranslateY() + this.getFitHeight() == noxatotuvchi.getTranslateY() + noxatotuvchi.getFitHeight()
            if (this.getTranslateX() <= noxatotuvchi.getTranslateX() + noxatotuvchi.getFitWidth() &&
                    noxatotuvchi.nuqtaY() == this.y) {
                this.osimlik = noxatotuvchi; // this.noxatotuvchi = noxatotuvchi;
                belgilandi = true;
            }
        });

        if (belgilandi)
            return;
        yongoqlar.forEach(yongoq -> {
            // this.getTranslateY() + this.getFitHeight() == yongoq.getTranslateY() + yongoq.getFitHeight()
            if (this.getTranslateX() <= yongoq.getTranslateX() + yongoq.getFitWidth() &&
                    yongoq.nuqtaY() == this.y) {

                this.osimlik = yongoq; // this.yongoq = yongoq;
                belgilandi = true;
            }
        });
    }

    private void yeyish() {
        if (this.getTranslateX() <= osimlik.getTranslateX() + osimlik.getFitWidth() - 10 &&
                this.getTranslateX() > osimlik.getTranslateX()) {

            foiz++;

            if (foiz == 100) {
                osimlik.zararlanish();
                foiz = 0;
            }

            tezlik = 0;
        }

        if (osimlik.qutordi()) {
            belgilandi = false;
            tezlik = -1;

            tinglovchi.yeganPayti(osimlik);

            if (osimlik instanceof Kungaboqar) {
                kungaboqarlar.removeIf(jism -> jism == osimlik);
            }

            if (osimlik instanceof Noxatotuvchi) {
                noxatotuvchilar.removeIf(jism -> jism == osimlik);
            }

            if (osimlik instanceof Yongoq) {
                yongoqlar.removeIf(jism -> jism == osimlik);
            }

            osimlik = null;
            foiz = 0;
        }
    }

    @Override
    public void toxtat() {
        t.stop();
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
    public boolean bor() {
        return getTranslateX() + getFitWidth() / 2 < Tizim.EKRAN_ENI;  // Tizim.EKRAN_ENI - 136
    }

    // PlantEatListener
    public interface OsimlikYeyishTinglovchisi {
        void yeganPayti(Osimlik osimlik);
    }

    public interface YakunlashTinglovchisi {
        /* onReachHouse */
        void uygaKirdi(Zombi zombi);
    }
}
