package quinzical.model;

import java.io.Serializable;

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
}
