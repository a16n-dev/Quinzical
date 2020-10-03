package quinzical.util;

import java.util.HashMap;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class Macron {
    private HashMap<String, String> vowels;
    private HashMap<TextField, MacronData> registeredFields;
    private static Macron instance;

    private Macron() {
        vowels = new HashMap<>();
        vowels.put("a", "ā");
        vowels.put("e", "ē");
        vowels.put("i", "ī");
        vowels.put("o", "ō");
        vowels.put("u", "ū");
        
        registeredFields = new HashMap<>();
    }

    public static Macron getInstance() {
        if (instance == null) {
            instance = new Macron();
        }
        return instance;
    }
    
    private String addMacron(String text, int index) {
        var builder = new StringBuilder(text);
        builder.setCharAt(index, vowels.get(Character.toString(text.charAt(index))).charAt(0));
        return builder.toString();
    }

    public void bindToTextField(TextField input) {
        registeredFields.put(input, new MacronData(input.getText()));
        input.setOnKeyTyped(e -> {
            var macronData = registeredFields.get(input);
            String macron = vowels.get(e.getCharacter());
            if (macron != null) {
                String oldText = registeredFields.get(input).text;
                String newText = input.getText();

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
            }
            else {
                macronData.macronIndex = -1;
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
                }
            }
        });
    }
}

class MacronData {
    public String text;
    public int macronIndex;

    public MacronData(String text, int macronIndex) {
        this.text = text;
        this.macronIndex = macronIndex;
    }
    public MacronData(String text) {
        this.text = text;
        this.macronIndex = -1;
    }
}