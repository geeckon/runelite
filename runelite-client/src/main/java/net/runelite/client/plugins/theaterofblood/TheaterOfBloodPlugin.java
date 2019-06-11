package net.runelite.client.plugins.theaterofblood;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.*;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PluginDescriptor(
        name = "Theater of Blood",
        description = "Show Theater of Blood overlay",
        tags = {"raids", "theater", "blood", "raids2"}
)
public class TheaterOfBloodPlugin extends Plugin
{
    @Getter @Setter
    private boolean count = false;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private TheaterOfBloodOverlay overlay;

    @Inject
    private TheaterOfBloodConfig config;

    @Getter
    private List<TheaterOfBloodPlayer> activePlayers;

    @Getter
    private int unknownDamage = 0;

    @Provides
    TheaterOfBloodConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(TheaterOfBloodConfig.class);
    }

    public TheaterOfBloodPlugin() {
        activePlayers = new ArrayList<>();
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

    @Subscribe
    public void onAnimationChanged(final AnimationChanged event)
    {
        List<Integer> animationIds =  new ArrayList<>();
        animationIds.add(AnimationID.CRUSH_ATTACK);
        animationIds.add(AnimationID.SLASH_ATTACK);
        animationIds.add(AnimationID.STAB_ATTACK);
        animationIds.add(AnimationID.BOW_ATTACK);
        animationIds.add(AnimationID.CROSSBOW_ATTACK);
        animationIds.add(AnimationID.BLOWPIPE_ATTACK);
        animationIds.add(AnimationID.TRIDENT_ATTACK);
        animationIds.add(AnimationID.BLITZ_ATTACK);
        animationIds.add(AnimationID.BARRAGE_ATTACK);
        animationIds.add(AnimationID.WHIP_ATTACK);
        animationIds.add(AnimationID.CLAWS_ATTACK);
        animationIds.add(AnimationID.SCYTHE_ATTACK);
        animationIds.add(AnimationID.GODSWORD_SLASH_ATTACK);
        animationIds.add(AnimationID.GODSWORD_CRUSH_ATTACK);
        animationIds.add(AnimationID.HALBERD_STAB_ATTACK);
        animationIds.add(AnimationID.HALBERD_SLASH_ATTACK);
        animationIds.add(AnimationID.BANDOS_GODSWORD_SPECIAL_ATTACK);
        animationIds.add(AnimationID.HALBERD_SPECIAL_ATTACK);
        animationIds.add(AnimationID.WARHAMMER_SPECIAL_ATTACK);
        animationIds.add(AnimationID.CLAWS_SPECIAL_ATTACK);

        Actor actor = event.getActor();

        // Is player
        if (!(actor instanceof Player) || actor.getAnimation() < 0) {
//        if (actor.getName() != "Geks") {
            return;
        }

        System.out.println(actor.getWorldArea().distanceTo(actor.getInteracting().getWorldArea()));
        this.setCount(true);
        return;

//        int animationId = actor.getAnimation();
//
//        // Reset scores with yes emote
//        if (actor == client.getLocalPlayer() && animationId == AnimationID.EMOTE_YES) {
//            activePlayers.clear();
//            return;
//        }
//
//        // Is not attack event
//        if (!animationIds.contains(animationId)) {
//            if (actor.getName() != null && animationId != -1) {
//                System.out.println(actor.getName());
//                System.out.println(Integer.toString(animationId));
//            }
//            return;
//        }
//
//        String actorName = actor.getName();
//
//        // Add to active players list if not added and there are less than 5 people
//        if (!containsName(actor.getName()) && (activePlayers.size() < 5)) {
//            TheaterOfBloodPlayer player = new TheaterOfBloodPlayer(actor.getName());
//            activePlayers.add(player);
//        }
//
//        TheaterOfBloodPlayer player = getByName(actorName);
//
//        Actor target = actor.getInteracting();
//
//        player.setTarget(target);
//        int distanceToTarget = actor.getWorldArea().distanceTo(target.getWorldArea());
//        int ticksRemaining = 0;
//
//        if (animationId == AnimationID.BLITZ_ATTACK || animationId == AnimationID.BARRAGE_ATTACK || animationId == AnimationID.TRIDENT_ATTACK) {
//            ticksRemaining = (int) Math.ceil((distanceToTarget + 2) / 3.0) + 1;
//        } else if (animationId == AnimationID.BLOWPIPE_ATTACK) {
//            ticksRemaining = (int) Math.min(Math.ceil(distanceToTarget / 5.0) + 1, 2);
//        } else if (animationId == AnimationID.BOW_ATTACK || animationId == AnimationID.CROSSBOW_ATTACK) {
//            ticksRemaining = (int) Math.ceil((distanceToTarget + 4) / 6.0) + 1;
//        } else {
//            // Melee
//            ticksRemaining = 1;
//        }
//        player.setTicksRemaining(ticksRemaining);
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (this.count) {
            System.out.println("tick");
        }
//        if (activePlayers == null) {
//            return;
//        }
//
//        for (TheaterOfBloodPlayer player : activePlayers) {
//            player.advanceTick();
//        }
    }

    @Subscribe
    public void onHitsplat(HitsplatApplied event)
    {
        System.out.println(event.getHitsplat().getAmount());
        this.setCount(false);
//        Actor actor = event.getActor();
//        Hitsplat hitsplat = event.getHitsplat();
//
//        List<TheaterOfBloodPlayer> players = getByTargetAndTick(actor);
//
//        if (players.isEmpty()) {
//            return;
//        }
//
//        // Damage on players
//        if (actor instanceof Player) {
//            return;
//        }
//
//        // Heal
//        if (hitsplat.getHitsplatType().equals(Hitsplat.HitsplatType.HEAL)) {
//            return;
//        }
//
//        int damage = hitsplat.getAmount();
//
//        // Damage from unknown source
//        if (!hitsplat.getHitsplatType().equals(Hitsplat.HitsplatType.DAMAGE)) {
//            unknownDamage += damage;
//        }
//
//        if (players.size() == 1) {
//            TheaterOfBloodPlayer player = players.get(0);
//            player.addDamageDone(damage);
//        } else {
//            float splitDamage = damage / (float) players.size();
//
//            for (TheaterOfBloodPlayer player : players) {
//                player.addDamageDone(splitDamage);
//            }
//        }
    }

    public boolean containsName(String name){
        for (TheaterOfBloodPlayer player : activePlayers) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public TheaterOfBloodPlayer getByName(String name) {
        for (TheaterOfBloodPlayer player : activePlayers) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public List<TheaterOfBloodPlayer> getByTargetAndTick(Actor target) {
        List<TheaterOfBloodPlayer> newList = new ArrayList<>();

        for (TheaterOfBloodPlayer player : activePlayers) {
            if (player.getTarget() == target && player.getTicksRemaining() == 0) {
                newList.add(player);
            }
        }
        return newList;
    }
}
