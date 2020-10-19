package quinzical.avatar;

import quinzical.model.Avatar.Slot;

public enum Eyes implements Cosmetic {
    SUNGLASSES("sunglasses", "Sunglasses", 100),
    GLASSES("glasses", "Glasses", 50),
    ;

    private String fileName;

    private String displayName;

    private int price;

    private Eyes(String path, String name, int cost) {
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
        return "eyes_" + ordinal();
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
        return Slot.EYES;
    }
}
