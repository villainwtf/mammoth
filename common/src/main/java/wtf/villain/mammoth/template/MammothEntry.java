package wtf.villain.mammoth.template;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import wtf.villain.mammoth.utils.TextureProperty;

import java.util.UUID;

/**
 * An object holding entry-related data.
 *
 * @see MammothTemplate
 */
@ApiStatus.Internal
public final class MammothEntry {
    private static final String NAME_FORMAT = "!mammoth-%02d";

    private final int index;
    private MammothEntryProvider entryProvider;

    private Component currentText;
    private Component newText;

    private TextureProperty currentSkin;
    private TextureProperty newSkin;

    private Integer currentPing;
    private Integer newPing;

    private boolean textChanged;
    private boolean pingChanged;
    private boolean skinChanged;
    private boolean dirty;

    /**
     * Creates a new {@link MammothEntry entry}.
     *
     * @param entryProvider the initial entry provider
     */
    MammothEntry(int index, @NotNull MammothEntryProvider entryProvider) {
        this.index = index;
        this.entryProvider = entryProvider;
    }

    /**
     * Updates all the values of this {@link MammothEntry entry}.
     */
    public void update() {
        // Checking for text changes
        {
            if (this.entryProvider != null) {
                this.setNewText(this.entryProvider.text());
            }

            this.textChanged = this.currentText == null || !this.currentText.equals(this.newText);
            this.currentText = this.newText;
        }

        // Checking for skin changes
        {
            if (this.entryProvider != null) {
                this.setNewSkin(this.entryProvider.skin());
            }

            this.skinChanged = this.currentSkin == null || !this.currentSkin.equals(this.newSkin);
            this.currentSkin = this.newSkin;
        }

        // Checking for ping changes
        {
            if (this.entryProvider != null) {
                this.setNewPing(this.entryProvider.ping());
            }

            this.pingChanged = this.currentPing == null || !this.currentPing.equals(this.newPing);
            this.currentPing = this.newPing;
        }
    }

    /**
     * Resets the {@link MammothEntry entry}'s mutation state.
     */
    public void resetState() {
        this.dirty = false;

        this.newText = null;
        this.newSkin = null;
        this.newPing = null;
    }

    /**
     * Returns if any change happened inside this {@link MammothEntry entry}.
     *
     * @return {@code true} if any of the values changed, {@code false} otherwise
     */
    public boolean anyChanged() {
        return this.textChanged || this.pingChanged || this.skinChanged;
    }

    /**
     * Sets the new {@link MammothEntryProvider entry provider} of this {@link MammothEntry entry}.
     *
     * @param entryProvider the new {@link MammothEntryProvider entry provider}
     */
    public void setEntryProvider(@Nullable MammothEntryProvider entryProvider) {
        this.entryProvider = entryProvider;
    }

    /**
     * Sets the new {@link Component text} of this {@link MammothEntry entry}.
     *
     * @param text the new {@link Component text}
     */
    public void setNewText(@NotNull Component text) {
        this.newText = text;
        this.dirty = true;
    }

    /**
     * Sets the new {@link TextureProperty skin} of this {@link MammothEntry entry}.
     *
     * @param skin the new {@link TextureProperty skin}
     */
    public void setNewSkin(@NotNull TextureProperty skin) {
        this.newSkin = skin;
        this.dirty = true;
    }

    /**
     * Sets the new ping of this {@link MammothEntry entry}.
     *
     * @param ping the new ping
     */
    void setNewPing(int ping) {
        this.newPing = ping;
        this.dirty = true;
    }

    /**
     * Returns if this {@link MammothEntry entry} is dirty.
     *
     * <p>An entry is considered dirty if its content is not manipulated by
     * an {@link MammothEntryProvider entry provider}.</p>
     *
     * @return {@code true} if the entry is dirty, {@code false} otherwise
     */
    public boolean dirty() {
        return this.dirty;
    }

    /**
     * Returns if the text of this {@link MammothEntry entry} has changed.
     *
     * @return {@code true} if the text changed, {@code false} otherwise
     */
    public boolean textChanged() {
        return this.textChanged;
    }

    /**
     * Returns if the ping of this {@link MammothEntry entry} has changed.
     *
     * @return {@code true} if the ping changed, {@code false} otherwise
     */
    public boolean pingChanged() {
        return this.pingChanged;
    }

    /**
     * Returns if the skin of this {@link MammothEntry entry} has changed.
     *
     * @return {@code true} if the skin changed, {@code false} otherwise
     */
    public boolean skinChanged() {
        return this.skinChanged;
    }

    /**
     * Returns the {@link MammothEntryProvider entry provider} of this {@link MammothEntry entry}.
     *
     * @return the {@link MammothEntryProvider entry provider}
     */
    @UnknownNullability
    public MammothEntryProvider entryProvider() {
        return this.entryProvider;
    }

    /**
     * Returns the {@link Component current text} of this {@link MammothEntry entry}.
     *
     * @return the {@link Component current text}
     */
    @UnknownNullability
    public Component currentText() {
        return this.currentText;
    }

    /**
     * Returns the {@link Component new text} of this {@link MammothEntry entry}.
     *
     * @return the {@link Component new text}
     */
    @UnknownNullability
    Component newText() {
        return this.newText;
    }

    /**
     * Returns the {@link TextureProperty current skin} of this {@link MammothEntry entry}.
     *
     * @return the {@link TextureProperty current skin}
     */
    @UnknownNullability
    public TextureProperty currentSkin() {
        return this.currentSkin;
    }

    /**
     * Returns the {@link TextureProperty new skin} of this {@link MammothEntry entry}.
     *
     * @return the {@link TextureProperty new skin}
     */
    @UnknownNullability
    TextureProperty newSkin() {
        return this.newSkin;
    }

    /**
     * Returns the current ping of this {@link MammothEntry entry}.
     *
     * @return the current ping
     */
    @UnknownNullability
    public Integer currentPing() {
        return this.currentPing;
    }

    /**
     * Returns the new ping of this {@link MammothEntry entry}.
     *
     * @return the new ping
     */
    @UnknownNullability
    Integer newPing() {
        return this.newPing;
    }

    /**
     * Returns the fake name of this {@link MammothEntry entry}.
     *
     * @return the fake name
     */
    @NotNull
    public String name() {
        return NAME_FORMAT.formatted(this.index);
    }

    /**
     * Returns the fake name of this {@link MammothEntry entry}.
     *
     * @return the fake name
     */
    @NotNull
    public UUID uuid() {
        return UUID.fromString(this.name());
    }
}