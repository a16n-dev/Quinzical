package quinzical.util;

import java.text.Normalizer;
import java.util.HashMap;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
     * @param input   The input field to add macron support to
     * @param wrapper The wrapper for the input field (to append the GUI element to)
     */
    public void bindToTextField(TextField input, VBox wrapper) {
        Label macronHint = new Label("Press the up arrow to add a macron!");
        macronHint.setVisible(false);
        macronHint.setFont(new Font(20));
        macronHint.setTextFill(Color.rgb(255, 255, 255));
        wrapper.getChildren().add(macronHint);

        registeredFields.put(input, new MacronData(macronHint, input.getText()));
        input.setOnKeyTyped(e -> {
            var macronData = registeredFields.get(input);
            String macron = vowels.get(e.getCharacter());
            if (macron != null) {
                String oldText = sanitise(registeredFields.get(input).text);
                String newText = sanitise(input.getText());

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
                macronData.showHint();
            } else {
                macronData.macronIndex = -1;
                macronData.hideHint();
            }
            macronData.text = input.getText();
        });

        input.setOnKeyPressed(e -> {
            e.consume();
            if (e.getCode() == KeyCode.UP) {
                var macronData = registeredFields.get(input);
                if (macronData.macronIndex != -1) {
                    input.setText(addMacron(input.getText(), macronData.macronIndex));
                    input.positionCaret(macronData.macronIndex + 1);
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
    public void showHint() {
        hint.setVisible(true);
    }

    /**
     * Remove macron GUI display
     */
    public void hideHint() {
        hint.setVisible(false);
    }
}