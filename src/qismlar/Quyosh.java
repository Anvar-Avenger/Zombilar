package qismlar;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import markaz.Tizim;

import java.net.URL;
import java.util.Objects;


public class Quyosh extends ImageView {

    private final int yorqinlik = Tizim.QUYOSH_YORQINLIGI;

    private final int chetlanish = 15;

    private final QuyoshChiqishTinglovchisi tinglovchi;


    Quyosh(double x, double y, QuyoshChiqishTinglovchisi tinglovchi) {
        setImage(new Image("zaxira/rasmlar/qismlar/quyosh.png"));

        setFitWidth(0);
        setFitHeight(0);
        setTranslateX(x);
        setTranslateY(y);

        // Add style
        getStyleClass().add("quyosh");

        // Add event listener
        this.tinglovchi = tinglovchi;
        hodisaBiriktirish();
    }

    private void hodisaBiriktirish() {
        this.setOnMouseClicked(h -> {
            // Play audio
            URL manzil = getClass().getResource("/zaxira/ovozli-fayllar/quyosh.wav");
            new AudioClip(Objects.requireNonNull(manzil).toString()).play();

            // Transition for scaling smoothly sun until reach score corner
            ScaleTransition mt = new ScaleTransition(Duration.seconds(0.5), this);
            mt.setToX(2. / 3);
            mt.setToY(2. / 3);
            mt.play();

            // Moving sun smoothly to score corner
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            tt.setToX(312 - chetlanish);
            tt.setToY(20 - chetlanish);

            // After finishing animation
            tt.setOnFinished(f -> {
                AnchorPane jism = (AnchorPane) this.getParent();
                jism.getChildren().remove(this);

                // Notify sun fully rise
                tinglovchi.chiqdi(yorqinlik);
            });
            tt.play();
        });
    }

    // Listener to inter-components
    interface QuyoshChiqishTinglovchisi {

        void chiqdi(int yorqinlik);
    }
}
