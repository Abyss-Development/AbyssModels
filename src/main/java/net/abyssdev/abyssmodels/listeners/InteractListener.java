package net.abyssdev.abyssmodels.listeners;

import net.abyssdev.abysslib.listener.AbyssListener;
import net.abyssdev.abysslib.nbt.NBTUtils;
import net.abyssdev.abysslib.nms.NMSUtils;
import net.abyssdev.abyssmodels.AbyssModels;
import net.abyssdev.abyssmodels.type.ModelType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class InteractListener extends AbyssListener<AbyssModels> {

    public InteractListener(final AbyssModels plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        final Player player = event.getPlayer();
        final ItemStack item = player.getItemInHand();

        if (!NBTUtils.get().contains(item, "MODEL-TYPE")) {
            return;
        }

        final ModelType type = ModelType.valueOf(NBTUtils.get().getString(item, "MODEL-TYPE"));

        final Block block = event.getClickedBlock();

        switch (type) {
            case NORMAL: {
                return;
            }

            case ARMOR_STAND_HELMET: {
                final Location location = block.getRelative(event.getBlockFace()).getLocation();
                final ArmorStand stand = block.getWorld().spawn(location, ArmorStand.class);

                stand.setGravity(false);
                stand.setInvisible(true);
                stand.setInvulnerable(true);

                stand.getEquipment().setHelmet(item);

                break;
            }
        }
    }
}
