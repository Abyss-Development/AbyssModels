package net.abyssdev.abyssmodels;

import lombok.Getter;
import net.abyssdev.abysslib.menu.templates.AbyssMenu;
import net.abyssdev.abysslib.patterns.registry.Registry;
import net.abyssdev.abysslib.plugin.AbyssPlugin;
import net.abyssdev.abysslib.text.MessageCache;
import net.abyssdev.abyssmodels.command.ModelCommand;
import net.abyssdev.abyssmodels.menus.CategoryMenu;
import net.abyssdev.abyssmodels.menus.registry.CategoryRegistry;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public final class AbyssModels extends AbyssPlugin {

    private final MessageCache messageCache = new MessageCache(this.getConfig("lang"));

    private final FileConfiguration menusConfig = this.getConfig("menus");
    private final FileConfiguration categoryConfig = this.getConfig("categories");

    private final Registry<String, AbyssMenu> categoryRegistry = new CategoryRegistry(this);

    private final AbyssMenu categoryMenu = new CategoryMenu(this);
    private final ModelCommand modelCommand = new ModelCommand(this);

    @Override
    public void onEnable() {
        this.loadMessages(this.messageCache, this.getConfig("lang"));
        this.modelCommand.register();
    }

    @Override
    public void onDisable() {
        this.modelCommand.unregister();
    }

}