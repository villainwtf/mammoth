package wtf.villain.mammoth.template;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.villain.mammoth.tablist.MammothTabList;
import wtf.villain.mammoth.utils.TextureProperty;

import static net.kyori.adventure.text.Component.empty;

/**
 * A template what holds all the entries for a {@link MammothTabList tab list}.
 *
 * <p>There are multiple ways to provide entries for a template:</p>
 *
 * <ul>
 *  <li> you can use {@link MammothEntryProvider entry providers} to provide lines
 *  with methods backed by {@link #provider(int, MammothEntryProvider)}, or
 *  <li> you can set the values of the entries using any method backed by
 *  {@link #set(int, Component, TextureProperty, int)}
 * </ul>
 *
 * <p>You can also provide entries for the template as if they were stateful by not
 * updating the template every tick (see {@link #updateEntryStates()}).</p>
 *
 * @see MammothEntry
 * @see MammothEntryProvider
 */
public abstract class MammothTemplate {
    /**
     * The default {@link MammothEntryProvider entry provider}.
     */
    public static final MammothEntryProvider DEFAULT_PROVIDER = MammothEntryProvider.entryProvider(empty(), TextureProperty.QUESTION_MARK, -1);

    /**
     * The maximum amount of rows what a vanilla client can display.
     */
    public static final int TEMPLATE_MAX_ROWS = 20;

    /**
     * The maximum amount of columns what a vanilla client can display.
     *
     * <p>Before 1.7, this value was 3, but since templates doesn't know about the
     * players version (by default), we allocate 4 columns anyway.</p>
     */
    public static final int TEMPLATE_MAX_COLUMNS = 4;

    /**
     * The combined size of the {@link MammothTemplate#entries array}.
     */
    public static final int TEMPLATE_MAX_SIZE = TEMPLATE_MAX_COLUMNS * TEMPLATE_MAX_ROWS;

    private final MammothEntry[] entries;
    private final MammothHeaderAndFooter headerAndFooter;

    /**
     * Creates a new template.
     */
    public MammothTemplate() {
        this.entries = new MammothEntry[TEMPLATE_MAX_SIZE];
        this.headerAndFooter = new MammothHeaderAndFooter();

        for (int i = 0; i < TEMPLATE_MAX_SIZE; i++) {
            this.entries[i] = new MammothEntry(i, defaultProvider());
        }
    }

    /**
     * Gets called upon every update.
     *
     * <p>This is where all mutations to the entry set must be made if used
     * with {@link MammothTemplate#updateEntryStates() updateEntryStates()}.</p>
     */
    public void update() {
        // empty
    }

    /**
     * Returns if the entries should clear their update data after ticking, making
     * the tab list dynamical.
     *
     * <p>If this method returns {@code false}, then the update data of every
     * entry is kept, making the tab list keep the last modification on every update.</p>
     *
     * @return {@code true} if the provider should clear the update data, {@code false} otherwise
     */
    public boolean updateEntryStates() {
        return true;
    }

    /**
     * Returns the {@link MammothEntryProvider entry provider} for entries without content.
     *
     * @return the default provider
     */
    @NotNull
    public MammothEntryProvider defaultProvider() {
        return DEFAULT_PROVIDER;
    }

    /**
     * Sets the header to {@code null}.
     */
    public final void resetHeader() {
        this.headerAndFooter.setNewHeader(null);
    }

    /**
     * Sets the header to {@code null}.
     */
    public final void resetFooter() {
        this.headerAndFooter.setNewFooter(null);
    }

    /**
     * Sets the value of the header.
     *
     * @param header the header
     */
    public final void header(@Nullable Component header) {
        this.headerAndFooter.setNewHeader(header);
    }

    /**
     * Sets the value of the footer.
     *
     * @param footer the footer
     */
    public final void footer(@Nullable Component footer) {
        this.headerAndFooter.setNewFooter(footer);
    }

    /**
     * Returns the header.
     *
     * @return the header
     */
    @Nullable
    public final Component currentHeader() {
        Component header = this.headerAndFooter.currentHeader();

        if (header == null) {
            header = this.headerAndFooter.newHeader();
        }

        return header;
    }

