package sahifalar.sozlamalar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Sozlamalar {

    @FXML
    AnchorPane panel;
    @FXML
    private ImageView musiqa;
    @FXML
    private ImageView tovush;

    // Variable for volume range
    protected int kichik, katta;

    // X coordinate for image views
    private int mX, tX;

    // Percentage volume of music and background effect
    private int foizM = 100, foizT = 100;

    @FXML
    void Musiqa(MouseEvent hodisa) {
        panel.setOnMouseDragged(tortish -> {
            mX = (int) (tortish.getX() - musiqa.getFitWidth() / 2 - kichik);
            musiqa.setTranslateX(mX >= 0 && mX <= kichik ? mX : musiqa.getTranslateX());
            foizM = (int) musiqa.getTranslateX() / 2;
        });

        if (hodisa.getClickCount() < 0)
            System.out.println("+");
    }

    @FXML
    void Tovush(MouseEvent hodisa) {
        panel.setOnMouseDragged(tortish -> {
            tX = (int) (tortish.getX() - tovush.getFitWidth() / 2 - kichik);
            tovush.setTranslateX(tX >= 0 && tX <= kichik ? tX : tovush.getTranslateX());
            foizT = (int) tovush.getTranslateX() / 2;
        });

        if (hodisa.getClickCount() < 0)
            System.out.println("+");
    }

    @FXML
    void Chiqish(MouseEvent hodisa) {
        if (hodisa.getClickCount() > 0)
            System.exit(0);
    }

    @FXML
    void Boshiga(MouseEvent hodisa) {
        Stage ilova = (Stage) ((Node) hodisa.getSource()).getScene().getWindow();
        ilova.close();
    }

    public void initialize() {
        kichik = (int) musiqa.getLayoutX();
        katta = 2 * kichik;
    }
}
