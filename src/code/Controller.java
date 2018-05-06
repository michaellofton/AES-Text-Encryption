package code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.datatransfer.*;
import java.util.Objects;
import javafx.scene.image.Image;

//TEXT AREA:
// http://www.java2s.com/Code/Java/JavaFX/UsingTextAreatodisplaymorelines.htm


public class Controller
{
    @FXML private Label programTitle;
    @FXML private Text processTime;
    @FXML private Text timeText = new Text();

    @FXML private Text unprocessedMessageErrorText;
    @FXML private Label messageToEncryptDecryptLabel = new Label();
    @FXML private Button copyUnprocessedMessageButton = new Button();
    @FXML private Button pasteUnprocessedMessageButton = new Button();
    @FXML private TextArea unprocessedTextArea = new TextArea();

    @FXML private Text keyErrorText;
    @FXML private Label resultedMessageLabel;
    @FXML private Button copyResultedMessageButton;
    @FXML private TextArea resultingTextArea;

    @FXML private Label keyLabel;
    @FXML private TextArea keyTextArea;
    @FXML private Button copyKeyButton;
    @FXML private Button pasteKeyButton;
    /*
    @FXML private Label ivLabel;
    @FXML private TextArea ivTextArea;
    @FXML private Button copyIvButton;
    */

    @FXML private Button encryptButton;
    @FXML private Button decryptButton;

    @FXML private void displayInfoBox()
    {
        Image infoIcon = new javafx.scene.image.Image("file:res/info.png");
        String infoString =
                "Software Info:" + "\n" +
                "For accurate results, encrypt something first" + "\n\n" +
                "Version:\t\t\t" + aesEncryption.getVersion() + "\n" +
                "Developer:\t\t" + aesEncryption.getDeveloper() + "\n" +
                "Algorithm:\t\t" + aesEncryption.getAlgorithm() + "\n" +
                "Keysize:\t\t\t" + aesEncryption.getKeySize() + "\n" +
                "Mode:\t\t\t" + aesEncryption.getMode() + "\n" +
                "Padding:\t\t\t" + aesEncryption.getPadding() + "\n" +
                "Provider:\t\t\t" + aesEncryption.getProvider() + "\n" +
                "Text Encoding:\t\t" + aesEncryption.getTextEncoding() + "\n" +
                "IV Location:\t\t" + aesEncryption.getIvLocation() + "\n" +
                "Cipher Size:\t\t" + aesEncryption.getCipherSize() + "\n" +
                "Key Settings:\t\t" + aesEncryption.getKeySettings();
        AlertBox.display("Software Info", infoString, infoIcon);
    }

    @FXML private void displaySettingsBox()
    {
        String settingsString = "These button's don't do anything right now! Yay!" + "\n" +
                                "  Change the program's behavior:";

        SettingsBox.display("Software Settings", settingsString);
    }

    @FXML private void displayHelpBox()
    {
        Image helpIcon = new Image("file:res/help.png");

        String helpString = "\tIf you need help, you've come to the right place." + "\n\n" +

                            "\tThis program is a text encryption program" + "\n" +
                            "\tthat implements the Advanced Encryption" + "\n" +
                            "\tStandard algorithm to mix up, randomize," + "\n" +
                            "\ttranspose, alter, change, and re-order" + "\n" +
                            "\tthe message that you, the user, give the" + "\n" +
                            "\tprogram, so that no hackers or creepers" + "\n" +
                            "\tcan read your messages without your approval." + "\n\n" +

                            "\tTo encrypt your message, just type something" + "\n" +
                            "\tin the top white box area and press 'Encrypt'." + "\n\n" +

                            "\tTo decrypt a message you were given, paste" + "\n" +
                            "\tthe encrypted message in the top white box area," + "\n" +
                            "\tthen paste the key you were given in the white box" + "\n" +
                            "\tarea labeled 'Key:', and finally press 'Decrypt'." + "\n\n" +

                            "\tTo send a message to someone, just copy the"+ "\n" +
                            "\tencrypted message and the key and paste them both" + "\n" +
                            "\tinto an email, or a private message on social media" + "\n" +
                            "\t(Or wherever you're communicating). When they get your" + "\n" +
                            "\tkey and encrypted message, they can paste it into their" + "\n" +
                            "\tversion of this software, press 'Decrypt' and receive your" + "\n" +
                            "\tmessage securely and safely.";

        AlertBox.display("Help", helpString, helpIcon);
    }

