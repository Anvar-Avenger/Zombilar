package qismlar;

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

    private final AudioClip tegdi;

    Noxat(double x, double y, Zombi zombi) {
        setImage(new Image("/zaxira/rasmlar/qismlar/noxat.png"));

        setTranslateX(x);
        setTranslateY(y);

        setFitWidth(24);
        setFitHeight(24);

        URL manzil = getClass().getResource("/zaxira/ovozli-fayllar/noxat-tegishi.wav");
        tegdi = new AudioClip(Objects.requireNonNull(manzil).toString());

        harakat(zombi);
    }

    void harakat(Zombi zombi) {
        t = new Timeline(new KeyFrame(
                Duration.millis(2.5),
                jarayon -> {
                    setTranslateX(getTranslateX() + 1);

                    if (getTranslateX() + getFitWidth() > zombi.getTranslateX() || getTranslateX() > 1366) {
                        tegdi.play();
                        zombi.tegdi();
                        t.stop();
                        ((AnchorPane) this.getParent()).getChildren().remove(this);
                    }
                }
        ));
        t.setCycleCount(-1);
        t.play();
    }
}
