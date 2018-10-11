import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/mainWindow.fxml"));
        primaryStage.setTitle("Order System");
        primaryStage.setScene(new Scene(root, 475, 355));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
