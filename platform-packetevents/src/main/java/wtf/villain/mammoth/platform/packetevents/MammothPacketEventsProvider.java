package wtf.villain.mammoth.platform.packetevents;

import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoRemove;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerListHeaderAndFooter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.villain.mammoth.platform.*;
import wtf.villain.mammoth.template.MammothEntry;

import java.util.*;

import static net.kyori.adventure.text.Component.empty;

@ApiStatus.Experimental
public class MammothPacketEventsProvider implements PlatformProvider {
    private final PacketEventsAPI<?> api;
    private final Set<GameFeature> supportedFeatures;

    public MammothPacketEventsProvider(@NotNull PacketEventsAPI<?> api) {
        this.api = api;
        this.supportedFeatures = computeFeatures(api);
    }

    private Set<GameFeature> computeFeatures(PacketEventsAPI<?> api) {
        // Sadly we can't provide features per player since PacketEvents
        // can't send packets serialized using the player's version
        ServerVersion version = api.getServerManager().getVersion();
        EnumSet<GameFeature> set = EnumSet.noneOf(GameFeature.class);

        if (version.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            set.add(GameFeature.INFO_PACKETS_ONLY);
        }

        if (version.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            set.add(GameFeature.RGB_COLORS);
        }

        if (version.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            set.add(GameFeature.SEPARATE_INFO_PACKETS);
        }

        return Collections.unmodifiableSet(set);
    }

    @Override
    @NotNull
    public Set<GameFeature> supportedFeatures(@NotNull UUID uuid) {
        return this.supportedFeatures;
    }

    @Override
    @NotNull
    public Object bakeEntry(@NotNull MammothEntry entry, @NotNull Set<GameFeature> gameFeatures) {
        UserProfile profile = new UserProfile(entry.uuid(), entry.name());
        profile.setTextureProperties(List.of(new TextureProperty("textures", entry.currentSkin().value(), entry.currentSkin().signature())));

        if (gameFeatures.contains(GameFeature.SEPARATE_INFO_PACKETS)) {
            return new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                profile,
                true,
                entry.currentPing(),
                GameMode.SURVIVAL,
                entry.currentText(),
                null
            );
        } else {
            return new WrapperPlayServerPlayerInfo.PlayerData(
                entry.currentText(),
                profile,
                GameMode.SURVIVAL,
                entry.currentPing()
            );
        }
    }

    @Override
    public void send(@NotNull UUID uuid, @NotNull Set<GameFeature> gameFeatures, @NotNull UpdateAction action, @NotNull Collection<Object> entries) {
        this.api.getPlayerManager().sendPacket(uuid, this.createInfoPacket(gameFeatures, action, entries));
    }

    @Override
    public void sendHeaderAndFooter(@NotNull UUID uuid, @Nullable Component header, @Nullable Component footer) {
        this.api.getPlayerManager().sendPacket(uuid, new WrapperPlayServerPlayerListHeaderAndFooter(
            header != null ? header : empty(),
            footer != null ? footer : empty()
        ));
    }

    @NotNull
    private PacketWrapper<?> createInfoPacket(@NotNull Set<GameFeature> gameFeatures, @NotNull UpdateAction action, @NotNull Collection<Object> bakedEntries) {
        if (gameFeatures.contains(GameFeature.SEPARATE_INFO_PACKETS)) {
            List<WrapperPlayServerPlayerInfoUpdate.PlayerInfo> updateInfo = this.cast(bakedEntries);

            if (action == UpdateAction.REMOVE) {
                return new WrapperPlayServerPlayerInfoRemove(updateInfo.stream().map(info -> info.getGameProfile().getUUID()).toList());
            } else {
                return new WrapperPlayServerPlayerInfoUpdate(updateAction(action), updateInfo);
            }
        } else {
            List<WrapperPlayServerPlayerInfo.PlayerData> updateInfo = this.cast(bakedEntries);
            return new WrapperPlayServerPlayerInfo(action(action), updateInfo);
        }
    }

    @NotNull
    private WrapperPlayServerPlayerInfo.Action action(@NotNull UpdateAction action) {
        return switch (action) {
            case ADD -> WrapperPlayServerPlayerInfo.Action.ADD_PLAYER;
            case REMOVE -> WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER;
            case UPDATE_NAME -> WrapperPlayServerPlayerInfo.Action.UPDATE_DISPLAY_NAME;
            case UPDATE_LATENCY -> WrapperPlayServerPlayerInfo.Action.UPDATE_LATENCY;
        };
    }

    @NotNull
    private WrapperPlayServerPlayerInfoUpdate.Action updateAction(@NotNull UpdateAction action) {
        return switch (action) {
            case ADD -> WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER;
            case UPDATE_NAME -> WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_DISPLAY_NAME;
            case UPDATE_LATENCY -> WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LATENCY;
            default -> throw new IllegalArgumentException("unreachable");
        };
    }

    @SuppressWarnings("unchecked")
    private <T> T cast(@NotNull Object object) {
        return (T) object;
    }
}