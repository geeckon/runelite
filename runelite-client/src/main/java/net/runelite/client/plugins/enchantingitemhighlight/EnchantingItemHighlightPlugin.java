package net.runelite.client.plugins.enchantingitemhighlight;

import com.google.common.collect.ImmutableList;
import com.google.inject.Provides;

import java.util.HashMap;
import java.util.List;
import java.awt.Color;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Enchanting Item Highlight",
        description = "Highlight items targetable by selected enchanting spell",
        tags = {"highlight", "items", "overlay", "enchanting"},
        enabledByDefault = true
)
public class EnchantingItemHighlightPlugin extends Plugin
{
    private HashMap<String, List<Integer>> SPELL_ITEMS = new HashMap<>();

    private String CAST = "Cast";

    private String CAST_ON = " ->";

    private String CANCEL = "Cancel";

    @Getter
    @Setter
    private String casting;

    public EnchantingItemHighlightPlugin() {
        List<Integer> LVL_1_ENCHANT_ITEMS = ImmutableList.of(
                ItemID.SAPPHIRE_RING,
                ItemID.SAPPHIRE_NECKLACE,
                ItemID.SAPPHIRE_BRACELET,
                ItemID.SAPPHIRE_AMULET,
                ItemID.OPAL_RING,
                ItemID.OPAL_BRACELET,
                ItemID.OPAL_NECKLACE,
                ItemID.OPAL_AMULET
        );
        List<Integer> LVL_2_ENCHANT_ITEMS = ImmutableList.of(
                ItemID.EMERALD_RING,
                ItemID.EMERALD_NECKLACE,
                ItemID.EMERALD_BRACELET,
                ItemID.EMERALD_AMULET,
                ItemID.JADE_RING,
                ItemID.JADE_BRACELET,
                ItemID.JADE_NECKLACE,
                ItemID.JADE_AMULET
        );
        List<Integer> LVL_3_ENCHANT_ITEMS = ImmutableList.of(
                ItemID.RUBY_RING,
                ItemID.RUBY_NECKLACE,
                ItemID.RUBY_BRACELET,
                ItemID.RUBY_AMULET,
                ItemID.TOPAZ_RING,
                ItemID.TOPAZ_BRACELET,
                ItemID.TOPAZ_NECKLACE,
                ItemID.TOPAZ_AMULET
        );
        List<Integer> LVL_4_ENCHANT_ITEMS = ImmutableList.of(
                ItemID.DIAMOND_RING,
                ItemID.DIAMOND_NECKLACE,
                ItemID.DIAMOND_BRACELET,
                ItemID.DIAMOND_AMULET
        );
        List<Integer> LVL_5_ENCHANT_ITEMS = ImmutableList.of(
                ItemID.DRAGONSTONE_RING,
                ItemID.DRAGONSTONE_BRACELET,
                ItemID.DRAGONSTONE_AMULET
        );
        List<Integer> LVL_6_ENCHANT_ITEMS = ImmutableList.of(
                ItemID.ONYX_RING,
                ItemID.ONYX_BRACELET,
                ItemID.ONYX_AMULET,
                ItemID.ONYX_NECKLACE
        );
        List<Integer> LVL_7_ENCHANT_ITEMS = ImmutableList.of(
                ItemID.ZENYTE_RING,
                ItemID.ZENYTE_BRACELET,
                ItemID.ZENYTE_AMULET,
                ItemID.ZENYTE_NECKLACE
        );

        SPELL_ITEMS.put("Lvl-1 Enchant", LVL_1_ENCHANT_ITEMS);
        SPELL_ITEMS.put("Lvl-2 Enchant", LVL_2_ENCHANT_ITEMS);
        SPELL_ITEMS.put("Lvl-3 Enchant", LVL_3_ENCHANT_ITEMS);
        SPELL_ITEMS.put("Lvl-4 Enchant", LVL_4_ENCHANT_ITEMS);
        SPELL_ITEMS.put("Lvl-5 Enchant", LVL_5_ENCHANT_ITEMS);
        SPELL_ITEMS.put("Lvl-6 Enchant", LVL_6_ENCHANT_ITEMS);
        SPELL_ITEMS.put("Lvl-7 Enchant", LVL_7_ENCHANT_ITEMS);
    }

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
        final String casting = this.getCasting();

        if (casting != null) {
            final String[] splitString = casting.split(CAST_ON);

            List<Integer> itemIds = SPELL_ITEMS.get(splitString[0]);

            if (itemIds != null) {
                return itemIds.contains(itemId);
            }
        }

        return false;
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        final String option = event.getOption();
        final String target = Text.removeTags(event.getTarget());
        if (option.equals(CAST)) {
            this.setCasting(target);
        } else if (!option.equals(CANCEL)) {
            this.setCasting("");
        }
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
