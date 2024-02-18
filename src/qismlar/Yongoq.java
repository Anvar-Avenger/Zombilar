package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import markaz.Tizim;
import qismlar.qoidalar.IOsimlik;


public class Yongoq extends Osimlik implements IOsimlik {

    private Timeline t;

    private int jon = Tizim.YONGOQ_JON;

    public Yongoq() {
        setTranslateX(610);
        setTranslateY(joylashuvY());
        setFitWidth(80);
        setFitHeight(90);

        Image rasm = new Image("zaxira/rasmlar/qismlar/nut.gif");
        setImage(rasm);

        // Check for die
        jarayon();
    }

    private void jarayon() {
        t = new Timeline(new KeyFrame(Duration.millis(1), hodisa -> {
            if (qutordi()) {
                t.stop();

                // Remove nut from screen
                ((AnchorPane) this.getParent()).getChildren().remove(this);
            }
        }));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    @Override
    public int ekildi() {
        return 50;
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
