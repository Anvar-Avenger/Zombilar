package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class Kungaboqar extends ImageView {

    private final Timeline t = new Timeline();

    private int foiz = 0;
    private int jon = 2;

    boolean ruxsat = false;

    public Kungaboqar(Label quyosh) {
        Image rasm = new Image("zaxira/rasmlar/qismlar/kungaboqar.gif");

        setTranslateX(410);
        setTranslateY(y());
        setFitWidth(rasm.getWidth() < 100 ? rasm.getWidth() : 100);
        setFitHeight(90);

        setImage(rasm);
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(100),
                jarayon -> {
                    if (ruxsat) {
                        foiz++;

                        if (foiz == 20) {
                            qoshish(quyosh);
                        }

                        if (foiz == 100) {
                            foiz = 0;
                        }
                    }

                    if (oldi()) {
                        t.stop();
                        ((AnchorPane) this.getParent()).getChildren().remove(this);
                    }
                }
        ));
        t.setCycleCount(-1);
        t.play();
    }

    void qoshish(Label quyosh_soni) {
        AnchorPane jism = (AnchorPane) this.getParent();
        Quyosh quyosh = new Quyosh(getTranslateX(), getTranslateY() - 5, quyosh_soni);
        jism.getChildren().add(quyosh);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), quyosh);
        tt.setByX(50);
        tt.setByY(-10);
        tt.play();

        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), quyosh);
        st.setToX(1);
        st.setToY(1);
        st.play();
    }

    public void ruxsat() {
        ruxsat = true;
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
