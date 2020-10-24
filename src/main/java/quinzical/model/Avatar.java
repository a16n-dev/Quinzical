package quinzical.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import quinzical.avatar.Accessory;
import quinzical.avatar.Cosmetic;
import quinzical.avatar.Eyes;
import quinzical.avatar.Hat;

public class Avatar implements Serializable{

    private static final long serialVersionUID = 1402907673497852680L;

    private Hat slotHat;

    private Accessory slotAccessory;

    private Eyes slotEyes;

    private boolean forceDisableAnimation;

    public static enum Slot {
        HAT,
        ACCESSORY,
        EYES
    }

    
    public Avatar(){
        slotHat = null;
        slotAccessory = null;
        slotEyes = null;
    }

    public Avatar(Hat hat, Accessory accessory, Eyes eyes){
        slotHat = hat;
        slotAccessory = accessory;
        slotEyes = eyes;
    }

    public void setHat(Hat hat){
        slotHat = hat;
    }

    public Hat getHat(){
       return slotHat;
    }

    public void setAccessory(Accessory accessory){
        slotAccessory = accessory;
    }

    public Accessory getAccessory(){
       return slotAccessory;
    }

    public void setEyes(Eyes eyes){
        slotEyes = eyes;
    }

    public Eyes getEyes(){
        return slotEyes;
     }

    public boolean isEquipped(Cosmetic c){
        if(c == null){
            return false;
        }
        return (
            (slotHat != null && slotHat.equals(c)) ||
             (slotAccessory != null && slotAccessory.equals(c)) ||
             (slotEyes != null && slotEyes.equals(c))
             );
    }

    public void setforceDisableAnimation(boolean b){
        forceDisableAnimation = b;
    }

    public boolean hasForceDisableAnimation(){
        return forceDisableAnimation;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("hat", slotHat == null ? null : slotHat.getFile());
            obj.put("accessory", slotAccessory == null ? null : slotAccessory.getFile());
            obj.put("eyes", slotEyes == null ? null : slotEyes.getFile());
            
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static Avatar fromJSONObject(String raw) {
        try {
            // if this raises an error, there is a good chance that you need to convert the argument to a string before passing
            JSONObject obj = new JSONObject(raw);

            String hat = null;
            String accessory = null;
            String eyes = null;
            try {
                hat = obj.getString("hat");
            }
            catch (JSONException e) {
            }
            try {
                accessory = obj.getString("accessory");
            }
            catch (JSONException e) {
            }
            try {
                eyes = obj.getString("eyes");
            }
            catch (JSONException e) {
            }

            Hat slotHat = hat != null && hat.length() > 0 ? Hat.valueOf(hat) : null;
            Accessory slotAccessory = accessory != null && accessory.length() > 0 ? Accessory.valueOf(accessory) : null;
            Eyes slotEyes = eyes != null && eyes.length() > 0 ? Eyes.valueOf(eyes) : null;
            
            return new Avatar(slotHat, slotAccessory, slotEyes);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
