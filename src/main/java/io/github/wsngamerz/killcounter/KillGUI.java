package io.github.wsngamerz.killcounter;

import net.minecraft.server.v1_13_R1.NBTTagCompound;
import net.minecraft.server.v1_13_R1.NBTTagList;
import net.minecraft.server.v1_13_R1.NBTTagString;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KillGUI implements Listener {

    private DataManager dm = DataManager.getManager();

    private Inventory killMenu = Bukkit.createInventory(null, 27, "Kills");
    private Inventory hostileKillMenu = Bukkit.createInventory(null, 27, "Hostile Kills");
    private Inventory neutralKillMenu = Bukkit.createInventory(null, 27, "Neutral Kills");
    private Inventory passiveKillMenu = Bukkit.createInventory(null, 27, "Passive Kills");

    public void mainMenu(Player player) {
        // Hostile Menu Item
        ItemStack hostileItem = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta hostileItemMeta = hostileItem.getItemMeta();
        hostileItemMeta.setDisplayName("Hostile Mob Kills");
        List<String> hostileItemLore = new ArrayList<>();
        hostileItemLore.add("They just want to kill you!");
        hostileItemMeta.setLore(hostileItemLore);
        hostileItem.setItemMeta(hostileItemMeta);

        // Neutral Menu Item
        ItemStack neutralItem = new ItemStack(Material.GRASS_BLOCK, 1);
        ItemMeta neutralItemMeta = neutralItem.getItemMeta();
        neutralItemMeta.setDisplayName("Neutral Mob Kills");
        List<String> neutralItemLore = new ArrayList<>();
        neutralItemLore.add("Don't annoy them. Be warned!");
        neutralItemMeta.setLore(neutralItemLore);
        neutralItem.setItemMeta(neutralItemMeta);

        // Passive Menu Item
        ItemStack passiveItem = new ItemStack(Material.WHEAT, 1);
        ItemMeta passiveItemMeta = passiveItem.getItemMeta();
        passiveItemMeta.setDisplayName("Passive Mob Kills");
        List<String> passiveItemLore = new ArrayList<>();
        passiveItemLore.add("The friendly ones");
        passiveItemMeta.setLore(passiveItemLore);
        passiveItem.setItemMeta(passiveItemMeta);

        killMenu.setItem(11, hostileItem); // Hostile
        killMenu.setItem(13, neutralItem); // Neutral
        killMenu.setItem(15, passiveItem); // Passive

        player.openInventory(killMenu);
    }

    public void hostileMenu(Player player) {
        for (String[] mobData : Variables.mobList.values()) {
            if (mobData[1].equals("Hostile")) {
                Integer kills = dm.getUserInt(player.getDisplayName(), mobData[1], mobData[0]);
                hostileKillMenu.addItem(mobHead(mobData, kills));
            }
        }
        player.openInventory(hostileKillMenu);
    }

    public void neutralMenu(Player player) {
        for (String[] mobData : Variables.mobList.values()) {
            if (mobData[1].equals("Neutral")) {
                Integer kills = dm.getUserInt(player.getDisplayName(), mobData[1], mobData[0]);
                neutralKillMenu.addItem(mobHead(mobData, kills));
            }
        }
        player.openInventory(neutralKillMenu);
    }

    public void passiveMenu(Player player) {
        for (String[] mobData : Variables.mobList.values()) {
            if (mobData[1].equals("Passive")) {
                Integer kills = dm.getUserInt(player.getDisplayName(), mobData[1], mobData[0]);
                passiveKillMenu.addItem(mobHead(mobData, kills));
            }
        }
        player.openInventory(passiveKillMenu);
    }

    private ItemStack mobHead(String[] mobData, Integer kills) {

        ItemStack mobHead = new ItemStack(Material.PLAYER_HEAD, 1);

        // Using NMS for item nbt
        net.minecraft.server.v1_13_R1.ItemStack is = CraftItemStack.asNMSCopy(mobHead);
        NBTTagCompound tag = is.getTag();
        NBTTagCompound display = new NBTTagCompound();
        NBTTagCompound headOwner = new NBTTagCompound();
        NBTTagCompound headProperties = new NBTTagCompound();
        NBTTagCompound textureValue = new NBTTagCompound();
        NBTTagList lore = new NBTTagList();
        NBTTagList textures = new NBTTagList();


        if (tag == null) {
            tag = new NBTTagCompound();
        }

        // NBT Lore
        lore.add(new NBTTagString("==============="));
        lore.add(new NBTTagString(mobData[0] + " Kills"));
        lore.add(new NBTTagString(kills.toString()));
        lore.add(new NBTTagString("==============="));

        // NBT Player Texture
        headOwner.setString("Id", mobData[2]);
        textureValue.setString("Value", mobData[3]);
        textures.add(textureValue);
        headProperties.set("textures", textures);

        // Display Tags
        display.set("Lore", lore);
        display.set("Name", new NBTTagString("{\"text\":\"" + mobData[0] + "\"}"));

        // Root tags
        headOwner.set("Properties", headProperties);
        tag.set("display", display);
        tag.set("SkullOwner", headOwner);
        is.setTag(tag);

        // Convert and return values
        mobHead = CraftItemStack.asBukkitCopy(is);
        return mobHead;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inventory = event.getInventory();

        Bukkit.getLogger().info("Clicked");

        if (inventory.getName().equals(killMenu.getName())) {

            if (clicked.getType().equals(Material.IRON_SWORD)) {
                // Hostile Mob Kills
                player.closeInventory();
                hostileMenu(player);

            } else if (clicked.getType().equals(Material.GRASS_BLOCK)) {
                // Neutral Mob Kills
                player.closeInventory();
                neutralMenu(player);

            } else if (clicked.getType().equals(Material.WHEAT)) {
                // Passive Mob Kills
                player.closeInventory();
                passiveMenu(player);

            }

        }
    }

}
