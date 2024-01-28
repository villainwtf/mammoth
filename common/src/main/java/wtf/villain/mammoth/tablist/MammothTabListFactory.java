package wtf.villain.mammoth.tablist;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.villain.mammoth.platform.PlatformProvider;
import wtf.villain.mammoth.template.MammothTemplate;

import java.util.UUID;

/**
 * A simple interface for creating {@link MammothTabList tab list} instances.
 */
@FunctionalInterface
public interface MammothTabListFactory {
    /**
     * Attempts to provide a {@link MammothTabList tab list} for the given player.
     *
     * @param uuid             the player
     * @param platformProvider the platform provider
     * @param template         the template
     * @return a new {@link MammothTabList tab list}, or {@code null} if the factory
     * failed to create a tab list
     */
    @Nullable
    MammothTabList provideTabList(
        @NotNull UUID uuid,
        @NotNull PlatformProvider platformProvider,
        @NotNull MammothTemplate template
    );
}
