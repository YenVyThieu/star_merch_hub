package Group.com.starmerch.Artifact.star_merch_hub.model;

public enum ArtistName {

    BTS("BTS"),
    BLACKPINK("BLACKPINK"),
    STRAY_KIDS("Stray Kids"),
    TWICE("TWICE"),
    NEWJEANS("NewJeans");

    private final String displayName;

    ArtistName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}