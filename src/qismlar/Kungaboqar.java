package qismlar;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import markaz.Tizim;
import qismlar.qoidalar.IOsimlik;


public class Kungaboqar extends Osimlik implements IOsimlik {

    private final Timeline t = new Timeline();

    private int jon = Tizim.KUNGABOQAR_JON;
    private int foiz = 0;

    private boolean ekilgan = false;

    // Sun
    private final QuyoshChiqishTinglovchisi tinglovchi;

    public Kungaboqar(QuyoshChiqishTinglovchisi tinglovchi) {
        Image rasm = new Image("zaxira/rasmlar/qismlar/kungaboqar.gif");

        // Set position
        setTranslateX(410);
        setTranslateY(joylashuvY());

        // Set with and height
        setFitWidth(rasm.getWidth() < 100 ? rasm.getWidth() : 100);
        setFitHeight(90);

        setImage(rasm);

        this.tinglovchi = tinglovchi;

        harakat();
    }

    private void harakat() {
        t.getKeyFrames().add(new KeyFrame(Duration.millis(100), jarayon -> {
            if (!ekilgan) {
                return;
            }

            foiz++;

            if (foiz == 20) {
                qoshish();
            }

            if (foiz == 100) {
                foiz = 0;
            }

            if (qutordi()) {
                t.stop();

                // Remove sunflower after it
                ((AnchorPane) this.getParent()).getChildren().remove(this);
            }
        }));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void qoshish() {
        // Create new sun
        Quyosh quyosh = new Quyosh(getTranslateX(), getTranslateY() - 5, tinglovchi::quyoshChiqdi);

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
    @Override
    public int ekildi() {
        ekilgan = true;

        return 50;
    }

    @Override
    public void zararlanish() {
        jon--;
    }

    /**
     * To check sunflower is dead
     */
    @Override
    public boolean qutordi() {
        return jon < 1;
    }

    @Override
    public int joylashuvY() {
        return 10;
    }

    public interface QuyoshChiqishTinglovchisi {
        void quyoshChiqdi(int yorqinlik);
    }
}
