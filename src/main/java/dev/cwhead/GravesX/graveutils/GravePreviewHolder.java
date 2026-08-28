package dev.cwhead.GravesX.graveutils;

import com.ranull.graves.type.Grave;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Inventory holder for read-only grave previews.
 * <p>
 * Players who fail the protection check are shown a snapshot copy of the grave
 * inventory held by this class instead of the live grave inventory. The click
 * and drag listeners cancel every interaction with an inventory held by a
 * {@code GravePreviewHolder}, so a previewing player can never mutate the real
 * grave contents (including via cross-inventory actions such as
 * double-click collect-to-cursor, which bypass clicked-inventory checks).
 * </p>
 */
public class GravePreviewHolder implements InventoryHolder {
    private final Grave grave;
    private Inventory inventory;

    /**
     * Constructs a preview holder for the given grave.
     *
     * @param grave the grave being previewed
     */
    public GravePreviewHolder(Grave grave) {
        this.grave = grave;
    }

    /**
     * Gets the grave this preview belongs to.
     *
     * @return the grave
     */
    public Grave getGrave() {
        return grave;
    }

    /**
     * Gets the snapshot inventory shown to the previewing player.
     *
     * @return the preview inventory
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the snapshot inventory shown to the previewing player.
     *
     * @param inventory the preview inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
