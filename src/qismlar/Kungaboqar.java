package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import markaz.Tizim;


public class Kungaboqar extends Osimlik {

    private final Timeline t = new Timeline();

    private int foiz = 0;
    private int jon = Tizim.KUNGABOQAR_JON;

    private boolean ruxsat = false;

    // Sun
    private final SunBornListener sunBornListener;

    public Kungaboqar(SunBornListener listener) {
        Image rasm = new Image("zaxira/rasmlar/qismlar/kungaboqar.gif");

        // Set position
        setTranslateX(410);
        setTranslateY(y());

        // Set with and height
        setFitWidth(rasm.getWidth() < 100 ? rasm.getWidth() : 100);
        setFitHeight(90);

        setImage(rasm);

        this.sunBornListener = listener;

        t.getKeyFrames().add(new KeyFrame(Duration.millis(100), jarayon -> {
            if (ruxsat) {
                foiz++;

                if (foiz == 20) {
                    qoshish();
                }

                if (foiz == 100) {
                    foiz = 0;
                }
            }

            if (qulagan()) {
                t.stop();

                // Remove sunflower after it
                ((AnchorPane) this.getParent()).getChildren().remove(this);
            }
        }
        ));
        t.setCycleCount(-1);
        t.play();
    }

    private void qoshish() {
        // Create new sun
        Quyosh quyosh = new Quyosh(getTranslateX(), getTranslateY() - 5, sunBornListener::onSunrise);

        // Append sun to parent node
        AnchorPane jism = (AnchorPane) this.getParent();
        jism.getChildren().add(quyosh);

        // To move sun top-right edge of sunflower
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), quyosh);
        tt.setByX(50);
        tt.setByY(-10);
        tt.play();

        // Scale smoothly sun
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), quyosh);
        st.setToX(1);
        st.setToY(1);
        st.play();
    }

    /**
     * Permit planting sunflower
     */
    public void ruxsatBerish() {
        ruxsat = true;
    }

    public int y() {
        return 10;
    }

    protected void zararlanish() {
        jon--;
    }

    /**
     * To check sunflower is dead
     */
    protected boolean qulagan() {
        return jon < 1;
    }


    public interface SunBornListener {
        void onSunrise(int brightness);
    }
}
