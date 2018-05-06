/**
 * Finished by Michael L. on 12/25/16
 */

package code;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox
{
    public static void display(String title, String message, Image windowIcon)
    {
        //the title and message parameters can be taken in as string values
        //to have the alert box have any title and say any message.
        //This is dynamic and allows for easy customization when creating
        //largescale applications.



        Stage window = new Stage();
        window.getIcons().add(windowIcon);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
            //Block user input/interaction with other windows until this
            //window is taken care of
        window.setTitle(title);
            //Set the title of the window to whatever we want it to be when creating
            //a new alert box
        window.setMinWidth(300);
            //Sets the minimum width of the box to be 250 pixels

        Label label = new Label();
        label.setText(message);
            //Whatever message we want the Alert Box to say is displayed through
            //a label object.

        VBox layout = new VBox(10);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);
            //Set the widgets/gadgets/content to be centered in the middle of the layout
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
            //Displays the window, and before the user can return to the first window, forces the user
            //to close the alert box window
    }
}
