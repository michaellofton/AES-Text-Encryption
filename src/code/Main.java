package code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Main extends Application 
{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("code.fxml"));
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
        primaryStage.setTitle("AES Text Encryption");
        primaryStage.setScene(new Scene(root, 430, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
