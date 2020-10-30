package quinzical.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import quinzical.avatar.Cosmetic;

/**
 * Avatar class representing a player avatar which can be customised and
 * displayed on various screens of the application.
 */
public class Avatar implements Serializable {

    private static final long serialVersionUID = 1402907673497852680L;

    private Cosmetic slotHat;

    private Cosmetic slotAccessory;

    private Cosmetic slotEyes;

    private boolean forceDisableAnimation;

    public static enum Slot {
        HAT, ACCESSORY, EYES
    }

    /**
     * Empty constructor
     */
    public Avatar() {
        slotHat = null;
        slotAccessory = null;
        slotEyes = null;
        forceDisableAnimation = true;
    }

    /**
     * Constructor with cosmetics
     * 
     * @param hat       the avatar's hat
     * @param accessory the avatar's accessory
     * @param eyes      the avatar's eyes
     */
    public Avatar(Cosmetic hat, Cosmetic accessory, Cosmetic eyes) {
        slotHat = hat;
        slotAccessory = accessory;
        slotEyes = eyes;
        forceDisableAnimation = true;
    }

    /**
     * Set the hat of the avatar
     * 
     * @param hat
     */
    public void setHat(Cosmetic hat) {
        slotHat = hat;
    }

    /**
     * 
     * @return the avatar's hat
     */
    public Cosmetic getHat() {
        return slotHat;
    }

    /**
     * Set the accessory of the avatar
     * 
     * @param accessory
     */
    public void setAccessory(Cosmetic accessory) {
        slotAccessory = accessory;
    }

    /**
     * 
     * @return get the accessory of the avatar
     */
    public Cosmetic getAccessory() {
        return slotAccessory;
    }

    /**
     * Set the eyes of the avatar
     * 
     * @param eyes
     */
    public void setEyes(Cosmetic eyes) {
        slotEyes = eyes;
    }

    /**
     * 
     * @return the eyes of the avatar
     */
    public Cosmetic getEyes() {
        return slotEyes;
    }

    /**
     * Check whether the avatar has a certain cosmetic equipped
     * 
     * @param c
     * @return
     */
    public boolean isEquipped(Cosmetic c) {
        if (c == null) {
            return false;
        }
        return ((slotHat != null && slotHat.equals(c)) || (slotAccessory != null && slotAccessory.equals(c))
                || (slotEyes != null && slotEyes.equals(c)));
    }

    /**
     * Set whether the avatar should have animations or not
     * 
     * @param b
     */
    public void setforceDisableAnimation(boolean b) {
        forceDisableAnimation = b;
    }

    /**
     * @return whether the avatar has animations
     */
    public boolean hasForceDisableAnimation() {
        return forceDisableAnimation;
    }

    /**
     * Convert the avatar to a JSONObject so that it can be sent to the backend
     * server
     * 
     * @return the avatar as a JSONObject
     */
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("hat", slotHat == null ? null : slotHat.toString());
            obj.put("accessory", slotAccessory == null ? null : slotAccessory.toString());
            obj.put("eyes", slotEyes == null ? null : slotEyes.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * Static method to create an avatar from a JSONObject which has been sent to
     * the client by the server.
     * 
     * @param raw The JSONObject as a string
     * @return an Avatar object
     */
    public static Avatar fromJSONObject(String raw) {
        try {
            // if this raises an error, there is a good chance that you need to convert the
            // argument to a string before passing
            JSONObject obj = new JSONObject(raw);

            String hat = null;
            String accessory = null;
            String eyes = null;
            try {
                hat = obj.getString("hat");
            } catch (JSONException e) {
            }
            try {
                accessory = obj.getString("accessory");
            } catch (JSONException e) {
            }
            try {
                eyes = obj.getString("eyes");
            } catch (JSONException e) {
            }

            Cosmetic slotHat = hat != null && hat.length() > 0 ? Cosmetic.valueOf(hat) : null;
            Cosmetic slotAccessory = accessory != null && accessory.length() > 0 ? Cosmetic.valueOf(accessory) : null;
            Cosmetic slotEyes = eyes != null && eyes.length() > 0 ? Cosmetic.valueOf(eyes) : null;

            return new Avatar(slotHat, slotAccessory, slotEyes);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
