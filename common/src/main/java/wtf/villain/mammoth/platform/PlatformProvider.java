package wtf.villain.mammoth.platform;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.villain.mammoth.template.MammothEntry;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * A simple platform provider interface.
 *
 * <p>Platform providers are in charge for:</p>
 * <ul>
 *   <li>Determining the {@link GameFeature features supported} by the player's client</li>
 *   <li>Sending data towards the player's client</li>
 * </ul>
 *
 * @see GameFeature
 * @see UpdateAction
 */
@ApiStatus.Experimental
public interface PlatformProvider {
    /**
     * Returns the {@link GameFeature supported features} for a player.
     *
     * @param uuid the player
     * @return the {@link GameFeature features} supported by the player's protocol version
     */
    @NotNull
    Set<GameFeature> supportedFeatures(@NotNull UUID uuid);

    /**
     * Bakes the given {@link MammothEntry tab list entry} into a format what can be sent to players.
     *
     * @param entry        the entry
     * @param gameFeatures the supported features
     * @return the baked entries
     */
    @NotNull
    Object bakeEntry(@NotNull MammothEntry entry, @NotNull Set<GameFeature> gameFeatures);

    /**
     * Sends the changes for the player.
     *
     * @param uuid         the player
     * @param gameFeatures the supported features
     * @param action       the update action
     * @param bakedEntries the baked entries
     */
    void send(@NotNull UUID uuid, @NotNull Set<GameFeature> gameFeatures, @NotNull UpdateAction action, @NotNull Collection<Object> bakedEntries);

    /**
     * Sends a header and footer change for the player.
     *
     * @param uuid   the player
     * @param header the header
     * @param footer the footer
     */
    void sendHeaderAndFooter(@NotNull UUID uuid, @Nullable Component header, @Nullable Component footer);
}