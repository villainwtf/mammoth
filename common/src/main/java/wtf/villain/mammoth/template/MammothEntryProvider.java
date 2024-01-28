package wtf.villain.mammoth.template;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import wtf.villain.mammoth.utils.TextureProperty;

import static net.kyori.adventure.text.Component.empty;

/**
 * Represents a provider for an entry in a {@link MammothTemplate tab list template}.
 *
 * <p>An entry consists of 3 things:</p>
 *
 * <ul>
 *  <li> {@link Component displayed text}
 *  <li> {@link TextureProperty skin}
 *  <li> ping / latency
 * </ul>
 *
 * @see Component
 * @see MammothEntry
 * @see MammothTemplate
 * @see TextureProperty
 */
public interface MammothEntryProvider {
    /**
     * Returns the displayed text of this entry.
     *
     * @return the text
     */
    @NotNull
    Component text();

    /**
     * Returns the property (wrapped as {@link TextureProperty}) of this entry.
     *
     * @return the property
     */
    @NotNull
    default TextureProperty skin() {
        return TextureProperty.QUESTION_MARK;
    }

    /**
     * Returns the displayed ping of this entry.
     *
     * @return the ping
     */
    default int ping() {
        return -1;
    }

    /**
     * Creates a {@link MammothEntryProvider tab list entry provider} using the given text.
     *
     * @param text the text
     * @return a new {@link MammothEntryProvider tab list entry provider}
     */
    @NotNull
    static MammothEntryProvider entryProvider(@NotNull Component text) {
        return entryProvider(text, TextureProperty.QUESTION_MARK);
    }

    /**
     * Creates a {@link MammothEntryProvider tab list entry provider} using the given property.
     *
     * @param property the property
     * @return a new {@link MammothEntryProvider tab list entry provider}
     */
    @NotNull
    static MammothEntryProvider entryProvider(@NotNull TextureProperty property) {
        return entryProvider(empty(), property);
    }

    /**
     * Creates a {@link MammothEntryProvider tab list entry provider} with the given ping.
     *
     * @param ping the ping
     * @return a new {@link MammothEntryProvider tab list entry provider}
     */
    @NotNull
    static MammothEntryProvider entryProvider(int ping) {
        return entryProvider(empty(), ping);
    }

    /**
     * Creates a {@link MammothEntryProvider tab list entry provider} with the given text and ping.
     *
     * @param text the text
     * @param ping the ping
     * @return a new {@link MammothEntryProvider tab list entry provider}
     */
    @NotNull
    static MammothEntryProvider entryProvider(@NotNull Component text, int ping) {
        return entryProvider(text, TextureProperty.QUESTION_MARK, ping);
    }

    /**
     * Creates a {@link MammothEntryProvider tab list entry provider} with the given text and property.
     *
     * @param text     the text
     * @param property the property
     * @return a new {@link MammothEntryProvider tab list entry provider}
     */
    @NotNull
    static MammothEntryProvider entryProvider(@NotNull Component text, @NotNull TextureProperty property) {
        return entryProvider(text, property, -1);
    }

    /**
     * Creates a {@link MammothEntryProvider tab list entry provider} with the given property and ping.
     *
     * @param property the property
     * @param ping     the ping
     * @return a new {@link MammothEntryProvider tab list entry provider}
     */
    @NotNull
    static MammothEntryProvider entryProvider(@NotNull TextureProperty property, int ping) {
        return entryProvider(empty(), property, ping);
    }

    /**
     * Creates a {@link MammothEntryProvider tab list entry provider} with the given text, property and ping.
     *
     * @param text     the text
     * @param property the property
     * @param ping     the ping
     * @return a new {@link MammothEntryProvider tab list entry provider}
     */
    @NotNull
    static MammothEntryProvider entryProvider(@NotNull Component text, @NotNull TextureProperty property, int ping) {
        // @formatter:off
        return new MammothEntryProvider() {
            @Override public @NotNull Component text() { return text; }
            @Override public @NotNull TextureProperty skin() { return property; }
            @Override public int ping() { return ping; }
        };
        // @formatter:on
    }
}