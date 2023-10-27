package org.wcc;

import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

    public ItemStack cocaine = new ItemStack(Material.COCOA_BEANS, 3);
    ShapedRecipe cocaine_craft;

    public ItemStack ecstasy = new ItemStack(Material.FERN, 3);
    ShapedRecipe ecstasy_craft;

    public ItemStack hashish = new ItemStack(Material.BAMBOO, 3);
    ShapedRecipe hashish_craft;



    public String prefix;

    @Override
    public void onEnable(){
        saveDefaultConfig();
        reloadConfig();
        prefix = getConfig().getString("prefix");
        prefix += " &r";
        prefix = prefix.replace("&", "§");


        // LSD craft config
        ItemMeta IM = lsd.getItemMeta();
        IM.setDisplayName("LSD");
        IM.setCustomModelData(10);
        lsd.setItemMeta(IM);
        NamespacedKey key = new NamespacedKey(this, "lsd");
        lsd_craft = new ShapedRecipe(key, lsd);
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



        // Cocaine craft config
        IM = cocaine.getItemMeta();
        IM.setDisplayName("Cocaine");
        IM.setCustomModelData(10);
        cocaine.setItemMeta(IM);
        key = new NamespacedKey(this, "bamboo");
        cocaine_craft = new ShapedRecipe(key, cocaine);
        cocaine_craft.shape(
                "A11",
                "A##",
                "ASS"
        );

        cocaine_craft.setIngredient('A', Material.SUGAR_CANE);
        cocaine_craft.setIngredient('1', Material.ROTTEN_FLESH);
        cocaine_craft.setIngredient('#', Material.SWEET_BERRIES);
        cocaine_craft.setIngredient('S', Material.GRASS);
        getServer().addRecipe(cocaine_craft);



        // Ecstasy craft config
        IM = ecstasy.getItemMeta();
        IM.setDisplayName("Ecstasy");
        IM.setCustomModelData(10);
        ecstasy.setItemMeta(IM);
        key = new NamespacedKey(this, "ecstasy");
        ecstasy_craft = new ShapedRecipe(key, ecstasy);
        ecstasy_craft.shape(
                "SSS",
                "#1#",
                "AAA"
        );

        ecstasy_craft.setIngredient('A', Material.SUGAR_CANE);
        ecstasy_craft.setIngredient('1', Material.BROWN_MUSHROOM);
        ecstasy_craft.setIngredient('#', Material.STRING);
        ecstasy_craft.setIngredient('S', Material.GRASS);
        getServer().addRecipe(ecstasy_craft);



        // Cocaine craft config
        IM = hashish.getItemMeta();
        IM.setDisplayName("Hashish");
        IM.setCustomModelData(10);
        hashish.setItemMeta(IM);
        key = new NamespacedKey(this, "hashish");
        hashish_craft = new ShapedRecipe(key, hashish);
        hashish_craft.shape(
                "1#A",
                "#AS",
                "AS1"
        );

        hashish_craft.setIngredient('A', Material.SUGAR_CANE);
        hashish_craft.setIngredient('1', Material.SPIDER_EYE);
        hashish_craft.setIngredient('#', Material.GUNPOWDER);
        hashish_craft.setIngredient('S', Material.REDSTONE);
        getServer().addRecipe(hashish_craft);


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