    /**
     * Returns the footer.
     *
     * @return the footer
     */
    @Nullable
    public final Component currentFooter() {
        Component footer = this.headerAndFooter.currentFooter();

        if (footer == null) {
            footer = this.headerAndFooter.newFooter();
        }

        return footer;
    }

    /**
     * Sets the text, skin and ping property of a {@link MammothEntry tab list entry} to its default value.
     *
     * @param x the x position of the entry
     * @param y the y position of the entry
     */
    public final void reset(int x, int y) {
        set(pos(x, y), defaultProvider());
    }

    /**
     * Sets the text, skin and ping property of a {@link MammothEntry tab list entry} to its default value.
     *
     * @param pos the position
     */
    public final void reset(int pos) {
        set(pos, defaultProvider());
    }

    /**
     * Sets the text, skin and ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param x        the x position of the entry
     * @param y        the y position of the entry
     * @param provider the provider
     */
    public final void set(int x, int y, @NotNull MammothEntryProvider provider) {
        set(pos(x, y), provider.text(), provider.skin(), provider.ping());
    }

    /**
     * Sets the text, skin and ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos      the position of the entry
     * @param provider the provider
     */
    public final void set(int pos, @NotNull MammothEntryProvider provider) {
        set(pos, provider.text(), provider.skin(), provider.ping());
    }

    /**
     * Sets the text property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param x    the x position of the entry
     * @param y    the y position of the entry
     * @param text the text
     */
    public final void set(int x, int y, @NotNull Component text) {
        set(pos(x, y), text, DEFAULT_PROVIDER.skin(), DEFAULT_PROVIDER.ping());
    }

    /**
     * Sets the text property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos  the position of the entry
     * @param text the text
     */
    public final void set(int pos, @NotNull Component text) {
        set(pos, text, DEFAULT_PROVIDER.skin(), DEFAULT_PROVIDER.ping());
    }

    /**
     * Sets the skin property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param x    the x position of the entry
     * @param y    the y position of the entry
     * @param skin the skin
     */
    public final void set(int x, int y, @NotNull TextureProperty skin) {
        set(pos(x, y), DEFAULT_PROVIDER.text(), skin, DEFAULT_PROVIDER.ping());
    }

    /**
     * Sets the skin property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos  the position of the entry
     * @param skin the skin
     */
    public final void set(int pos, @NotNull TextureProperty skin) {
        set(pos, DEFAULT_PROVIDER.text(), skin, DEFAULT_PROVIDER.ping());
    }

    /**
     * Sets the ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param x    the x position of the entry
     * @param y    the y position of the entry
     * @param ping the ping
     */
    public final void set(int x, int y, int ping) {
        set(pos(x, y), DEFAULT_PROVIDER.text(), DEFAULT_PROVIDER.skin(), ping);
    }

    /**
     * Sets the ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos  the position of the entry
     * @param ping the ping
     */
    public final void set(int pos, int ping) {
        set(pos, DEFAULT_PROVIDER.text(), DEFAULT_PROVIDER.skin(), ping);
    }

    /**
     * Sets the text and ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param x    the x position of the entry
     * @param y    the y position of the entry
     * @param text the text
     * @param ping the ping
     */
    public final void set(int x, int y, @NotNull Component text, int ping) {
        set(pos(x, y), text, DEFAULT_PROVIDER.skin(), ping);
    }

    /**
     * Sets the text and ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos  the position of the entry
     * @param text the text
     * @param ping the ping
     */
    public final void set(int pos, @NotNull Component text, int ping) {
        set(pos, text, DEFAULT_PROVIDER.skin(), ping);
    }

    /**
     * Sets the text and skin property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param x    the x position of the entry
     * @param y    the y position of the entry
     * @param text the text
     * @param skin the skin
     */
    public final void set(int x, int y, @NotNull Component text, @NotNull TextureProperty skin) {
        set(pos(x, y), text, skin, DEFAULT_PROVIDER.ping());
    }

    /**
     * Sets the text and skin property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos  the position of the entry
     * @param text the text
     * @param skin the skin
     */
    public final void set(int pos, @NotNull Component text, @NotNull TextureProperty skin) {
        set(pos, text, skin, DEFAULT_PROVIDER.ping());
    }

