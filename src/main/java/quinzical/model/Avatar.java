package quinzical.model;

import java.io.Serializable;

import quinzical.avatar.Accessory;
import quinzical.avatar.Cosmetic;
import quinzical.avatar.Hat;

public class Avatar implements Serializable{

    private static final long serialVersionUID = 1402907673497852680L;

    private Hat slotHat;

    private Accessory slotAccessory;

    public static enum Slot {
        HAT,
        ACCESSORY
    }

    
    public Avatar(){
        slotHat = null;
        slotAccessory = null;
    }

    public Avatar(Hat hat, Accessory accessory){
        slotHat = hat;
        slotAccessory = accessory;
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

    public boolean isEquipped(Cosmetic c){
        if(c == null){
            return false;
        }
        return ((slotHat != null && slotHat.equals(c)) || (slotAccessory != null && slotAccessory.equals(c)));
    }
}
