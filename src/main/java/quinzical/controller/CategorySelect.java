package quinzical.controller;

import java.util.ArrayList;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import quinzical.components.CategoryListItem;
import quinzical.model.Category;
import quinzical.util.QuestionBank;

public class CategorySelect {

    @FXML
    private JFXMasonryPane fxBaseCategoryContainer;

    @FXML
    private JFXMasonryPane fxUserCategoryContainer;

    public void initialize() {
        // Retrieve list of all categories as an arrayList
        ArrayList<Category> categories = QuestionBank.getInstance().getCategories();
        System.out.println(categories);
        for(Category c : categories){
            Region content = CategoryListItem.create(c);
            if(c.isUserCreated()){
                fxUserCategoryContainer.getChildren().add(content);
            } else {
                fxBaseCategoryContainer.getChildren().add(content);

            }
        }
    }

}
