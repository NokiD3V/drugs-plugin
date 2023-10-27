package org.wcc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.potion.PotionEffectType.*;

public class Events implements Listener {
    private final Map<String, Integer> cooldowns = new HashMap<>();
    private final Main plugin;

    public Events(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void ballFiring(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if(e.getItem() == null) return;
        if (!e.getItem().getItemMeta().hasCustomModelData()) return;
        if(e.getItem().getItemMeta().getCustomModelData() == 10){
            if(!p.hasPermission("drugs.usedrugs")){
                p.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.permissions"));
                return;
            }
            Integer cd = cooldowns.getOrDefault(p.getUniqueId().toString(), 0);
            if(cd > 0){
                p.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.cooldown"));
                return;
            }
            cooldowns.put(p.getUniqueId().toString(), plugin.getConfig().getInt("cooldown"));

            e.getItem().setAmount(e.getItem().getAmount() - 1);
            p.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.use").replace("%drug_name%", e.getItem().getItemMeta().getDisplayName()));
            p.addPotionEffect(new PotionEffect(SPEED, 20 * 60,2));
            p.addPotionEffect(new PotionEffect(JUMP, 20 * 60,2));
            p.addPotionEffect(new PotionEffect(FAST_DIGGING, 20 * 60,2));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                p.addPotionEffect(new PotionEffect(SLOW, 20 * 90,2));
                p.addPotionEffect(new PotionEffect(BLINDNESS, 20 * 30,1));
                p.addPotionEffect(new PotionEffect(SLOW_DIGGING, 20 * 90,2));
            }, 20L * 60); // amount to wait in ticks , 20 ticks = 1 second
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                cooldowns.remove(p.getUniqueId().toString());
            }, 20L * plugin.getConfig().getInt("cooldown"));
        }
    }

    @EventHandler
    public void Craft(CraftItemEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getRecipe().getResult().getItemMeta().equals(plugin.lsd.getItemMeta())) {
            boolean isEnabled = plugin.getConfig().getBoolean("enabled");
            if(!isEnabled) {
                p.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.disabled"));
                event.setCancelled(true);
                return;
            }

            if(!p.hasPermission("drugs.createdrugs")){
                p.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.permissions"));
                event.setCancelled(true);
                return;
            }


        }

    }

}
