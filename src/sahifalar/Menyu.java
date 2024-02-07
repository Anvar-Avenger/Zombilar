package sahifalar;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;


public class Menyu {

    private AudioClip musiqa;

    @FXML
    void initialize() {
        try {
            URL manzil = Objects.requireNonNull(getClass().getResource(
                    "../zaxira/ovozli-fayllar/crazy-dave-(intro-theme).mp3"
            ));

            musiqa = new AudioClip(manzil.toString());
            musiqa.setCycleCount(AudioClip.INDEFINITE);
            musiqa.play();
        } catch (IllegalAccessError ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void Boshlash(MouseEvent hodisa) {
        musiqa.stop();

        try {
            Parent ildiz = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sahifalar/sahna/sahna.fxml")));
            Node jism = (Node) hodisa.getSource();
            Scene yangi = jism.getScene();

            // Start fade animation
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), jism.getParent());
            ft.setFromValue(1);
            ft.setToValue(0);

            // Change scene/page
            ft.setOnFinished(keyin -> yangi.setRoot(ildiz));
            ft.play();

        } catch (Exception xatolik) {
            System.out.print("Xatolik mavjud");
            xatolik.printStackTrace();
        }
    }

    @FXML
    void Davomi(MouseEvent hodisa) {
        URL manzil = getClass().getResource("/zaxira/ovozli-fayllar/menyudan-qochish.wav");
        new AudioClip(Objects.requireNonNull(manzil).toString()).play();
        if (hodisa.getClickCount() > 0)
            System.out.println("Ushbu funksiya ishlamaydi");
    }

    @FXML
    void Sozlamalar(MouseEvent hodisa) {
        URL manzil = getClass().getResource("/zaxira/ovozli-fayllar/menyuga-borish.wav");
        new AudioClip(Objects.requireNonNull(manzil).toString()).play();

        try {
            URL sahifa = getClass().getResource("/sahifalar/sozlamalar/sozlamalar.fxml");
            Parent ildiz = FXMLLoader.load(Objects.requireNonNull(sahifa));
            Scene yangi = new Scene(ildiz);
            yangi.setFill(Color.TRANSPARENT);

            Stage ilova = new Stage();
            ilova.setScene(yangi);
            ilova.setTitle("Sozlamalar");
            ilova.setResizable(false);
            ilova.setFullScreen(true);
            ilova.initStyle(StageStyle.TRANSPARENT);
            ilova.getIcons().add(((Stage) ((Node) hodisa.getSource()).getScene().getWindow()).getIcons().get(0));
            ilova.showAndWait();
        } catch (Exception x) {
            System.out.println("Xatolik mavjud");
            x.printStackTrace();
        }
    }

    @FXML
    void Chiqish(MouseEvent hodisa) {
        System.exit(hodisa.getClickCount() > 0 ? 0 : -1);
    }
}
