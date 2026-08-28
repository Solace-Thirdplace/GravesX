package com.ranull.graves.listener;

import com.ranull.graves.Graves;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;

/**
 * Listens for BlockFromToEvent to prevent water or lava from flowing over grave blocks.
 */
public class BlockFromToListener implements Listener {
    private final Graves plugin;

    /**
     * Constructs a new BlockFromToListener with the specified Graves plugin.
     *
     * @param plugin The Graves plugin instance.
     */
    public BlockFromToListener(Graves plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles BlockFromToEvent to prevent fluid from flowing into grave blocks.
     *
     * @param event The BlockFromToEvent to handle.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent event) {
        String toType = event.getToBlock().getType().name();

        if (isGraveBlock(event) || toType.contains("SKULL")
                || toType.equals("PLAYER_HEAD") || toType.equals("PLAYER_WALL_HEAD")) {
            event.setCancelled(true);
        }
    }

    /**
     * Handles BlockFormEvent to prevent forming liquids (e.g. a water source
     * re-forming between two neighbouring sources) from replacing grave blocks.
     * Source formation does not fire BlockFromToEvent, so without this a grave
     * head placed in shallow water is silently destroyed a tick after placement.
     *
     * @param event The BlockFormEvent to handle.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockForm(BlockFormEvent event) {
        if (plugin.getCacheManager().getGrave(event.getBlock()) != null) {
            event.setCancelled(true);
        }
    }

    /**
     * Handles BlockFadeEvent to prevent environmental block transitions from
     * removing grave blocks.
     *
     * @param event The BlockFadeEvent to handle.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockFade(BlockFadeEvent event) {
        if (plugin.getCacheManager().getGrave(event.getBlock()) != null) {
            event.setCancelled(true);
        }
    }

    /**
     * Checks if the destination block of the fluid is a grave block.
     *
     * @param event The BlockFromToEvent to check.
     * @return True if the destination block is a grave block, false otherwise.
     */
    private boolean isGraveBlock(BlockFromToEvent event) {
        return plugin.getCacheManager().getGrave(event.getToBlock()) != null;
    }
}
