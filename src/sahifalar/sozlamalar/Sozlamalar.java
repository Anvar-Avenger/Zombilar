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

    protected int kichik, katta;
    private int m_x, t_x;

    private int foiz_m = 100, foiz_t = 100;

    @FXML
    void Musiqa(MouseEvent hodisa) {
        panel.setOnMouseDragged(tortish -> {
            m_x = (int) (tortish.getX() - musiqa.getFitWidth() / 2 - kichik);
            musiqa.setTranslateX(m_x >= 0 && m_x <= kichik ? m_x : musiqa.getTranslateX());
            foiz_m = (int) musiqa.getTranslateX() / 2;
        });

        if (hodisa.getClickCount() < 0)
            System.out.println("+");
    }

    @FXML
    void Tovush(MouseEvent hodisa) {
        panel.setOnMouseDragged(tortish -> {
            t_x = (int) (tortish.getX() - tovush.getFitWidth() / 2 - kichik);
            tovush.setTranslateX(t_x >= 0 && t_x <= kichik ? t_x : tovush.getTranslateX());
            foiz_t = (int) tovush.getTranslateX() / 2;
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
