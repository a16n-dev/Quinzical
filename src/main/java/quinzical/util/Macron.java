package quinzical.util;

import java.text.Normalizer;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class to handle the ability to add Macrons to characters the user types
 */
public class Macron {
    private HashMap<String, String> vowels;
    private HashMap<TextField, MacronData> registeredFields;
    private static Macron instance;

    // singleton code
    private Macron() {
        vowels = new HashMap<>();
        vowels.put("a", "ā");
        vowels.put("e", "ē");
        vowels.put("i", "ī");
        vowels.put("o", "ō");
        vowels.put("u", "ū");
        vowels.put("A", "Ā");
        vowels.put("E", "Ē");
        vowels.put("I", "Ī");
        vowels.put("O", "Ō");
        vowels.put("U", "Ū");

        registeredFields = new HashMap<>();
    }

    /**
     * Get the macron instance
     * 
     * @return
     */
    public static Macron getInstance() {
        if (instance == null) {
            instance = new Macron();
        }
        return instance;
    }

    /**
     * Internal method used to replace a character in a string with the macron
     * version
     */
    private String addMacron(String text, int index) {
        var builder = new StringBuilder(text);
        builder.setCharAt(index, vowels.get(Character.toString(text.charAt(index))).charAt(0));
        return builder.toString();
    }

    /**
     * Takes a text field and it's wrapper and enables macron support on it. This
     * allows the user to press the up arrow key after typing a vowel and have a
     * macron added.
     * 
     * @param fxInput   The input field to add macron support to
     * @param wrapper The wrapper for the input field (to append the GUI element to)
     */
    public void bindToTextField(TextField fxInput, Label fxMacronLetter, VBox fxMacronContainer) {
        registeredFields.put(fxInput, new MacronData(fxMacronLetter, fxInput.getText()));
        fxInput.setOnKeyTyped(e -> {
            var macronData = registeredFields.get(fxInput);
            String macron = vowels.get(e.getCharacter());
            if (macron != null) {
                String oldText = sanitise(registeredFields.get(fxInput).text);
                String newText = sanitise(fxInput.getText());

                int index = -1;
                for (int i = 0; i < Math.min(oldText.length(), newText.length()); i++) {
                    if (Character.compare(oldText.charAt(i), newText.charAt(i)) != 0) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    index = Math.min(oldText.length(), newText.length());
                }
                macronData.macronIndex = index;
                double location = computeTextWidth(fxInput.getFont(), fxInput.getText().substring(0, index), 9999) + 13;
                VBox.setMargin(fxMacronLetter, new Insets(0, 0, 0, location));
                macronData.showHint(macron);
            } else {
                macronData.macronIndex = -1;
                macronData.hideHint();
            }
            macronData.text = fxInput.getText();
        });

        fxInput.setOnKeyPressed(e -> {
            e.consume();
            if (e.getCode() == KeyCode.UP) {
                var macronData = registeredFields.get(fxInput);
                if (macronData.macronIndex != -1) {
                    fxInput.setText(addMacron(fxInput.getText(), macronData.macronIndex));
                    fxInput.positionCaret(macronData.macronIndex + 1);
                    macronData.macronIndex = -1;
                    macronData.hideHint();
                }
            }
        });
    }

    /**
     * Remove all macrons from a sentence
     * 
     * @param str The string to remove macrons from
     * @return Macron-less string
     */
    private String sanitise(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return str.toLowerCase().replaceAll("\\p{M}", "");
    }

    double computeTextWidth(Font font, String text, double wrappingWidth) {
        Text helper = new Text();
        helper.setFont(font);
        helper.setText(text);
        // Note that the wrapping width needs to be set to zero before
        // getting the text's real preferred width.
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);
        double w = Math.min(helper.prefWidth(-1), wrappingWidth);
        helper.setWrappingWidth((int)Math.ceil(w));
        double textWidth = Math.ceil(helper.getLayoutBounds().getWidth());
        return textWidth;
    }
}

/**
 * Class used to store macron input field metadata
 */
class MacronData {
    public String text;
    public int macronIndex;
    private Label hint;

    public MacronData(Label hint, String text, int macronIndex) {
        this.hint = hint;
        this.text = text;
        this.macronIndex = macronIndex;
    }

    public MacronData(Label hint, String text) {
        this.hint = hint;
        this.text = text;
        this.macronIndex = -1;
    }

    /**
     * Display to the user that they can add a macron
     */
    public void showHint(String macron) {
        hint.setText(macron);
        hint.setVisible(true);
    }

    /**
     * Remove macron GUI display
     */
    public void hideHint() {
        hint.setVisible(false);
    }
}