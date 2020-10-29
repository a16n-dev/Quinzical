package quinzical.controller.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldTableCell;
import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.util.Modal;
import quinzical.util.QuestionBank;

/**
 * This allows the user to add their own custom categories and edit the
 * quesitions in them. This handles adding, changing and removing questions and
 * categories. It also ensures that this information is correctly reflected in
 * the table views.
 */
public class ManageCategories {

    // Define components in view
    @FXML
    private TableView<String> fxCategoryTable;
    @FXML
    private TableColumn<String, String> fxCategoryNameCol;
    @FXML
    private TableView<Question> fxQuestionTable;
    @FXML
    private TableColumn<Question, String> fxQuestionCol;
    @FXML
    private TableColumn<Question, String> fxAnswerCol;
    @FXML
    private TableColumn<Question, Integer> fxValCol;

    @FXML
    private TextArea fxClueInput;

    @FXML
    private TextField fxPrefixInput;

    @FXML
    private TextField fxAnswerField;

    @FXML
    private TextField fxDifficultyField;

    /**
     * Stores a list of questions in each category, where the key is the category
     * name
     */
    private ObservableMap<String, ObservableList<Question>> questions;

    /**
     * Stores a list of category names for the categories the user has created
     */
    private ObservableList<String> categories;

    /**
     * Stores the currently selected category
     */
    private String category;

    /**
     * Stores the currently selected question
     */
    private Question question;

    public void initialize() {

        questions = FXCollections.observableHashMap();
        categories = FXCollections.observableArrayList();

        // Retrieve categories from question bank
        for(Category c : QuestionBank.getInstance().getUserCategories()){
            System.out.println("bruh");
            questions.put(c.getName(), FXCollections.observableArrayList(c.getQuestions()));
            categories.add(c.getName());
        };


        showCategories();
    }

    private void showCategories() {
        // Link table
        fxCategoryTable.setItems(categories);

        // Map column to data
        fxCategoryNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        fxCategoryNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        // Listener for the user selecting a row
        fxCategoryTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                category = newValue;
                showQuestions();

                // Enable buttons
                enableQuestionButtonBar(false);
            } else {
                fxQuestionTable.setItems(null);
                enableQuestionButtonBar(true);
            }
        });
    }

    /**
     * Takes the currently selected category and displays the list of all questions
     * in that category in the question table
     */
    private void showQuestions() {
        // Link table
        fxQuestionTable.setItems(questions.get(category));

        // Map columns to data
        fxQuestionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHint()));
        fxAnswerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnswer()));
        fxValCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

        fxQuestionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                question = newValue;
                showQuestionDetails();
            }
        });
    }

    /**
     * Creates a new question in the currently selected category
     */
    @FXML
    private void newQuestion() {
        questions.get(category).add(new Question());
        fxQuestionTable.getSelectionModel().selectLast();
    }

    /**
     * Deletes the question which is currently selected
     */
    @FXML
    private void deleteQuestion() {
        questions.get(category).remove(question);
        updateQuestionBank();
    }

    /**
     * Deletes the category which is currently selected. If the category contains
     * questions then the user will be prompted to confirm the deletion
     */
    @FXML
    private void deleteCategory() {
        if(category == null){
            return;
        }
        // If category contains questions prompt for confirmation
        if (questions.get(category).size() > 0) {
            Modal.confirmation("Delete" + category + "?", "This will delete all questions in the category", e -> {
                questions.remove(category);
                categories.remove(category);
                updateQuestionBank();
            });
        } else {
            questions.remove(category);
            categories.remove(category);
            updateQuestionBank();
        }
    }

    /**
     * Displays a dialog to let the user create a new category
     */
    @FXML
    private void createCategory() {
        TextInputDialog dialog = new TextInputDialog("Animals");
        dialog.setTitle("Create new Category");
        dialog.setHeaderText("Enter a name");
        dialog.setContentText("Category name:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            questions.put(name, FXCollections.observableArrayList());
            categories.add(name);
            fxCategoryTable.getSelectionModel().selectLast();
        });
    }

    private void enableQuestionButtonBar(boolean b) {
        // addQButton.setDisable(b);
        // removeQButton.setDisable(b);
        // removeCButton.setDisable(b);
    }

    /**
     * Displays the details of the selected question in the form
     */
    @FXML
    private void showQuestionDetails() {
        fxClueInput.setText(question.getHint());
        fxPrefixInput.setText(question.getPrefix());
        fxAnswerField.setText(question.getAnswer());
        fxDifficultyField.setText(Integer.toString(question.getDifficulty()));
    }

    /**
     * Saves the changes the user has made to the question
     */
    @FXML
    private void saveChanges() {
        String id = question.getId();
        String clue = fxClueInput.getText();
        String prefix = fxPrefixInput.getText();
        String answer = fxAnswerField.getText();
        String difficulty = fxDifficultyField.getText();
        Question q = new Question(id, Integer.parseInt(difficulty), clue, prefix, answer);
        int index = questions.get(category).indexOf(question);
        questions.get(category).set(index, q);


        updateQuestionBank();
    }

    private void updateQuestionBank(){
        // Put logic here to save to question bank
        HashMap<String, Category> userCategories = new HashMap<String, Category>();

        for (Map.Entry<String, ObservableList<Question>> entry : questions.entrySet()) {
            Category c = new Category(entry.getKey());
            c.addQuestion(entry.getValue());
            userCategories.put(entry.getKey(), c);
        }

        QuestionBank.getInstance().setUserCategories(userCategories);
    }

}