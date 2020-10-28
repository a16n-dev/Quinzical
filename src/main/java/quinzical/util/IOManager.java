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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import quinzical.model.Category;
import quinzical.model.Question;

/**
 * Class which handles IO for the rest of the rest of the application (e.g.
 * saving and loading questions and winnings)
 */
public class IOManager {

    private static final String DATAPATH = "./gamedata";

    private static enum Path {
        BASE_CATEGORIES("/categories/"),
        USER_CATEGORIES("/user/categories/"),
        USER_DATA("/user/"),

        ;
        private String path;

        private Path(String path){
            this.path = path;
        }

        public String getPath(){
            return DATAPATH + path;
        }
    }

    /**
     * Load the questions from the file
     * 
     * @param reload Whether to reload from the default settings or load from the
     *               current active game
     * @return The state of the application based on the files
     */
    public static HashMap<String, Category> loadQuestions(String directory) {
        HashMap<String, Category> categoryList = new HashMap<>();

        try {
            // try to load the questions from the file
            if ((new File(directory)).isDirectory()) {
                File[] files = (new File(directory)).listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String categoryName = file.getName();
                        Category category = new Category(categoryName,false);
                        String line;
                        //Iterate over each question
                        while ((line = br.readLine()) != null) {
                            try {
                                String[] parts = line.split("\\|");
                                String id = parts[0];
                                int difficulty = Integer.parseInt(parts[1]);
                                String questionText = parts[2];
                                String questionPrefix = parts[3];
                                String answer = parts[4];
                                Question question = new Question(id, difficulty, questionText, questionPrefix, answer);

                                category.addQuestion(question);
                            } catch (Exception e) {}
                        }
                        br.close();
                        categoryList.put(categoryName,category);
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
        return categoryList;
    }

    public static HashMap<String, Category> getBaseQuestions(){
        return loadQuestions(Path.BASE_CATEGORIES.getPath());
    }

    public static HashMap<String, Category> getUserQuestions(){
        return loadQuestions(Path.USER_CATEGORIES.getPath());
    }

    /**
     * Save the users custom questions to file
     * @param state State to save
     */
    public static void saveUserQuestions(HashMap<String, Category> state) {
        String directory = "userCategories";

        for (String category : state.keySet()) {
            Writer writer = null;
            try {
                writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(directory + "/" + category), "utf-8"));

                for (Question question : state.get(category).getQuestions()) {
                    writer.write(question.toString() + "\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            FileOutputStream file = new FileOutputStream(Path.USER_DATA.getPath() + state.getFileName());
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
            FileInputStream file = new FileInputStream(Path.USER_DATA + state.getFileName());
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            @SuppressWarnings("unchecked")
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
            File f = new File(Path.USER_DATA + state.getFileName());
            f.delete();
            return;
        } catch (Exception e) {
        }
    }
}