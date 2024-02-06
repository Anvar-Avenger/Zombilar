package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class Yongoq extends ImageView {

    private Timeline t;

    private int jon = 6;

    public Yongoq() {
        setTranslateX(610);
        setTranslateY(y());
        setFitWidth(80);
        setFitHeight(90);

        Image rasm = new Image("zaxira/rasmlar/qismlar/nut.gif");
        setImage(rasm);
        jarayon();
    }

    void jarayon() {
        t = new Timeline(new KeyFrame(
                Duration.millis(1),
                hodisa -> {
                    if (oldi()) {
                        t.stop();
                        ((AnchorPane) this.getParent()).getChildren().remove(this);
                    }
                }
        ));
        t.setCycleCount(-1);
        t.play();
    }

    void zararlanish() {
        jon--;
    }

    boolean oldi() {
        return jon < 1;
    }

    public int y() {
        return 10;
    }
}
