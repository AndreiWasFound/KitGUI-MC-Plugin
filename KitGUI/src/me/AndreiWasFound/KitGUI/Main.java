package me.AndreiWasFound.KitGUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin implements Listener {
	
	public static Inventory kits;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		createInventory();
	}

	@Override
	public void onDisable() {

	}
	
	
	private void createInventory() {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Kits");
		ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "Noob kit");
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(ChatColor.RED + "Click here to get this kit");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		inv.setItem(3, item);
		
		item.setType(Material.IRON_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Iron kit");
		item.setItemMeta(meta);
		inv.setItem(4, item);
		
		item.setType(Material.DIAMOND_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Diamond kit");
		item.setItemMeta(meta);
		inv.setItem(5, item);
		
		kits = inv;
	}
	

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("kits")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Console can't use this command");
				return true;	
			}
			Player player = (Player) sender;
			player.openInventory(kits);
			return true;
		}
		return false;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().contains("Kits"))
			return;
		if (event.getCurrentItem() == null)
			return;
		if (event.getCurrentItem().getItemMeta() == null)
			return;	
		
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		if (event.getClickedInventory().getType() == InventoryType.PLAYER)
			return;
		
		if (event.getSlot() == 3) {
			// slot 3 is "Noob kit"
			if (!player.hasPermission("kits.noob")) {
				player.sendMessage("You do not have permission to perform this action.");
				return;
			}
			// drops the chest
			this.dropChest(player, this.getNoobKit());
			player.closeInventory();
			player.updateInventory();
			return;
		}
		
		if (event.getSlot() == 4) {
			// slot 4 is "Iron kit"
			if (!player.hasPermission("kits.iron")) {
				player.sendMessage("You do not have permission to perform this action.");
				return;
			}
			// drops the chest
			this.dropChest(player, this.getIronKit());
			player.closeInventory();
			player.updateInventory();
			return;
			
			
		}
		
		if (event.getSlot() == 5) {
			// slot 5 is "Diamond kit"
			if (!player.hasPermission("kits.diamond")) {
				player.sendMessage("You do not have permission to perform this action.");
				return;
			}
			// drops the chest
			this.dropChest(player, this.getDiamondKit());
			player.closeInventory();
			player.updateInventory();
			return;
		}
	}
	
	
	private void dropChest(Player player, ItemStack[] items) {
		Location chest = null;
		if (player.getFacing() == BlockFace.NORTH)
			chest = player.getLocation().add(0,0,-1);
		if (player.getFacing() == BlockFace.SOUTH)
			chest = player.getLocation().add(0,0,1);
		if (player.getFacing() == BlockFace.EAST)
			chest = player.getLocation().add(1,0,0);
		if (player.getFacing() == BlockFace.WEST)
			chest = player.getLocation().add(-1,0,0);
		
		if (chest.getBlock().getType() != Material.AIR) {
			player.sendMessage(ChatColor.DARK_RED + "Cannot open kit here!");
			return;
		}
		
		Block block = chest.getBlock();
		block.setType(Material.CHEST);
		Chest c = (Chest) block.getState();
		c.getInventory().addItem(items);
		
	}
	
	
	private ItemStack[] getNoobKit() {
		ItemStack[] items = {
							new ItemStack(Material.WOODEN_SWORD),
							new ItemStack(Material.WOODEN_PICKAXE),
							new ItemStack(Material.WOODEN_AXE),
							new ItemStack(Material.WOODEN_SHOVEL),
							new ItemStack(Material.WOODEN_HOE),
							new ItemStack(Material.COOKED_BEEF,32),
							new ItemStack(Material.LEATHER_HELMET),
							new ItemStack(Material.LEATHER_CHESTPLATE),
							new ItemStack(Material.LEATHER_LEGGINGS),
							new ItemStack(Material.LEATHER_BOOTS),
							new ItemStack(Material.COAL,16),
							};
		return items;
	}
	
	private ItemStack[] getIronKit() {
		ItemStack[] items = {
							new ItemStack(Material.IRON_SWORD),
							new ItemStack(Material.IRON_PICKAXE),
							new ItemStack(Material.IRON_AXE),
							new ItemStack(Material.IRON_SHOVEL),
							new ItemStack(Material.COOKED_BEEF,32),
							new ItemStack(Material.IRON_HELMET),
							new ItemStack(Material.IRON_CHESTPLATE),
							new ItemStack(Material.IRON_LEGGINGS),
							new ItemStack(Material.IRON_BOOTS),
							};
		return items;
	}
	
	private ItemStack[] getDiamondKit() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
		item.setItemMeta(meta);
		
		ItemStack item2 = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta meta2 = item2.getItemMeta();
		meta2.addEnchant(Enchantment.DIG_SPEED, 3, true);
		item2.setItemMeta(meta2);
		
		ItemStack item3 = new ItemStack(Material.DIAMOND_HELMET);
		
		ItemStack item4 = new ItemStack(Material.DIAMOND_CHESTPLATE);
		
		ItemStack item5 = new ItemStack(Material.DIAMOND_LEGGINGS);
		
		ItemStack item6 = new ItemStack(Material.DIAMOND_BOOTS);
		
		ItemStack item7 = new ItemStack(Material.COOKED_BEEF, 64);
		
		ItemStack item8 = new ItemStack(Material.GOLDEN_APPLE, 4);
		
		ItemStack[] items = {item, item2, item3, item4, item5, item6, item7, item8};
		return items;
	}  
	
	
}









