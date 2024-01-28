package wtf.villain.mammoth.platform;

import org.jetbrains.annotations.ApiStatus;

/**
 * An enumeration representing possible actions with entries.
 */
@ApiStatus.Internal
public enum UpdateAction {
    /**
     * Adds an entry to the player's tab list.
     */
    ADD,
    /**
     * Removes an entry from the player's tab list.
     */
    REMOVE,
    /**
     * Updates the latency of an entry.
     */
    UPDATE_LATENCY,
    /**
     * Updates the name of an entry.
     */
    UPDATE_NAME
}