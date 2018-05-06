/**
 * Finished by Michael L. on 12/25/16
 */

package code;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.util.ArrayList;

import static jdk.nashorn.internal.runtime.regexp.joni.constants.StackType.POS;

public class SettingsBox
{
    public static void display(String title, String message)
    {
        //the title and message parameters can be taken in as string values
        //to have the settings box have any title and say any message.
        //This is dynamic and allows for easy customization when creating
        //largescale applications.


        //Fields / Local variables for method:
        Stage window = new Stage();
        Label label = new Label();

        Image icon = new Image("file:res/settings.png");
        window.getIcons().add(icon);

        Label keySizeLabel;
        Label modeLabel;
        Label paddingLabel;
        Label textEncodingLabel;

        ChoiceBox<String> choiceBoxKeySize = new ChoiceBox<>();
        ChoiceBox<String> choiceBoxMode = new ChoiceBox<>();
        ChoiceBox<String> choiceBoxPadding = new ChoiceBox<>();
        ChoiceBox<String> choiceBoxTextEncoding = new ChoiceBox<>();

        HBox keySizeHBox = new HBox(5); //The 5 is the amount of spacing in between each item in the hbox in pixels
        HBox modeHBox = new HBox(5);
        HBox paddingHBox = new HBox(5);
        HBox textEncodingHBox = new HBox(5);

        RadioButton keyIsRandom;
        RadioButton keyIsUserPass;


        VBox ivVbox = new VBox();
        Label ivLabel;
        HBox ivContainer = new HBox(10);
        VBox ivChoices = new VBox(3);
        ToggleGroup ivToggleGroup;
        RadioButton appendToCiphertext;
        RadioButton prependToCiphertext;
        RadioButton appendToKey;
        RadioButton prependToKey;
        CheckBox ivHighEntropy;


        VBox layout = new VBox(10);
        Scene scene = new Scene(layout);

        //************************************************************************************

        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
            //Block user input/interaction with other windows until this
            //window is taken care of
        window.setTitle(title);
            //Set the title of the window to whatever we want it to be when creating
            //a new settings box
        window.setMinWidth(300);
            //Sets the minimum width of the box to be 250 pixels

        //************************************************************************************

        label.setText(message);
            //Whatever message we want the Settings Box to say is displayed through
            //a label object.

        //************************** CHOICE GADGET SETTINGS **********************************************************

        //Make drop down menus that allows the user to change how the program
        //behaves based on the selected choices:

        VBox labels = new VBox(14);
        VBox choiceGadgets = new VBox(5);
        HBox labelsAndChoiceGadgets = new HBox(5);

        //Key Size Settings
            keySizeLabel = new Label("Key Size:");
            choiceBoxKeySize.getItems().addAll("128", "192", "256");
            choiceBoxKeySize.setValue(aesEncryption.getKeySize());  //Sets the default selected choice to be whatever is currently being used

        //Mode Settings
            modeLabel = new Label("Mode:");
            choiceBoxMode.getItems().addAll("CBC", "ECB");
            choiceBoxMode.setValue(aesEncryption.getMode());

        //Padding Settings
            paddingLabel = new Label("Padding:");
            choiceBoxPadding.getItems().addAll("NoPadding", "PKCS5Padding");
            choiceBoxPadding.setValue(aesEncryption.getPadding());

        //Text Encoding Settings
            textEncodingLabel = new Label("Text Encoding:");
            choiceBoxTextEncoding.getItems().addAll("Base64", "Hexadecimal");
            choiceBoxTextEncoding.setValue(aesEncryption.getTextEncoding());

        //Key Settings
            VBox keySettingsVBox = new VBox();
            Label keySettingsLabel = new Label("Key Settings:");

            ToggleGroup keyToggleGroup = new ToggleGroup();

            keyIsRandom = new RadioButton("Generate key randomly using software");
            keyIsUserPass = new RadioButton("Type in password as the key");
            keyIsRandom.setToggleGroup(keyToggleGroup);
            keyIsUserPass.setToggleGroup(keyToggleGroup);

            switch ((aesEncryption.getKeySettings()))
            {
                case "Randomly Generated Key":
                    keyIsRandom.setSelected(true);
                    break;
                case "Typed Password Key":
                    keyIsUserPass.setSelected(true);
                    break;
            }

        keySizeHBox.getChildren().addAll(choiceBoxKeySize);
        modeHBox.getChildren().addAll(choiceBoxMode);
        paddingHBox.getChildren().addAll(choiceBoxPadding);
        textEncodingHBox.getChildren().addAll(choiceBoxTextEncoding);
        keySettingsVBox.getChildren().addAll(keyIsRandom, keyIsUserPass);

        labels.getChildren().addAll(keySizeLabel, modeLabel, paddingLabel, textEncodingLabel, keySettingsLabel);
        choiceGadgets.getChildren().addAll(keySizeHBox, modeHBox, paddingHBox, textEncodingHBox, keySettingsVBox);

        labelsAndChoiceGadgets.getChildren().addAll(labels, choiceGadgets);
        labelsAndChoiceGadgets.setAlignment(Pos.CENTER);


            // choiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> System.out.println(newValue));
        //Different types of lists have diffrent kinds of selection models
        //getSelectionModel() - Listens for selection changes:
        //selectedItemProperty() - The item that the user selected from the list
        //addListener( (v, oldValue, newValue) -> code ) - Sits on the item/object and waits for something to happen (waiting for a click)
        //v is the property of the selected item (the specific item in the list) v meaning observable
        //oldValue is the selected value before it's changed
        //newValue is the new value to change to

        //****************************** IV SETTINGS *************************************************

        ivContainer.setAlignment(Pos.CENTER);
        ivVbox.setAlignment(Pos.TOP_CENTER);

        ivLabel = new Label("IV:");

        ivToggleGroup = new ToggleGroup();
        //A radio button a button in a group of buttons (usually 3), that can
        //only have 1 of the buttons checked.
        //Example - Difficulty: Easy, Medium, or Hard. Only 1 can be checked for a game mode.
        //Because they need to all be apart of a group, create a ToggleGroup object.

        prependToCiphertext = new RadioButton("Prepend to Ciphertext");
        appendToCiphertext = new RadioButton("Append to Ciphertext");
        prependToKey = new RadioButton("Prepend to Key");
        appendToKey = new RadioButton("Append to Key");
        //Create all the radio button items you want a user to view and check

        //Set the default selected method of placement for IV based on what the variable is in the aesEncryption class:
        //When looking at the returned string from aesEncryption.getIvLocation()
        //When the string's value (case) is "Prepended to Ciphertext", set the prependToCiphertext radio button to be
        //the default selected button, and then break out of THAT case.
        switch ((aesEncryption.getIvLocation()))
        {
            case "Prepended to Ciphertext":
                prependToCiphertext.setSelected(true);
                break;
            case "Appended to Ciphertext":
                appendToCiphertext.setSelected(true);
                break;
            case "Prepended to Key":
                prependToKey.setSelected(true);
                break;
            case "Appended to Key":
                appendToKey.setSelected(true);
                break;
        }

        ivHighEntropy = new CheckBox("Use mouse coordinates" + "\n"
                                    + "for higher entropy");

        appendToCiphertext.setToggleGroup(ivToggleGroup);
        prependToCiphertext.setToggleGroup(ivToggleGroup);
        appendToKey.setToggleGroup(ivToggleGroup);
        prependToKey.setToggleGroup(ivToggleGroup);
        //Add all the radio menu items to the Toggle group so java can know which
        //once to select and deselect based on the user's input.
        ivChoices.getChildren().addAll(prependToCiphertext, appendToCiphertext, prependToKey, appendToKey, ivHighEntropy);

        ivVbox.getChildren().add(ivLabel);
        ivContainer.getChildren().addAll(ivVbox, ivChoices); //Is an HBox, ivChoices is a VBox

        //************************************************************************************


        layout.getChildren().addAll(label, labelsAndChoiceGadgets, ivContainer);
        layout.setAlignment(Pos.CENTER);
            //Set the widgets/gadgets/content to be centered in the middle of the layout

        //************************************************************************************
        window.setScene(scene);
        window.showAndWait();
            //Displays the window, and before the user can return to the first window, forces the user
            //to close the settings box window
    }
}