    //When calling function, can set clipboardOwner parameter to null,
    //and by default it will use the local machine's clipboard.
    public void copyToClipboard(String textToCopy, ClipboardOwner user)
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //Create & get the clipboard from the computer
        Transferable selectedText = new StringSelection(textToCopy); //Make the text selected
        clipboard.setContents(selectedText, user); //Copy & Write the selected text to the user's clipboard
    }

    public String pasteFromClipboard(ClipboardOwner user)
    {
        String returnString = "null. This is an error.";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //Create & get the clipboard from the computer
        try
        {
            returnString = (String)(clipboard.getData(DataFlavor.stringFlavor));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            returnString = "Error when trying to paste from clipboard: " + e.toString();
        }
        return returnString;

    }

    public void handle(Button button)
    {
        //Impelment handle method from ActionEvent class
        //will be called whenever use clicks a button.
        //Called an "EVENT" when a user clicks on the button and something happens
        //The code in this method will determine what to do after user has clicked a button.

        if (button.equals(copyUnprocessedMessageButton))
        {
            copyToClipboard(unprocessedTextArea.getText(), null);
        }
        else if (button.equals(pasteUnprocessedMessageButton))
        {
            unprocessedTextArea.setText(pasteFromClipboard(null));
        }
        else if (button.equals(copyResultedMessageButton))
        {
            copyToClipboard(resultingTextArea.getText(), null);
        }
        else if (button.equals(copyKeyButton))
        {
            copyToClipboard(keyTextArea.getText(), null);
        }
        else if (button.equals(pasteKeyButton))
        {
            keyTextArea.setText(pasteFromClipboard(null));
        }
        /*
        else if (button.equals(copyIvButton))
        {
            copyToClipboard(ivTextArea.getText(), null);
        }
        */
        else if (button.equals(encryptButton))
        {
            timeText.setText("");
            unprocessedMessageErrorText.setText("");
            String message = unprocessedTextArea.getText();

            aesEncryption encryptObj = new aesEncryption();
            String encryptionCiphertext = encryptObj.aesEncrypt(message);

            resultingTextArea.setText(encryptionCiphertext);
            keyTextArea.setText(encryptObj.getKey());
            keyErrorText.setText("");

            messageToEncryptDecryptLabel.setText("Message to Encrypt:");
            resultedMessageLabel.setText("Encrypted Message:");

            timeText.setFill(Color.DARKGREEN);
            timeText.setStyle("-fx-font-weight: bold;");
            timeText.setText(encryptObj.getEncryptionTimeMilliSec() + " milliseconds.");
            processTime.setText("Encrypting Time: ");

        }
        else if (button.equals(decryptButton))
        {
            unprocessedMessageErrorText.setText("");

            aesEncryption decryptObj = new aesEncryption();

            String ciphertextToDecrypt = unprocessedTextArea.getText();

            //Recreate secret key and iv bytes to give to decryption method
            //Call the methods that give the encoded aes key and encoded IV
            //Decode the encoded key and iv so that we just get their bytes
            //Create a new secret key using the decoded aes key bytes, and pass the iv bytes into the decryption method.
            byte[] secretKey = decryptObj.decodeSecretKeyBytes(keyTextArea.getText().getBytes());
            //byte[] ivBytes = decryptObj.decodeIVBytes(ivTextArea.getText().getBytes());

            //String decryptedMessage = decryptObj.aesDecrypt(secretKey, ivBytes, ciphertextToDecrypt);
            String decryptedMessage = decryptObj.aesDecrypt(secretKey, ciphertextToDecrypt);

            if (!decryptObj.isValidKey()) //If the value returned is NOT true (so, false)
            {
                keyErrorText.setFill(Color.RED); //Color.CRIMSON
                keyErrorText.setStyle(" -fx-font-weight: bold; -fx-font-size: 14;");
                //keyErrorText.setAlignment(Pos.CENTER);
                keyErrorText.setText("ERROR: This key isn't the right key for the message.");
            }

            if (decryptObj.isValidKey())
            {
                resultingTextArea.setText(decryptedMessage);
            }


            messageToEncryptDecryptLabel.setText("Message to Decrypt:");
            resultedMessageLabel.setText("Decrypted Message:");

            timeText.setFill(Color.DARKCYAN);
            timeText.setStyle("-fx-font-weight: bold;");
            timeText.setText(decryptObj.getDecryptionTimeMilliSec() + " milliseconds.");
            processTime.setText("Decrypting Time: ");
        }
    }


    @FXML private void handleButtonClick_copyUnprocessedMessage()
    {
        //Copy all the data in the text area to clipboard
        handle(copyUnprocessedMessageButton);
    }

    @FXML private void handleButtonClick_pasteUnprocessedMessage()
    {
        //Paste clipboard text to text area object for encryption
        handle(pasteUnprocessedMessageButton);
    }

    @FXML private void handleButtonClick_copyResultedMessage()
    {
        //Copies to the clipboard the entire base64 encoded encrypted message
        handle(copyResultedMessageButton);
    }

    @FXML private void handleButtonClick_copyKey()
    {
        //Copy the base64 encoded AES key data to the clipboard without selecting it
        handle(copyKeyButton);
    }

    @FXML private void handleButtonClick_pasteKey()
    {
        //Paste clipboard text to text area object for encryption
        handle(pasteKeyButton);
    }

    /*
    @FXML private void handleButtonClick_copyInitVec()
    {
        //Copy the base64 encoded AES IV data to the clipboard without selecting it
        handle(copyIvButton);
    }*/

    @FXML private void handleButtonClick_encryptMessage()
    {
        if (unprocessedTextArea.getText().equals(""))
        {
            unprocessedMessageErrorText.setFill(Color.RED); //Color.CRIMSON
            unprocessedMessageErrorText.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            unprocessedMessageErrorText.setText("ERROR: There is no message to encrypt!? Enter a message!");
        }
        else
        {
            unprocessedMessageErrorText.setText("");
            handle(encryptButton);
        }
    }
    @FXML private void handleButtonClick_decryptMessage()
    {
        if (unprocessedTextArea.getText().equals(""))
        {
            unprocessedMessageErrorText.setFill(Color.RED); //Color.CRIMSON
            unprocessedMessageErrorText.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            unprocessedMessageErrorText.setText("ERROR: Where is the message I need to decrypt? WHERE?!");
        }
        else if (keyTextArea.getText().equals(""))
        {
            if (!unprocessedMessageErrorText.getText().equals(""))
            {
                unprocessedMessageErrorText.setText("");
            }
            keyErrorText.setFill(Color.RED); //Color.CRIMSON
            keyErrorText.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            //keyErrorText.setAlignment(Pos.CENTER);
            keyErrorText.setText("ERROR: You didn't enter a key. I NEED a key.");
        }
        else //Shows error when key IS the right key
        //If keytextarea has space, but isn't the right key
        {
            unprocessedMessageErrorText.setText("");
            keyErrorText.setText("");
            handle(decryptButton);
        }
    }

}
