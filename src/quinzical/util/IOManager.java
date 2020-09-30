package quinzical.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import quinzical.model.Question;

/**
 * Class which handles IO for the rest of the rest of the application (e.g.
 * saving and loading questions and winnings)
 */
public class IOManager {
    /**
     * Load the questions from the file
     * 
     * @param reload Whether to reload from the default settings or load from the
     *               current active game
     * @return The state of the application based on the files
     */
    public static HashMap<String, ArrayList<Question>> loadQuestions(boolean reload) {
        HashMap<String, ArrayList<Question>> state = new HashMap<>();

        // choose the directory
        String directory = (reload ? "categories" : "categories_current");

        try {
            // try to load the questions from the file
            if ((new File(directory)).isDirectory()) {
                File[] files = (new File(directory)).listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String categoryName = file.getName();
                        String line;
                        while ((line = br.readLine()) != null) {
                            try {
                                String[] parts = line.split(",");
                                int value = Integer.parseInt(parts[0]);
                                String questionText = parts[1];
                                String answer = parts[2];
                                Question question = new Question(value, questionText, answer);

                                ArrayList<Question> questions = state.get(categoryName);
                                if (questions == null) {
                                    questions = new ArrayList<Question>();
                                    state.put(categoryName, questions);
                                }
                                questions.add(question);
                            } catch (Exception e) {
                                System.out.println(line + " is not a valid question");
                            }
                        }
                        br.close();
                    }
                }
            }
            // otherwise, make the directory
            else {
                File folder = new File(directory);
                folder.mkdir();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }

    /**
     * Save the state to the file
     * 
     * @param state State to save
     */
    public static void saveQuestions(HashMap<String, ArrayList<Question>> state) {
        String directory = "categories_current";

        for (String category : state.keySet()) {
            Writer writer = null;
            try {
                writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(directory + "/" + category), "utf-8"));

                for (Question question : state.get(category)) {
                    writer.write(question.toString() + "\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load the winnings from the file. If the file can't be found, return 0
     * 
     * @return The winnings
     */
    public static int loadWinnings() {
        File file = new File(".winnings");
        if (!file.exists()) {
            saveWinnings(0);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String winnings = br.readLine();
            br.close();
            return Integer.parseInt(winnings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Save the winnings to the file
     * 
     * @param winnings The winnings to save
     */
    public static void saveWinnings(int winnings) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(".winnings"), "utf-8"));
            writer.write(Integer.toString(winnings));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}