package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import markaz.Tizim;


public class Yongoq extends Osimlik {

    private Timeline t;

    private int jon = Tizim.YONGOQ_JON;

    public Yongoq() {
        setTranslateX(610);
        setTranslateY(y());
        setFitWidth(80);
        setFitHeight(90);

        Image rasm = new Image("zaxira/rasmlar/qismlar/nut.gif");
        setImage(rasm);
        jarayon();
    }

    private void jarayon() {
        t = new Timeline(new KeyFrame(
                Duration.millis(1),
                hodisa -> {
                    if (qutordi()) {
                        t.stop();

                        // Remove nut from screen
                        ((AnchorPane) this.getParent()).getChildren().remove(this);
                    }
                }
        ));
        t.setCycleCount(-1);
        t.play();
    }

    protected void zararlanish() {
        jon--;
    }

    protected boolean qutordi() {
        return jon < 1;
    }

    public int y() {
        return 10;
    }
}
