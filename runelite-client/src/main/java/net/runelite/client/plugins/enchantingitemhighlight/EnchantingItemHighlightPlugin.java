package net.runelite.client.plugins.enchantingitemhighlight;

import com.google.common.collect.ImmutableList;
import com.google.inject.Provides;
import java.util.List;
import java.awt.Color;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Enchanting Item Highlight",
        description = "Highlight items targetable by selected enchanting spell",
        tags = {"highlight", "items", "overlay", "enchanting"},
        enabledByDefault = true
)
public class EnchantingItemHighlightPlugin extends Plugin
{
    private static final List<Integer> ITEMS = ImmutableList.of(
            ItemID.ENSOULED_DRAGON_HEAD_13511,
            ItemID.ENSOULED_DRAGON_HEAD
    );

    @Inject
    private Client client;

    @Inject
    private ConfigManager configManager;

    @Inject
    private MenuManager menuManager;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private EnchantingItemHighlightConfig config;

    @Inject
    private EnchantingItemHighlightOverlay overlay;

    @Provides
    EnchantingItemHighlightConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(EnchantingItemHighlightConfig.class);
    }

    Boolean isTargetableBySelectedSpell(int itemId)
    {
        return ITEMS.contains(itemId);
    }

    Color getHighlightColor()
    {
        return config.getHighlightColor();
    }

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
    }
}
