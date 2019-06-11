package net.runelite.client.plugins.enchantingitemhighlight;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("enchantingitemhighlight")
public interface EnchantingItemHighlightConfig extends Config
{
    String GROUP = "enchantingitemhighlight";

    @ConfigItem(
            position = 0,
            keyName = "highlightColor",
            name = "Highlight color",
            description = "Color to highlight the targetable items"
    )
    default Color getHighlightColor()
    {
        return new Color(0, 255, 0);
    }
}
