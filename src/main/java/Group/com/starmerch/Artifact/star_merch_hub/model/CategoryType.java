package Group.com.starmerch.Artifact.star_merch_hub.model;

public enum CategoryType {

    LIGHTSTICK("Lightstick"),
    ALBUM("Album"),
    PHOTOCARD("Photocard"),
    APPAREL("Apparel"),
    ACCESSORY("Accessory");

    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}