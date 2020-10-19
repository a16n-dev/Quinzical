package quinzical.avatar;

import quinzical.model.Avatar;

/**
 * An interface to be implemented by all cosmetic items such as hats and
 * accessories that can be applied to a users avatar
 */
public interface Cosmetic {
    public String getFile();

    /**
     * @return The icon for this cosmetic for displaying in the shop
     */
    public String getIcon();

    /**
     * @return the unique id for the cosmetic. This is what the user stores to track what items they own
     */
    public String getId();

    /**
     * @return the price of the item, to use when the player purchases the item
     */
    public int getPrice();

    /**
     * @return the display name of the cosmetic
     */
    public String getName();

    /**
     * @return the slot the cosmetic is for
     */
    public Avatar.Slot getSlot();
}
