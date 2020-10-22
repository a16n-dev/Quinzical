package quinzical.util;

import java.util.List;

import quinzical.model.Ranking;
import quinzical.model.User;

/**
 * This a util class to handle communication to the server for user accounts
 */
public class UserConnect {

    /**
     * 
     * @param username
     * @param password
     * @return
     */
    public static String signUp(String username, String password){
        return null;
    }

    public static String signIn(String username, String password){
        return null;
    }

    public static List<Ranking> getLeaderboardData(){
        return null;
    }

    /**
     * This sends the users data to the server
     * The following information is sent to the server:
     *  - total score
     *  - coins
     *  - avatar
     *  - owned items
     *  - 
     * @param user the user instance to get the data from
     */
    public static void updateUserData(User user){
        //Get data from user
        List<String> unattemptedQuestions;

        //Send data to server
        
    }
        
}
