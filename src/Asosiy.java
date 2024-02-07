import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;


public class Asosiy extends Application {

    @Override
    public void start(Stage ilova) throws Exception {

        URL manzil = Objects.requireNonNull(getClass().getResource("sahifalar/menyu.fxml"));
        Parent ildiz = FXMLLoader.load(manzil);
        Scene sahna = new Scene(ildiz);

        ilova.setTitle("O\u2018simliklar va Zombilar");
        ilova.setScene(sahna);
        ilova.getIcons().add(new Image("zaxira/rasmlar/belgi.png"));
        ilova.setMaximized(true);
        ilova.setFullScreen(true);
        ilova.setFullScreenExitHint("");
        ilova.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        ilova.setOnCloseRequest(Event::consume);
        ilova.show();
    }


    public static void main(String[] qiymatlar) {
        launch(qiymatlar);
    }
}
