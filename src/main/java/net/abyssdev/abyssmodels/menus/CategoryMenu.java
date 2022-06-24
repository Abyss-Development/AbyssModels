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
import java.util.Optional;

public final class CategoryMenu extends AbyssMenu {

    private final AbyssModels plugin;
    private final List<MenuItem> items = new LinkedList<>();

    public CategoryMenu(final AbyssModels plugin) {
        super(plugin.getMenusConfig(), "menus.categories.");

        this.plugin = plugin;

        for (final String key : this.plugin.getMenusConfig().getConfigurationSection("menus.categories.items").getKeys(false)) {
            final ItemStack item = NBTUtils.get().setString(
                    new ItemBuilder(this.plugin.getMenusConfig(), "menus.categories.items." + key).parse(),
                    "CATEGORY", this.plugin.getMenusConfig().getString("menus.categories.items." + key + ".category"));

            final int slot = this.plugin.getMenusConfig().getInt("menus.categories.items." + key + ".slot");

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
            builder.addClickEvent(menuItem.getSlot(), event -> {
                final Optional<AbyssMenu> menu = this.plugin.getCategoryRegistry().get(NBTUtils.get().getString(menuItem.getItem(), "CATEGORY"));

                if (!menu.isPresent()) {
                    return;
                }

                menu.get().open(player);
            });
        }

        player.openInventory(builder.build());
    }
}