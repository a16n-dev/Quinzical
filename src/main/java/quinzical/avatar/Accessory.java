package quinzical.avatar;

import quinzical.model.Avatar.Slot;

public enum Accessory implements Cosmetic {
    MUSTACHE("mustache", "Mustache", 20),;

    private String fileName;

    private String displayName;

    private int price;

    private Accessory(String path, String name, int cost) {
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
        return "accessory_" + ordinal();
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
        return Slot.ACCESSORY;
    }
}
