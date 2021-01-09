package io.github.wsngamerz.killcounter;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {

    private DataManager dm = DataManager.getManager();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent deathEvent) {
        Entity entity = deathEvent.getEntity();
        if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
            if (damageEvent.getDamager() instanceof Player) {
                Player player = (Player) damageEvent.getDamager();
                String mobName = entity.getType().name();
                String[] mobData = Variables.mobList.get(mobName);
                if (mobData != null) {
                    if (dm.getUserString(player.getDisplayName(), "options", "actionbar").equals("on")) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("You killed a " + mobData[0]));
                    }
                    Integer kills = dm.getUserInt(player.getDisplayName(), mobData[1], mobData[0]);
                    kills++;
                    dm.setUserInt(player.getDisplayName(), mobData[1], mobData[0], kills);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String actionBar = dm.getUserString(player.getDisplayName(), "options", "actionbar");
        if (actionBar.equals("")) {
            dm.setUserString(player.getDisplayName(), "options", "actionbar", "on");
        }
    }
}
