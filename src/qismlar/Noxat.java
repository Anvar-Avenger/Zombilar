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
import java.util.Objects;


public class Noxat extends ImageView {

    private Timeline t;

    private final AudioClip tegishOvoz;

    Noxat(double x, double y, Zombi zombi) {
        setImage(new Image("/zaxira/rasmlar/qismlar/noxat.png"));

        setTranslateX(x);
        setTranslateY(y);

        setFitWidth(24);
        setFitHeight(24);

        URL manzil = getClass().getResource("/zaxira/ovozli-fayllar/noxat-tegishi.wav");
        tegishOvoz = new AudioClip(Objects.requireNonNull(manzil).toString());

        harakatlanish(zombi);
    }

    private void harakatlanish(Zombi zombi) {
        KeyFrame frame = new KeyFrame(
                Duration.millis(2.5),
                jarayon -> {
                    setTranslateX(getTranslateX() + 1);

                    if (getTranslateX() + getFitWidth() > zombi.getTranslateX() || getTranslateX() > Tizim.SCREEN_WIDTH) {
                        tegishOvoz.play();

                        // Crash pie with Zombie
                        zombi.zararlanish();

                        t.stop();
                        // Remove pie from screen
                        ((AnchorPane) this.getParent()).getChildren().remove(this);
                    }
                }
        );

        t = new Timeline(frame);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }
}
