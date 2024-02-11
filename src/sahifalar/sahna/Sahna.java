package sahifalar.sahna;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import markaz.Tizim;
import qismlar.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;


public class Sahna {

    @FXML
    private AnchorPane muhit;
    @FXML
    private Pane maydon;
    @FXML
    public Label quyosh;
    @FXML
    private Pane gul;
    @FXML
    private Pane noxatchi;
    @FXML
    private Pane devor;

    private Kungaboqar kungaboqar;
    private Noxatotuvchi noxatotuvchi;
    private Yongoq yongoq;

    private final Yol yol = new Yol();

    private final ArrayList<Kungaboqar> kungaboqarlar = new ArrayList<>();
    private final ArrayList<Noxatotuvchi> noxatotuvchilar = new ArrayList<>();
    private final ArrayList<Yongoq> yongoqlar = new ArrayList<>();
    private final ArrayList<Zombi> zombilar = new ArrayList<>();

    private boolean ruxsatK = false, ruxsatN = false, ruxsatY = false; // joyidan siljitish uchun
    private boolean ruxsat = false, ochirishga = false;

    private int ochko = Tizim.QUYOSHLAR;

    protected AudioClip musiqa;
    protected AudioClip uzish;
    protected AudioClip ekish;

    public void initialize() {
        URL uMusiqa = getClass().getResource("/zaxira/ovozli-fayllar/sahna-musiqasi.aac");
        URL uUzish = getClass().getResource("/zaxira/ovozli-fayllar/uzish.wav");
        URL uEkish = getClass().getResource("/zaxira/ovozli-fayllar/joylash.wav");

        musiqa = new AudioClip(Objects.requireNonNull(uMusiqa).toString());
        uzish = new AudioClip(Objects.requireNonNull(uUzish).toString());
        ekish = new AudioClip(Objects.requireNonNull(uEkish).toString());

        musiqa.play();

        // Set content activity
        quyosh.setText(Integer.toString(ochko));

        Zombi zombi;
        for (int i = 1; i <= 10; i++) {
            zombi = new Zombi();
            zombi.Noxatotuvchi(noxatotuvchilar);
            zombi.Kungaboqar(kungaboqarlar);
            zombi.Yongoq(yongoqlar);
            zombi.addPlantEatListener(osimlik -> yol.joyOchish(osimlik.nuqtaX(), osimlik.nuqtaY()));

            // Add zombie to scene and zombies list
            zombilar.add(zombi);
            muhit.getChildren().add(zombi);
        }

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.8), maydon);
        tt.setDelay(Duration.seconds(2));
        tt.setToX(-324); // 1790 - Tizim.SCREEN_WIDTH
        tt.setOnFinished(yangi -> {
            TranslateTransition t = new TranslateTransition(Duration.seconds(0.8), maydon);
            t.setDelay(Duration.seconds(2));
            t.setToX(0);
            t.play();
        });
        tt.play();
    }

    @FXML
    void KungaboqarYaratish(MouseEvent hodisa) {
        if (hodisa.getClickCount() > 0)
            if (!ruxsatK && quyoshYetarli(50)) {
                uzish.play();

                kungaboqar = new Kungaboqar(this::jamlash);
                kungaboqarlar.add(kungaboqar);

                muhit.getChildren().add(kungaboqar);

                ruxsatK = true;
                ruxsat = true;
            }
    }

    @FXML
    void NoxatotuvchiYaratish(MouseEvent hodisa) {
        if (hodisa.getClickCount() > 0)
            if (!ruxsatN && quyoshYetarli(100)) {
                uzish.play();

                noxatotuvchi = new Noxatotuvchi();
                noxatotuvchi.zombilarBiriktirish(zombilar);
                noxatotuvchilar.add(noxatotuvchi);

                muhit.getChildren().add(noxatotuvchi);

                ruxsatN = true;
                ruxsat = true;
            }
    }

    @FXML
    void YongoqYaratish(MouseEvent hodisa) {
        if (hodisa.getClickCount() > 0)
            if (!ruxsatY && quyoshYetarli(50)) {
                uzish.play();

                yongoq = new Yongoq();
                yongoqlar.add(yongoq);

                muhit.getChildren().add(yongoq);
                ruxsatY = true;
                ruxsat = true;
            }
    }

    @FXML
    void Kochish(MouseEvent hodisa) {
        if (!ruxsat) {
            return;
        }

        if (ruxsatK) {
            kungaboqar.setTranslateX(hodisa.getX() - kungaboqar.getFitWidth() / 2);
            kungaboqar.setTranslateY(hodisa.getY() - kungaboqar.getFitHeight() / 2);

            if (kungaboqar.getTranslateY() > kungaboqar.y()) {
                ochirishga = true;
            }
        }

        if (ruxsatN) {
            noxatotuvchi.setTranslateX(hodisa.getX() - noxatotuvchi.getFitWidth() / 2);
            noxatotuvchi.setTranslateY(hodisa.getY() - noxatotuvchi.getFitHeight() / 2);

            if (noxatotuvchi.getTranslateY() > noxatotuvchi.y()) {
                ochirishga = true;
            }
        }

        if (ruxsatY) {
            yongoq.setTranslateX(hodisa.getX() - yongoq.getFitWidth() / 2);
            yongoq.setTranslateY(hodisa.getY() - yongoq.getFitHeight() / 2);

            if (yongoq.getTranslateY() > yongoq.y()) {
                ochirishga = true;
            }
        }
    }

    @FXML
    public void Joylashtirish(MouseEvent hodisa) {
        if (hodisa.getClickCount() > 0)
            Ochirish();

        if (ruxsatK && ruxsat) {
            if (yol.joyBor(kungaboqar)) {
                ekish.play();

                ekish(50);
                kungaboqar.ruxsatBerish();

                // Check access to planting
                ruxsat = false;
                ruxsatK = false;
                ochirishga = false;
            }
        }

        if (ruxsatN && ruxsat) {
            if (yol.joyBor(noxatotuvchi)) {
                ekish.play();
                ekish(100);

                noxatotuvchi.ruxsat();

                ruxsat = false;
                ruxsatN = false;
                ochirishga = false;
            }
        }

        if (ruxsatY && ruxsat) {
            if (yol.joyBor(yongoq)) {
                ekish.play();
                ekish(50);

                ruxsat = false;
                ruxsatY = false;
                ochirishga = false;
            }
        }
    }

    private void Ochirish() {
        if (ruxsatK) {
            if (ochirish(gul, kungaboqar) && ochirishga) {
                muhit.getChildren().remove(kungaboqar);

                kungaboqarlar.remove(kungaboqar);
                ochirishga = false;
                ruxsatK = false;
            }
        }

        if (ruxsatN) {
            if (ochirish(noxatchi, noxatotuvchi) && ochirishga) {
                muhit.getChildren().remove(noxatotuvchi);

                noxatotuvchilar.remove(noxatotuvchi);
                ochirishga = false;
                ruxsatN = false;
            }
        }

        if (ruxsatY) {
            if (ochirish(devor, yongoq) && ochirishga) {
                muhit.getChildren().remove(yongoq);

                yongoqlar.remove(yongoq);
                ochirishga = false;
                ruxsatY = false;
            }
        }
    }

    private boolean ochirish(Pane jism, ImageView osimlik) {
        return !(osimlik.getTranslateX() + 20 < jism.getParent().getLayoutX() + jism.getLayoutX() ||
                osimlik.getTranslateX() + osimlik.getFitWidth() - 20 > jism.getParent().getLayoutX() + jism.getLayoutX() + jism.getWidth() ||
                osimlik.getTranslateY() + 5 < jism.getLayoutY() ||
                osimlik.getTranslateY() + osimlik.getFitHeight() - 5 > jism.getLayoutY() + jism.getHeight());
    }

    /*** Quyosh ***/
    private boolean quyoshYetarli(int baho) {
        return baho <= ochko;
    }

    private void jamlash(int qiymat) {
        ochko += qiymat; // Integer.parseInt(matn);

        // Increase score by reference of Label
        quyosh.setText(Integer.toString(ochko));
    }

    private void ekish(int baho) {
        ochko -= baho; // Integer.parseInt(quyosh.getText()) - baho)
        quyosh.setText(Integer.toString(ochko));
    }
}