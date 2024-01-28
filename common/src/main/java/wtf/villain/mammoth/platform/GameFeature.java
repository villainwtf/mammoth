package wtf.villain.mammoth.platform;

import org.jetbrains.annotations.ApiStatus;

/**
 * An enumeration of features supported by the game.
 *
 * @see PlatformProvider
 */
@ApiStatus.Internal
public enum GameFeature {
    /**
     * Supporting the usage of info packets only.
     *
     * @since Minecraft version 1.8
     */
    INFO_PACKETS_ONLY,
    /**
     * Supporting text components with RGB colors.
     *
     * @since Minecraft version 1.16
     */
    RGB_COLORS,
    /**
     * Supporting separated update / remove player info packets.
     *
     * @since Minecraft version 1.19.3
     */
    SEPARATE_INFO_PACKETS
}