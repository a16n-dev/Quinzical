package quinzical.avatar;

import quinzical.model.Avatar.Slot;

public enum Hat implements Cosmetic {
    BEANIE("beanie", "Beanie", 50),
    FROG("frog", "Frog Hat", 500),
    STRAW_HAT("straw_hat", "Straw Hat", 200),
    ROAD_CONE("road_cone", "Road Cone", 150),
    EGG("egg", "Egg", 130),
    ;

    private String fileName;

    private String displayName;

    private int price;

    private Hat(String path, String name, int cost) {
        fileName = path;
        price = cost;
        displayName = name;
    }

    @Override
    public String getFile() {
        return fileName;
    }

    @Override
    public String getIcon() {
        return fileName + "_icon.png";
    }

    @Override
    public String getId() {
        return "hat_" + ordinal();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return displayName;
    }

    @Override
    public Slot getSlot() {
        return Slot.HAT;
    }
}
