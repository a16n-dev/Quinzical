package quinzical.avatar;

import quinzical.model.Avatar.Slot;

/**
 * An enum representing all of the purchasable items for a users avatar. The
 * cosmetic items are divided into accessories, hats and eyes.
 * 
 * @author Alexander Nicholson
 */
public enum Cosmetic {
    // Accessories
    MUSTACHE(Slot.ACCESSORY, "mustache", "Mustache", 20),

    // Hats
    BEANIE(Slot.HAT, "beanie", "Beanie", 50), 
    FROG(Slot.HAT, "frog", "Frog Hat", 500),
    STRAW_HAT(Slot.HAT, "straw_hat", "Straw Hat", 200), 
    ROAD_CONE(Slot.HAT, "road_cone", "Road Cone", 150),
    EGG(Slot.HAT, "egg", "Egg", 130),

    // Eyes
    SUNGLASSES(Slot.EYES, "sunglasses", "Sunglasses", 100), 
    GLASSES(Slot.EYES, "glasses", "Glasses", 50),
    EYEBROWS(Slot.EYES, "eyebrows", "Eyebrows", 80), 
    MONOCLE(Slot.EYES, "monocle", "Monocle", 30),;

    private String fileName;

    private String displayName;

    private int price;

    private Slot slot;

    private Cosmetic(Slot slot, String fileName, String displayName, int price) {
        this.fileName = fileName;
        this.price = price;
        this.displayName = displayName;
        this.slot = slot;
    }

    public String getFile() {
        return fileName;
    }

    /**
     * @return The icon for this cosmetic for displaying in the shop
     */
    public String getIcon() {
        return fileName + "_icon.png";
    }

    /**
     * @return the unique id for the cosmetic. This is what the user stores to track
     *         what items they own
     */
    public String getId() {
        return slot + "_" + ordinal();
    }

    /**
     * @return the price of the item, to use when the player purchases the item
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return the display name of the cosmetic
     */
    public String getName() {
        return displayName;
    }

    /**
     * @return the slot the cosmetic is for
     */
    public Slot getSlot() {
        return slot;
    }
}
