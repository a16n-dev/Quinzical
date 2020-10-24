package quinzical.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.ResponseInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.naming.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.scene.control.Label;
import quinzical.controller.View;
import quinzical.model.Ranking;
import quinzical.model.User;

/**
 * This a util class to handle communication to the server for user accounts
 */
public class UserConnect {

    private static final String BASEURL = "http://13.210.217.144:3000";

    private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();

    /**
     * 
     * @param username
     * @param password
     * @return
     */
    public static void signUp(String username, String password, Label feedback) {
        // Create map for data to send
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);

        // Send request and handle response
        post("/register", map, (Function<HttpResponse<String>, Void>) (response -> {
            System.out.println(response.statusCode());

            if (response.statusCode() == 200) {
                // success

                // get response token
                Optional<String> token = response.headers().firstValue("auth-token");
                if(token.isPresent()){
                    User.getInstance().setToken(token.get());
                    User.getInstance().setName(username);
                    Modal.hide();
                    Platform.runLater(() -> {
                        Router.show(View.MAIN_MENU, false);
                    });
                }
            } else if (response.statusCode() == 400) {
                try {
                    JSONObject obj = new JSONObject(response.body());
                    String message = obj.getString("message");
                    Platform.runLater(() -> {
                        feedback.setText(message);
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }));
    }

    /**
     * A method to attempt to authenticate the user with the given credentials
     * 
     * @param username
     * @param password
     */
    public static void signIn(String username, String password, Label feedback) {

        // Create map for data to send
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);

        // Send request and handle response
        post("/login", map, (Function<HttpResponse<String>, Void>) (response -> {
            System.out.println(response.statusCode());
            // Attempt to read response to map
            if (response.statusCode() == 200) {
                Optional<String> token = response.headers().firstValue("auth-token");
                if(token.isPresent()){
                    User.getInstance().setToken(token.get());
                    User.getInstance().setName(username);
                    Modal.hide();
                    Platform.runLater(() -> {
                        Router.show(View.MAIN_MENU, false);
                    });
                    
                }
            } else if (response.statusCode() == 400) {
                try {
                    JSONObject obj = new JSONObject(response.body());
                    String message = obj.getString("message");
                    Platform.runLater(() -> {
                        feedback.setText(message);
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                // Login failed
            }
            return null;
        }));
    }

    public static void getLeaderboardData(Function<List<Ranking>, Void> handler) {

        String token = User.getInstance().getToken();
        
        System.out.println(token);

        var request = HttpRequest.newBuilder(URI.create(BASEURL + "/leaderboard")).header("auth-token", User.getInstance().getToken())
        .header("Content-Type", "application/json").header("accept", "application/json").GET().build();

        client.sendAsync(request, BodyHandlers.ofString()).thenApply((Function<HttpResponse<String>, Void>) (response -> {

            try {
                JSONObject obj = new JSONObject(response.body());

                int rank = obj.getInt("yourPlace");

                JSONArray rankingData = obj.getJSONArray("leaderboard");

                for (int i = 0; i < rankingData.length(); i++) {
                    System.out.println(rankingData.getString(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }));
    }

    /**
     * This sends the users data to the server The following information is sent to
     * the server: - total score - coins - avatar - owned items -
     * 
     * @param user the user instance to get the data from
     */
    public static void updateUserData(User user) {
        // Get data from user
        List<String> unattemptedQuestions;

        // Send data to server

    }

    /**
     * This is a wrapper function for a generic post request, sending json data to
     * the server.
     * 
     * @param route the route to send the post request to
     * @param body  a map representing key value pairs of data to send
     * @param fn    callback function to handle the response
     */
    private static void post(String route, HashMap<String, String> body, Function<HttpResponse<String>, Void> fn) {
        JSONObject requestBody = new JSONObject(body);
        // String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);

        // create a request
        var request = HttpRequest.newBuilder(URI.create(BASEURL + route)).POST(BodyPublishers.ofString(requestBody.toString()))
                .header("Content-Type", "application/json").header("accept", "application/json").build();

        // Send request
        client.sendAsync(request, BodyHandlers.ofString()).thenApply(fn);
    }

}
