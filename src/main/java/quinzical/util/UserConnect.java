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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.scene.control.Label;
import quinzical.controller.View;
import quinzical.model.Ranking;
import quinzical.model.User;

/**
 * This a util class to handle communication to the server for user accounts
 */
public class UserConnect {

    private static final String BASEURL = "http://localhost:3000";

    private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();

    private static final ObjectMapper objectMapper = new ObjectMapper();

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

            try {
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
                    Map<String, String> ResponseMap = objectMapper.readValue(response.body(), Map.class);
                    Platform.runLater(() -> {
                        feedback.setText(ResponseMap.get("message"));
                    });
                }
            } catch (IOException e) {
                Platform.runLater(() -> {
                    feedback.setText("An error occurred");
                });
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
            try {
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
                    Map<String, String> ResponseMap = objectMapper.readValue(response.body(), Map.class);
                    // Login failed
                    Platform.runLater(() -> {
                        feedback.setText(ResponseMap.get("message"));
                    });
                }

            } catch (IOException e) {
                Platform.runLater(() -> {
                    feedback.setText("An error occurred");
                });
            }
            return null;
        }));
    }

    public static List<Ranking> getLeaderboardData() {
        return null;
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

        try {
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);

            // create a request
            var request = HttpRequest.newBuilder(URI.create(BASEURL + route)).POST(BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json").header("accept", "application/json").build();

            // Send request
            client.sendAsync(request, BodyHandlers.ofString()).thenApply(fn);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
