package net.abyssdev.abyssmodels.menus.registry;

import net.abyssdev.abysslib.menu.templates.AbyssMenu;
import net.abyssdev.abysslib.patterns.registry.Registry;
import net.abyssdev.abyssmodels.AbyssModels;
import net.abyssdev.abyssmodels.menus.ModelMenu;

import java.util.HashMap;
import java.util.Map;

public final class CategoryRegistry implements Registry<String, AbyssMenu> {

    private final Map<String, AbyssMenu> menus = new HashMap<>();

    public CategoryRegistry(final AbyssModels plugin) {
        for (final String key : plugin.getCategoryConfig().getConfigurationSection("categories").getKeys(false)) {
            this.menus.put(key, new ModelMenu(plugin, "categories." + key + "."));
        }
    }

    @Override
    public Map<String, AbyssMenu> getRegistry() {
        return this.menus;
    }

}