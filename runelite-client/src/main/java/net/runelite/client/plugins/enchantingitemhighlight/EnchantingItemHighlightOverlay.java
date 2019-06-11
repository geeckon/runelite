package net.runelite.client.plugins.enchantingitemhighlight;

import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnchantingItemHighlightOverlay extends WidgetItemOverlay
{
    private final ItemManager itemManager;
    private final EnchantingItemHighlightPlugin plugin;

    @Inject
    EnchantingItemHighlightOverlay(ItemManager itemManager, EnchantingItemHighlightPlugin plugin)
    {
        this.itemManager = itemManager;
        this.plugin = plugin;
        showOnInventory();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget)
    {
        if (plugin.isTargetableBySelectedSpell(itemId))
        {
            final Color color = plugin.getHighlightColor();
            if (color != null) {
                Rectangle bounds = itemWidget.getCanvasBounds();
                final BufferedImage outline = itemManager.getItemOutline(itemId, itemWidget.getQuantity(), color);
                graphics.drawImage(outline, (int) bounds.getX(), (int) bounds.getY(), null);
            }
        }
    }
}
