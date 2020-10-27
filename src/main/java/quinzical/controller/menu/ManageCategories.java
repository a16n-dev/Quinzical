package quinzical.controller.menu;

import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import quinzical.model.Category;
import quinzical.model.Question;

/**
 * This allows the user to add their own custom categories and edit the
 * quesitions in them. This handles adding, changing and removing questions and
 * categories. It also ensures that this information is correctly reflected in
 * the table views.
 */
public class ManageCategories {

    // Define components in view
    @FXML
    private TableView<String> categoryTable;
    @FXML
    private TableColumn<String, String> categoryNameCol;
    @FXML
    private TableColumn<String, Integer> categoryQCountCol;
    @FXML
    private TableView<Question> questionTable;
    @FXML
    private TableColumn<Question, String> qQuestionCol;
    @FXML
    private TableColumn<Question, String> qAnswerCol;
    @FXML
    private TableColumn<Question, Integer> qValCol;

    @FXML
    private Button addQButton;

    @FXML
    private Button removeQButton;

    @FXML
    private Button removeCButton;

    private ObservableMap<String, ObservableList<Question>> categories;

    private String category;

    public void initialize() {
        // Retrieve categories from question bank
        categories = FXCollections.observableHashMap();

    }

    private void showCategories() {
        // Set cell values

        categoryNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        categoryQCountCol
                .setCellValueFactory(cellData -> (Bindings.size(categories.get(cellData.getValue()))).asObject());

        categoryNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        categoryTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                category = newValue;
                showQuestions();

                // Enable buttons
                enableQuestionButtonBar(false);
            } else {
                questionTable.setItems(null);
                enableQuestionButtonBar(true);
            }
        });
    }

    /**
     * Takes a category and displays all questions in the category in the
     * questionTable view
     * 
     * @param category
     */
    private void showQuestions() {
        questionTable.setItems(categories.get(category));

        qQuestionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHint()));
        qAnswerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnswer()));
        qValCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

        qQuestionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        qAnswerCol.setCellFactory(TextFieldTableCell.forTableColumn());
        qValCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        questionTable.editingCellProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // IOHelper.writeUser(_user);
            }
        });
    }

    @FXML
    private void newQuestion() {
        categories.get(category).add(new Question());
        questionTable.getSelectionModel().selectLast();
    }

    @FXML
    private void deleteSelectedQuestion() {
        Question q = questionTable.getSelectionModel().getSelectedItem();
        categories.get(category).remove(q);
    }

    @FXML
    private void LoadCategoryFromFile() {
        // IOHelper.loadFromFile(_app);
    }

    @FXML
    private void deleteCategory() {
        // Category c = categoryTable.getSelectionModel().getSelectedItem();
        // // If category contains questions prompt for confirmation
        // if (c.getQuestions().size() > 0) {
        // Alert alert = new Alert(AlertType.CONFIRMATION);
        // alert.setTitle("Delete " + c.getName().getValue());
        // alert.setHeaderText("Are you sure you want to delete " +
        // c.getName().getValue() + "?");
        // alert.setContentText("This will also delete all " + c.getQuestions().size() +
        // " questions in the category");

        // Optional<ButtonType> result = alert.showAndWait();
        // if (result.get() == ButtonType.OK) {
        // _user.removeCategory(c);
        // IOHelper.writeUser(_user);
        // }
        // } else {
        // _user.removeCategory(c);
        // IOHelper.writeUser(_user);
        // }
    }

    @FXML
    private void createCategory() {
        TextInputDialog dialog = new TextInputDialog("Animals");
        dialog.setTitle("Create new Category");
        dialog.setHeaderText("Enter a name");
        dialog.setContentText("Category name:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            categories.put(name, FXCollections.observableArrayList());
            categoryTable.getSelectionModel().selectLast();
        });
    }

    /**
     * Helper method to disable and enable multiple buttons All of these buttons
     * should be disabled when no category is selected
     * 
     * @param b
     */
    private void enableQuestionButtonBar(boolean b) {
        addQButton.setDisable(b);
        removeQButton.setDisable(b);
        removeCButton.setDisable(b);
    }

}