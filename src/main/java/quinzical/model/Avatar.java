package quinzical.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import quinzical.avatar.Cosmetic;

public class Avatar implements Serializable {

    private static final long serialVersionUID = 1402907673497852680L;

    private Cosmetic slotHat;

    private Cosmetic slotAccessory;

    private Cosmetic slotEyes;

    private boolean forceDisableAnimation;

    public static enum Slot {
        HAT, ACCESSORY, EYES
    }

    public Avatar() {
        slotHat = null;
        slotAccessory = null;
        slotEyes = null;
    }

    public Avatar(Cosmetic hat, Cosmetic accessory, Cosmetic eyes) {
        slotHat = hat;
        slotAccessory = accessory;
        slotEyes = eyes;
    }

    public void setHat(Cosmetic hat) {
        slotHat = hat;
    }

    public Cosmetic getHat() {
        return slotHat;
    }

    public void setAccessory(Cosmetic accessory) {
        slotAccessory = accessory;
    }

    public Cosmetic getAccessory() {
        return slotAccessory;
    }

    public void setEyes(Cosmetic eyes) {
        slotEyes = eyes;
    }

    public Cosmetic getEyes() {
        return slotEyes;
    }

    public boolean isEquipped(Cosmetic c) {
        if (c == null) {
            return false;
        }
        return ((slotHat != null && slotHat.equals(c)) || (slotAccessory != null && slotAccessory.equals(c))
                || (slotEyes != null && slotEyes.equals(c)));
    }

    public void setforceDisableAnimation(boolean b) {
        forceDisableAnimation = b;
    }

    public boolean hasForceDisableAnimation() {
        return forceDisableAnimation;
    }

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
