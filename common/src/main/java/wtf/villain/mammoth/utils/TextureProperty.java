package wtf.villain.mammoth.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a property holding textures.
 */
public interface TextureProperty {
    /**
     * A skin with a question mark as the head texture.
     */
    TextureProperty QUESTION_MARK = texture(
        "ewogICJ0aW1lc3RhbXAiIDogMTU5NzE0NTQ0NTQ4OCwKICAicHJvZmlsZUlkIiA6ICI2MDZlMmZmMGVkNzc0ODQyOWQ2Y2UxZDMzMjFjNzgzOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNSEZfUXVlc3Rpb24iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM0ZTA2M2NhZmI0NjdhNWM4ZGU0M2VjNzg2MTkzOTlmMzY5ZjRhNTI0MzRkYTgwMTdhOTgzY2RkOTI1MTZhMCIKICAgIH0KICB9Cn0=",
        "pmifc8AcELLvyYVMoHQxRRQo0HGFMQuRJNlzNkQ/C2IB4hv7kJRr3G19Xj03lDzHtvko3gNFC0qBADO1SV6YGvLcwV0pWlRP1R9ZFxJjCYO6+psjSo8BE12liUPs84DcInFGdNuhKgidtUadAN1C1IFetJf9uZ9M5hBpHLs/DojUFjCQ3HrymFv3jrVSPXSa/V5m71EH3zsm7T5v2u+ep4m7NAtcBedQ/1cxy4JL/VdIxVwyXZK8XZyOGOT4bmZf3qMC7D3pVuy8xLpU4T0dA5tOFMUxHTqOKBvveIcwXjQSXmIcvjG0biL0JE00cjhshKCCjgyuBs1i3Lv2KZ" +
            "KOuGi4rF0qf9UyTnPTbLDqNT6n7I7ZuOp8BJXYdSUYZsuESg0zBxuRJtpzM6wGCgvYNzbhT3f7GRQoFwMAZt3eKxJqd8iP4gDmYYdKuBylyodYBDP8xuSt/8JDx4YWvDWJQr8eDS16O4Eqc/yPe2Wuncyow7UiLci8Rcbzb0nP3fUqDBSXt9PiPXycXR7tjVqQqud8ZunkB0eQcU82Pc/JgvJk+8WwnMRAkTaeNFWbGVLyb6efGP0185ZEldzF5G00Qj12lKWXQdTVI1dFxGxABoTVqeTGAcgnIz6ZAq10krG8tFO3LS8TMYyQx/P3Kjrj9o5FJQTDkRdlKl3jg97FY50="
    );

    /**
     * Returns the value of this {@link TextureProperty texture property}.
     *
     * @return the value
     */
    @NotNull
    String value();

    /**
     * Returns the signature of this {@link TextureProperty texture property}.
     *
     * @return the signature
     */
    @NotNull
    String signature();

    /**
     * Creates a new {@link TextureProperty texture property}.
     *
     * @param value     the value
     * @param signature the signature
     * @return a new {@link TextureProperty texture property}
     */
    @NotNull
    static TextureProperty texture(@NotNull String value, @NotNull String signature) {
        // @formatter:off
        return new TextureProperty() {
            @Override @NotNull public String value() { return value; }
            @Override @NotNull public String signature() { return signature; }
        };
        // @formatter:on
    }
}