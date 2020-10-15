package quinzical.util;

import java.text.Normalizer;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Macron {
    private static Macron instance;
    private HashMap<Character, Character> pairs;
    private TextField fxInput;
    private String oldText;
    private int index;

    private Macron() {
        pairs = new HashMap<Character, Character>();
        pairs.put('a', 'ā');
        pairs.put('e', 'ē');
        pairs.put('i', 'ī');
        pairs.put('o', 'ō');
        pairs.put('u', 'ū');
        pairs.put('A', 'Ā');
        pairs.put('E', 'Ē');
        pairs.put('I', 'Ī');
        pairs.put('O', 'Ō');
        pairs.put('U', 'Ū');

        oldText = "";
        index = -1;
    }

    public static Macron getInstance() {
        if (instance == null) {
            instance = new Macron();
        }
        return instance;
    }

    private String replaceWithMacron(String text, int index) {
        var builder = new StringBuilder(text);
        System.out.println(index);
        System.out.println(text);
        System.out.println(pairs.get(text.charAt(index)));
        builder.setCharAt(index, pairs.get(text.charAt(index)));
        return builder.toString();
    }

    public void bind(TextField fxInput, Label fxMacronLetter, VBox fxMacronContainer) {
        this.fxInput = fxInput;

        fxInput.setOnKeyTyped(e -> {
            Character macron = pairs.get(e.getCharacter().charAt(0));
            String newText = fxInput.getText();
            if (macron != null) {
                index = firstDifferenceIndex(oldText, newText);
                double displayLocation = computeTextWidth(newText.substring(0, index));
                positionGUI(fxMacronLetter, displayLocation);

                fxMacronLetter.setText(Character.toString(macron));
                fxMacronLetter.setVisible(true);
            }
            else {
                fxMacronLetter.setVisible(false);
                index = -1;
            }
            oldText = newText;
        });

        fxInput.setOnKeyPressed(e -> {
            e.consume();
            if (e.getCode() == KeyCode.UP) {
                if (index != -1) {
                    String newText = replaceWithMacron(fxInput.getText(), index);
                    fxInput.setText(newText);
                    fxInput.positionCaret(index + 1);
                    index = -1;
                    fxMacronContainer.setVisible(false);
                    oldText = newText;
                    System.out.println("fuck you " + oldText);
                }
            }   
        });
    }

    private double computeTextWidth(String text) {
        double wrappingWidth = Double.MAX_VALUE;
        Font font = fxInput.getFont();

        Text helper = new Text();
        helper.setFont(font);
        helper.setText(text);
        // Note that the wrapping width needs to be set to zero before
        // getting the text's real preferred width.
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);
        double w = Math.min(helper.prefWidth(-1), wrappingWidth);
        helper.setWrappingWidth((int) Math.ceil(w));
        double textWidth = Math.ceil(helper.getLayoutBounds().getWidth());
        return textWidth;
    }

    private void positionGUI(Label fxLabel, double location) {
        System.out.println(location);
        VBox.setMargin(fxLabel, new Insets(0, 0, 0, location + 13));
    }

    private int firstDifferenceIndex(String a, String b) {
        System.out.println("-----");
        System.out.println(a + " " + b);
        int i = -1;
        int smallestLength = Math.min(a.length(), b.length());
        for (int j = 0; j < smallestLength; j++) {
            if (Character.compare(a.charAt(j), b.charAt(j)) != 0) {
                i = j;
                break;
            }
        }
        System.out.println(i == -1 ? smallestLength : i);
        System.out.println("----");
        return i == -1 ? smallestLength : i;
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
