package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import markaz.Tizim;

import java.net.URL;
import java.util.Objects;


public class Noxat extends ImageView {

    private Timeline t;

    private final AudioClip tegishOvoz;

    private final NoxatTegishTinglovchisi tinglovchi;


    Noxat(double x, double y, Zombi zombi, final NoxatTegishTinglovchisi tinglovchi) {
        setImage(new Image("/zaxira/rasmlar/qismlar/noxat.png"));

        setTranslateX(x);
        setTranslateY(y);

        setFitWidth(24);
        setFitHeight(24);

        URL manzil = getClass().getResource("/zaxira/ovozli-fayllar/noxat-tegishi.wav");
        tegishOvoz = new AudioClip(Objects.requireNonNull(manzil).toString());

        this.tinglovchi = tinglovchi;

        harakatlanish(zombi);
    }

    private void harakatlanish(Zombi zombi) {
        KeyFrame frame = new KeyFrame(Duration.millis(2.5), jarayon -> {
            setTranslateX(getTranslateX() + 1);

            // Zombiga teksa
            if (getTranslateX() + getFitWidth() > zombi.getTranslateX()) {
                tegishOvoz.play();

                // Crash pie with Zombie
                tinglovchi.tegdi();

                t.stop();
                // Remove pie from screen
                ((AnchorPane) this.getParent()).getChildren().remove(this);

                return;
            }

            // Sahnadan chiqib ketsa
            if (getTranslateX() > Tizim.EKRAN_ENI) {
                t.stop();
                // Remove pie from screen
                ((AnchorPane) this.getParent()).getChildren().remove(this);
            }
        });

        t = new Timeline(frame);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    public interface NoxatTegishTinglovchisi {
        void tegdi();
    }
}
