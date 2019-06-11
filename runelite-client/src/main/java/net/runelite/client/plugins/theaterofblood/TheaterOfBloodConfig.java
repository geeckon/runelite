package net.runelite.client.plugins.theaterofblood;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("theaterofblood")
public interface TheaterOfBloodConfig extends Config
{
    @ConfigItem(
            position = 3,
            keyName = "showTheaterOfBlood",
            name = "Show Theater Of Blood stats",
            description = "Configures whether to display Theater Of Blood Overlay"
    )
    default boolean showTheaterOfBlood()
    {
        return true;
    }
}
