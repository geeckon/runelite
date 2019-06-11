package net.runelite.client.plugins.theaterofblood;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TheaterOfBloodOverlay extends Overlay
{
    private final Client client;
    private final TheaterOfBloodPlugin plugin;
    private final TheaterOfBloodConfig config;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    private TheaterOfBloodOverlay(Client client, TheaterOfBloodPlugin plugin, TheaterOfBloodConfig config)
    {
        setPosition(OverlayPosition.BOTTOM_LEFT);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.setPreferredSize(new Dimension(150, 100));
        panelComponent.getChildren().clear();
        panelComponent.getChildren().add(LineComponent.builder()
                .left("Damage dealt (estimate)")
                .build());

        List<TheaterOfBloodPlayer> activePlayers = plugin.getActivePlayers();

        if (activePlayers == null || activePlayers.isEmpty()) {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("No players being tracked")
                    .build());
            return panelComponent.render(graphics);
        } else {
            Collections.sort(activePlayers, new CustomComparator());
            for (TheaterOfBloodPlayer player : activePlayers) {
                panelComponent.getChildren().add(LineComponent.builder()
                        .left(player.getName())
                        .right(Integer.toString( (int) Math.floor(player.getDamageDone())))
                        .build());
            }
        }

        if (plugin.getUnknownDamage() > 0) {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Unknown: ")
                    .right(Integer.toString(plugin.getUnknownDamage()))
                    .build());
        }

        return panelComponent.render(graphics);
    }

    public class CustomComparator implements Comparator<TheaterOfBloodPlayer> {
        @Override
        public int compare(TheaterOfBloodPlayer p1, TheaterOfBloodPlayer p2) {
            return -Float.compare(p1.getDamageDone(), p2.getDamageDone());
        }
    }
}
