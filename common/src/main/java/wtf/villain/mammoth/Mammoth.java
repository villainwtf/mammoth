package wtf.villain.mammoth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.villain.mammoth.platform.PlatformProvider;
import wtf.villain.mammoth.tablist.MammothTabList;
import wtf.villain.mammoth.tablist.impl.ModernMammothTabList;
import wtf.villain.mammoth.template.MammothTemplate;
import wtf.villain.mammoth.tablist.MammothTabListFactory;

import java.util.UUID;

/**
 * The wrapper for managing tab lists.
 *
 * <p>This class is a utility for creating {@link MammothTabList tab lists} for players.</p>
 *
 * <p>Every developer must track the provided tab lists for themselves.</p>
 *
 * @see MammothTemplate
 * @see MammothTabList
 */
public class Mammoth {
    /**
     * The default provider factory instance supplying {@link ModernMammothTabList}.
     */
    private static final MammothTabListFactory DEFAULT_FACTORY = ModernMammothTabList::new;

    private final PlatformProvider platformProvider;
    private final MammothTabListFactory factory;

    /**
     * Creates a new Mammoth instance.
     *
     * @param platformProvider the platform provider
     */
    public Mammoth(@NotNull PlatformProvider platformProvider) {
        this(platformProvider, DEFAULT_FACTORY);
    }

    /**
     * Creates a new Mammoth instance.
     *
     * @param platformProvider the platform provider
     * @param factory          the provider factory
     */
    public Mammoth(@NotNull PlatformProvider platformProvider, @NotNull MammothTabListFactory factory) {
        this.platformProvider = platformProvider;
        this.factory = factory;
    }

    /**
     * Attempts to provide a {@link MammothTabList tab list} for the given player.
     *
     * @param uuid     the player
     * @param template the template
     * @return a new {@link MammothTabList tab list}, or {@code null} if the factory
     * failed to create a tab list
     */
    @Nullable
    public final MammothTabList provideTabList(@NotNull UUID uuid, @NotNull MammothTemplate template) {
        return this.factory.provideTabList(uuid, this.platformProvider, template);
    }
}