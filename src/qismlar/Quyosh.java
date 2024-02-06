package qismlar;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;


public class Quyosh extends ImageView {

    Quyosh(double x, double y, Label quyosh) {
        setImage(new Image("zaxira/rasmlar/qismlar/quyosh.png"));

        setFitWidth(0);
        setFitHeight(0);
        setTranslateX(x);
        setTranslateY(y);
        getStyleClass().add("quyosh");

        setOnMouseClicked(h -> {
            URL manzil = getClass().getResource("/zaxira/ovozli-fayllar/quyosh.wav");
            new AudioClip(Objects.requireNonNull(manzil).toString()).play();
            ScaleTransition mt = new ScaleTransition(Duration.seconds(0.5), this);
            mt.setToX(2. / 3);
            mt.setToY(2. / 3);
            mt.play();
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            tt.setToX(312 - 15);
            tt.setToY(20 - 15);
            tt.setOnFinished(oxiri -> {
                AnchorPane jism = (AnchorPane) this.getParent();
                jism.getChildren().remove(this);
                quyosh.setText(Mant(quyosh.getText()));
            });
            tt.play();
        });
    }

    String Mant(String matn) {
        int son = Integer.parseInt(matn);
        son += 25;
        return Integer.toString(son);
    }
}