    /**
     * Sets the skin and ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param x    the x position of the entry
     * @param y    the y position of the entry
     * @param skin the skin
     * @param ping the ping
     */
    public final void set(int x, int y, @NotNull TextureProperty skin, int ping) {
        set(pos(x, y), DEFAULT_PROVIDER.text(), skin, ping);
    }

    /**
     * Sets the skin and ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos  the position of the entry
     * @param skin the skin
     * @param ping the ping
     */
    public final void set(int pos, @NotNull TextureProperty skin, int ping) {
        set(pos, DEFAULT_PROVIDER.text(), skin, ping);
    }

    /**
     * Sets the text, skin and ping property of a {@link MammothEntry tab list entry} at the given position.
     *
     * @param x    the x position of the entry
     * @param y    the y position of the entry
     * @param text the text
     * @param skin the skin
     * @param ping the ping
     */
    public final void set(int x, int y, @NotNull Component text, @NotNull TextureProperty skin, int ping) {
        set(pos(x, y), text, skin, ping);
    }

    /**
     * Sets the text, skin and ping property of the {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos  the position of the entry
     * @param text the text
     * @param skin the skin
     * @param ping the ping
     */
    public final void set(int pos, @NotNull Component text, @NotNull TextureProperty skin, int ping) {
        MammothEntry container = this.entries[pos];
        container.setEntryProvider(null);
        container.setNewText(text);
        container.setNewSkin(skin);
        container.setNewPing(ping);
    }

    /**
     * Sets the {@link MammothEntryProvider entry provider} of the {@link MammothEntry tab list entry} at the given position.
     *
     * @param x        the x position of the entry
     * @param y        the y position of the entry
     * @param provider the provider
     * @implNote If the entry gets mutated by any methods backed by {@link #set(int, Component, TextureProperty, int)},
     * the provider gets removed.
     */
    public final void provider(int x, int y, @NotNull MammothEntryProvider provider) {
        provider(pos(x, y), provider);
    }

    /**
     * Sets the {@link MammothEntryProvider entry provider} of the {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos      the position of the entry
     * @param provider the provider
     * @implNote If the entry gets mutated by any methods backed by {@link #set(int, Component, TextureProperty, int)},
     * the provider gets removed.
     */
    public final void provider(int pos, @NotNull MammothEntryProvider provider) {
        this.entries[pos].setEntryProvider(provider);
    }

    /**
     * Returns the text of the {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos the position of the entry
     * @return the text
     */
    @NotNull
    public final Component currentText(int pos) {
        return currentText(this.entries[pos]);
    }

    @NotNull
    private Component currentText(@NotNull MammothEntry container) {
        Component current = container.currentText();

        if (current == null) {
            current = container.newText();
        }

        return current;
    }

    /**
     * Returns the skin of the {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos the position of the entry
     * @return the skin
     */
    @NotNull
    public final TextureProperty currentSkin(int pos) {
        return currentSkin(this.entries[pos]);
    }

    @NotNull
    private TextureProperty currentSkin(@NotNull MammothEntry container) {
        TextureProperty current = container.currentSkin();

        if (current == null) {
            current = container.newSkin();
        }

        return current;
    }

    /**
     * Returns the ping of the {@link MammothEntry tab list entry} at the given position.
     *
     * @param pos the position of the entry
     * @return the ping
     */
    public final int currentPing(int pos) {
        return currentPing(this.entries[pos]);
    }

    private int currentPing(@NotNull MammothEntry container) {
        Integer current = container.currentPing();

        if (current == null) {
            current = container.newPing();
        }

        return current;
    }

    /**
     * Converts a 2-dimensional position into a {@link MammothEntry tab list entry} position.
     *
     * @param x the x position
     * @param y the y position
     * @return the converted entry position
     */
    protected final int pos(int x, int y) {
        return x * TEMPLATE_MAX_ROWS + y;
    }

    /**
     * Returns the {@link MammothEntry entries} of this template.
     *
     * @return the entries
     */
    @ApiStatus.Internal
    public final MammothEntry[] entries() {
        return this.entries;
    }

    /**
     * Returns the {@link MammothHeaderAndFooter header and footer} of this template.
     *
     * @return the header and footer
     */
    @ApiStatus.Internal
    public final MammothHeaderAndFooter headerAndFooter() {
        return this.headerAndFooter;
    }
}