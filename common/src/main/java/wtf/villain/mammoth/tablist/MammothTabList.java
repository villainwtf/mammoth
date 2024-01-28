package wtf.villain.mammoth.tablist;

import org.jetbrains.annotations.NotNull;
import wtf.villain.mammoth.platform.GameFeature;
import wtf.villain.mammoth.platform.PlatformProvider;
import wtf.villain.mammoth.template.MammothTemplate;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a tab list.
 *
 * <p>Implementations of this class handle the update logic for different versions of the game.</p>
 *
 * @see GameFeature
 * @see MammothTemplate
 */
public abstract class MammothTabList {
    /**
     * The player's uuid.
     */
    protected final UUID uuid;
    /**
     * The {@link PlatformProvider platform provider}.
     */
    protected final PlatformProvider platformProvider;
    /**
     * The {@link MammothTemplate tab list template}.
     */
    protected final MammothTemplate template;
    /**
     * The player's {@link GameFeature supported features}.
     */
    protected final Set<GameFeature> gameFeatures;

    /**
     * Creates a new tab list provider.
     *
     * @param uuid             the player
     * @param platformProvider the platform provider
     * @param template         the template
     */
    protected MammothTabList(@NotNull UUID uuid, @NotNull PlatformProvider platformProvider, @NotNull MammothTemplate template) {
        this.uuid = uuid;
        this.platformProvider = platformProvider;
        this.template = template;
        this.gameFeatures = platformProvider.supportedFeatures(uuid);
    }

    /**
     * Updates the tab list. It gets called upon every tick.
     */
    public abstract void update();

    /**
     * Destroys the tab list. It restores the previous state of the tab list seen by the player.
     */
    protected abstract void destroy();

    /**
     * Returns the {@link MammothTemplate tab list template} used by this {@link MammothTabList provider}.
     *
     * @param <T> the type of this template
     * @return the {@link MammothTemplate tab list template}
     */
    @NotNull
    @SuppressWarnings("unchecked")
    public final <T extends MammothTemplate> T template() {
        return (T) this.template;
    }
}