package quinzical.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.json.*;

import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import quinzical.components.SelectedCategory;
import quinzical.model.Category;
import quinzical.model.MultiplayerGame;
import quinzical.model.Question;
import quinzical.util.Connect;
import quinzical.util.QuestionBank;
import quinzical.util.Router;

public class CategorySelectMultiplayer extends CategorySelect {

    @FXML
    private GridPane fxSelected;

    @FXML
    private Button fxSubmit;

    @FXML
    private Label fxTitle;

    private final int SLOTS = 5;

    private ObservableList<Category> selected;

    public void initialize() {
        super.initialize();

        selected = getSelected();

        fxSubmit.disableProperty().bind(getDisabled().not());

        fxSubmit.setText("Create lobby");

        for (int i = selected.size(); i < 5; i++) {
            fxSelected.add(SelectedCategory.createTemplate(), i, 0);
        }

        selected.addListener((ListChangeListener<Category>) (c -> {
            if (selected.size() == SLOTS) {
                disableSelection(true);
            } else {
                disableSelection(false);
            }

            fxSelected.getChildren().clear();

            // Display arraylist
            for (int i = 0; i < selected.size(); i++) {
                Category category = selected.get(i);
                Region content = SelectedCategory.create(category, selected);
                fxSelected.add(content, i, 0);
            }

            for (int i = selected.size(); i < 5; i++) {
                fxSelected.add(SelectedCategory.createTemplate(), i, 0);
            }
        }));
    }

    @Override
    public void handleSubmit() {
        MultiplayerGame.startGame(null, true);

        // ArrayList<JSONObject> questions = new ArrayList<>();
        JSONArray questions = new JSONArray();

        for (Category category : selected) {
            for (Question question : category.getQuestions()) {
                questions.put(question.toJSONObject());
            }
        }

        JSONObject json = new JSONObject();
        try {
            json.put("questions", questions);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        Connect connect = Connect.getInstance();
        // maybe we want to send the questions a bit later once the user has decided on the number of questions and stuff
        connect.emit("CREATE_LOBBY", json);
        connect.onMessage("LOBBY_ID", args -> {
            try {
                JSONObject obj = new JSONObject(args[0].toString());
                int code = obj.getInt("code");
                MultiplayerGame.getInstance().setCode(code);
            } catch (JSONException err) {
                err.printStackTrace();
            }
            Router.show(View.LOBBY);
        });
    }
    @FXML
    public void selectRandom(){
        selected.setAll(QuestionBank.getInstance().getRandomCategories(5, false));
    }
}
