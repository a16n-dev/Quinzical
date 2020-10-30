package quinzical.controller.menu;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import quinzical.controller.View;
import quinzical.controller.component.SelectedCategory;
import quinzical.model.Category;
import quinzical.model.PracticeGame;
import quinzical.util.QuestionBank;
import quinzical.util.Router;

/**
 * The category select controller for the practice mode
 */
public class CategorySelectPractice extends CategorySelect{

    @FXML
    private GridPane fxSelected;

    @FXML
    private Button fxSubmit;

    @FXML
    private Label fxTitle;

    @FXML
    private HBox fxContainer;

    private ObservableList<Category> selected;

    private BooleanProperty disableSubmit;

    public void initialize() {
        disableSubmit = new SimpleBooleanProperty(true);

        fxSubmit.disableProperty().bind(disableSubmit);

        super.initialize();
        selected = getSelected();

        fxContainer.setMaxWidth(250);
        fxTitle.setText("SELECT CATEGORY");

        setContent(SelectedCategory.createTemplate());

        selected.addListener((ListChangeListener<Category>)(c -> {
            
            if(selected.size() == 0){
                disableSubmit.set(true);
            } else {
                disableSubmit.set(false);
            }

            if(selected.size() > 1){
                Platform.runLater(() -> selected.remove(0, selected.size() - 1));
            }

            fxSelected.getChildren().clear();

            //Display arraylist
                if(selected.size() > 0){
                    Region content = SelectedCategory.create(selected.get(0), selected);
                    setContent(content);
                }else {
                    setContent(SelectedCategory.createTemplate());
                }
        }));
    }

    @Override
    public void handleSubmit() {
        PracticeGame.getInstance().setCurrentCategory(selected.get(0));
        Router.show(View.PRACTICE_ANSWER_SCREEN);
    }

    /**
     * Set the content of a node
     * @param node the node to set the content of
     */
    private void setContent(Node node){
        fxSelected.add(node, 0, 0);
        GridPane.setColumnSpan(node, 5);
    }

    @FXML
    public void selectRandom(){
        selected.setAll(QuestionBank.getInstance().getRandomCategories(1, false));
    }
}
