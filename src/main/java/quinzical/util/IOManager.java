package quinzical.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import quinzical.model.Question;

/**
 * Class which handles IO for the rest of the rest of the application (e.g.
 * saving and loading questions and winnings)
 */
public class IOManager {

    private static String DATAPATH = "./gamedata/";
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
                                String[] parts = line.split("\\|");
                                int difficulty = Integer.parseInt(parts[0]);
                                String questionText = parts[1];
                                String questionPrefix = parts[2];
                                String answer = parts[3];
                                Question question = new Question(difficulty, questionText, questionPrefix, answer);

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

    /**
     * Writes the specified object into the state variable.
     */
    public static void writeState(State state, Object obj) {
        try {
            Files.createDirectories(Paths.get(DATAPATH));
        } catch (Exception e) {
        }
        ;

        try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(DATAPATH + state.getFileName());
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(obj);

            out.close();
            file.close();
        }

        catch (IOException ex) {
        }
    }

    /**
     * @return the object in state with the given name, or null if none exists
     */
    public static <T> T readState(State state) {
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(DATAPATH + state.getFileName());
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            T obj = (T) in.readObject();

            in.close();
            file.close();
            return obj;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Clears the specified state
     * 
     * @param state @see quinzical.util.State
     */
    public static void clearState(State state) {
        try {
            File f = new File(DATAPATH + state.getFileName());
            f.delete();
            return;
        } catch (Exception e) {
        }
    }
}