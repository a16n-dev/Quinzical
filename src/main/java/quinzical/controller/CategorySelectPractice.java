package quinzical.controller;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import quinzical.model.Category;
import quinzical.model.PracticeGame;
import quinzical.util.Router;

public class CategorySelectPractice extends CategorySelect{

    @FXML
    private HBox fxSelectedList;

    @FXML
    private Button fxSubmit;

    private final int SLOTS = 1;

    private ObservableList<Category> selected;

    private BooleanProperty disableSubmit;

    public void initialize() {
        disableSubmit = new SimpleBooleanProperty(true);

        fxSubmit.disableProperty().bind(disableSubmit);

        super.initialize();
        selected = getSelected();

        selected.addListener((ListChangeListener<Category>)(c -> {
            
            if(selected.size() == 0){
                disableSubmit.set(true);
            } else {
                disableSubmit.set(false);
            }

            if(selected.size() > 1){
                Platform.runLater(() -> selected.remove(0, selected.size() - 1));
            }

            fxSelectedList.getChildren().clear();

            //Display arraylist
            for(Category category : selected){
                JFXButton button = new JFXButton(category.getName());
                button.setOnAction(v -> {
                    selected.remove(category);
                });
                fxSelectedList.getChildren().add(button);
            }
        }));
    }

    @Override
    public void handleSubmit() {
        PracticeGame.getInstance().setCurrentCategory(selected.get(0));
        Router.show(View.PRACTICE_ANSWER_SCREEN);
    }
}
