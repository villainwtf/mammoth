package wtf.villain.mammoth.tablist.impl;


import org.jetbrains.annotations.NotNull;
import wtf.villain.mammoth.platform.PlatformProvider;
import wtf.villain.mammoth.platform.UpdateAction;
import wtf.villain.mammoth.tablist.MammothTabList;
import wtf.villain.mammoth.template.MammothEntry;
import wtf.villain.mammoth.template.MammothHeaderAndFooter;
import wtf.villain.mammoth.template.MammothTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A tab list provider for versions 1.8 - latest.
 *
 * @see MammothTabList
 */
public final class ModernMammothTabList extends MammothTabList {
    private final List<Object> skinChanged;
    private final List<Object> textChanged;
    private final List<Object> pingChanged;

    /**
     * Creates a provider instance.
     *
     * @param uuid             the player
     * @param platformProvider the platform provider
     * @param template         the template
     */
    public ModernMammothTabList(@NotNull UUID uuid, @NotNull PlatformProvider platformProvider, @NotNull MammothTemplate template) {
        super(uuid, platformProvider, template);
        this.textChanged = new ArrayList<>();
        this.skinChanged = new ArrayList<>();
        this.pingChanged = new ArrayList<>();
    }

    @Override
    public void update() {
        updateTabList();
        updateHeaderFooter();
    }

    @Override
    protected void destroy() {
        List<Object> baked = new ArrayList<>();

        for (MammothEntry entry : this.template.entries()) {
            baked.add(this.platformProvider.bakeEntry(entry, this.gameFeatures));
        }

        clearBakedData();
        this.platformProvider.send(this.uuid, this.gameFeatures, UpdateAction.REMOVE, baked);
    }

    private void updateTabList() {
        this.template.update();

        for (int i = 0; i < MammothTemplate.TEMPLATE_MAX_SIZE; i++) {
            MammothEntry entry = this.template.entries()[i];
            entry.update();

            // If the entry has nothing in it, fall back to the default entry provider
            if (!entry.dirty() && entry.entryProvider() == null) {
                entry.setEntryProvider(this.template.defaultProvider());
                entry.update();
            }

            // We check if anything changed
            if (entry.anyChanged()) {
                Object baked = this.platformProvider.bakeEntry(entry, this.gameFeatures);

                if (entry.skinChanged()) {
                    this.skinChanged.add(baked);
                } else {
                    if (entry.textChanged()) {
                        this.textChanged.add(baked);
                    }

                    if (entry.pingChanged()) {
                        this.pingChanged.add(baked);
                    }
                }
            }

            if (this.template.updateEntryStates()) {
                entry.resetState();
            }
        }

        this.platformProvider.send(this.uuid, this.gameFeatures, UpdateAction.ADD, this.skinChanged);
        this.platformProvider.send(this.uuid, this.gameFeatures, UpdateAction.UPDATE_NAME, this.textChanged);
        this.platformProvider.send(this.uuid, this.gameFeatures, UpdateAction.UPDATE_LATENCY, this.pingChanged);
        clearBakedData();
    }

    private void updateHeaderFooter() {
        MammothHeaderAndFooter entry = this.template.headerAndFooter();
        entry.update();

        // We check if anything changed
        if (entry.anyChanged()) {
            this.platformProvider.sendHeaderAndFooter(this.uuid, entry.newHeader(), entry.newFooter());
        }

        if (this.template.updateEntryStates()) {
            entry.resetState();
        }
    }

    private void clearBakedData() {
        this.skinChanged.clear();
        this.textChanged.clear();
        this.pingChanged.clear();
    }
}