package GUI;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class IndexViewTest extends ApplicationTest {

    private Label userStatus;
    private IndexView indexView;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = View.class.getResource("/view/index.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        indexView = (IndexView) fxmlLoader.getController();
        primaryStage.setTitle("University of Edinburgh Dissertation");
        primaryStage.setScene(new Scene(root, 900, 720));
        primaryStage.show();
    }

    @Test
    public void gameTypeTest() {
        clickOn("#newGame");
        assertEquals("new", indexView.getGameType());
        clickOn("#back");
        clickOn("#conTinue");
        assertEquals("continue", indexView.getGameType());
    }

    @Test
    public void emptyTest() {
        clickOn("#newGame");
        doubleClickOn("#userName");
        type(KeyCode.BACK_SPACE);
        clickOn("#login");
        FxAssert.verifyThat("#userStatus", LabeledMatchers.hasText("Please input your info!"));
        doubleClickOn("#userName");
        type(KeyCode.U);
        doubleClickOn("#IPAddress");
        type(KeyCode.BACK_SPACE);
        clickOn("#login");
        FxAssert.verifyThat("#userStatus", LabeledMatchers.hasText("Please input your info!"));
    }

}