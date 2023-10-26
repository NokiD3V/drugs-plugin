package org.wcc;

import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main extends JavaPlugin implements CommandExecutor {
    public ItemStack lsd = new ItemStack(Material.SUGAR, 3);
    ShapedRecipe lsd_craft;

    public String prefix;

    @Override
    public void onEnable(){
        saveDefaultConfig();
        reloadConfig();
        prefix = getConfig().getString("prefix");
        prefix += " &r";
        prefix = prefix.replace("&", "§");
        ItemMeta IM = lsd.getItemMeta();
        IM.setDisplayName("LSD");
        IM.setCustomModelData(10);
        lsd.setItemMeta(IM);
        lsd_craft = new ShapedRecipe(lsd);


        lsd_craft.shape(
                "1A1",
                "#A#",
                "SAS"
        );

        lsd_craft.setIngredient('A', Material.SUGAR_CANE);
        lsd_craft.setIngredient('1', Material.ROTTEN_FLESH);
        lsd_craft.setIngredient('#', Material.WHEAT_SEEDS);
        lsd_craft.setIngredient('S', Material.STRING);
        getServer().addRecipe(lsd_craft);

        getServer().getPluginManager().registerEvents(new Events(this), this);

        getCommand("drugs").setExecutor(this);

        getLogger().info("Plugin is enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Usage command");
            player.sendMessage(cmd.getName());
            if(player.hasPermission("drugs.admin") || player.isOp()){
                if(args.length > 0){
                    player.sendMessage(args[0]);
                    if(args[0].contains("reload")){
                        reloadConfig();
                        player.sendMessage(prefix + "Drugs plugin reloaded!");
                    }
                }
                else {
                    player.sendMessage(prefix+ "Chose a correct config");
                }
            }
        }
        return true;
    }

    @Override
    public void onDisable(){
        getLogger().info("Stop plugin...");
    }
}
