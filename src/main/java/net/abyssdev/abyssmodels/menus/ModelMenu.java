package net.abyssdev.abyssmodels.menus;

import net.abyssdev.abysslib.builders.ItemBuilder;
import net.abyssdev.abysslib.menu.MenuBuilder;
import net.abyssdev.abysslib.menu.item.MenuItem;
import net.abyssdev.abysslib.menu.templates.AbyssMenu;
import net.abyssdev.abysslib.nbt.NBTUtils;
import net.abyssdev.abyssmodels.AbyssModels;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public final class ModelMenu extends AbyssMenu {

    private final AbyssModels plugin;
    private final List<MenuItem> items = new LinkedList<>();

    public ModelMenu(final AbyssModels plugin, final String path) {
        super(plugin.getCategoryConfig(), path);

        this.plugin = plugin;

        for (final String key : this.plugin.getCategoryConfig().getConfigurationSection(path + "items").getKeys(false)) {
            final ItemStack item = NBTUtils.get().setString(
                    new ItemBuilder(this.plugin.getCategoryConfig(), path + "items." + key).parse(),
                    "MODEL-TYPE", this.plugin.getCategoryConfig().getString(path + "items." + key + ".type"));

            final int slot = this.plugin.getCategoryConfig().getInt(path + "items." + key + ".slot");

            this.items.add(new MenuItem(item, slot));
        }
    }

    @Override
    public void open(final Player player) {

        if (!player.hasPermission("abyssmodels.admin")) {
            this.plugin.getMessageCache().sendMessage(player, "messages.no-permission");
            return;
        }

        final MenuBuilder builder = this.createBase();

        for (final MenuItem menuItem : this.items) {
            builder.setItem(menuItem.getSlot(), menuItem.getItem());
            builder.addClickEvent(menuItem.getSlot(), event -> player.getInventory().addItem(menuItem.getItem()));
        }

        player.openInventory(builder.build());
    }

}