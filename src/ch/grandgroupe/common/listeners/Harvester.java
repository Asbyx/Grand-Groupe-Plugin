package ch.grandgroupe.common.listeners;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.utils.Misc;
import org.bukkit.*;

import static org.bukkit.Material.*;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;

public class Harvester extends AbstractListener {
	private int radius = 1;
	private final ShapedRecipe recipe1;
	private final ShapedRecipe recipe2;
	private final NamespacedKey key1 = new NamespacedKey(Main.PLUGIN, "harvester1");
	private final NamespacedKey key2 =  new NamespacedKey(Main.PLUGIN, "harvester2");


	public Harvester() {
		ItemStack harvester = new ItemStack(DIAMOND_HOE);
		ItemMeta meta = harvester.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Harvester");
		meta.setLocalizedName("Harvester");
		harvester.setItemMeta(meta);

		recipe1 = new ShapedRecipe(key1, harvester);
		recipe1.shape(" ed", " | ", " | ");
		recipe1.setIngredient('|', STICK);
		recipe1.setIngredient('e', EMERALD);
		recipe1.setIngredient('d', DIAMOND);

		recipe2 = new ShapedRecipe(key2, harvester);
		recipe2.shape("de ", " | ", " | ");
		recipe2.setIngredient('|', STICK);
		recipe2.setIngredient('e', EMERALD);
		recipe2.setIngredient('d', DIAMOND);
	}

	@EventHandler
	public void onBreakWithHarvester(BlockBreakEvent event) {
		if (isDisabled()) return;

		if (Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getLocalizedName().equals("Harvester")) {
			Material material = event.getBlock().getType();
			World world = event.getPlayer().getWorld();

			if (grass.contains(material)) {
				for (int x = -radius; x <= radius; x++) {
					for (int z = -radius; z <= radius; z++) {
						if (world.getBlockAt(event.getBlock().getLocation().clone().add(new Vector(x, 0, z))).getType() == material) {
							world.getBlockAt(event.getBlock().getLocation().clone().add(new Vector(x, 0, z))).breakNaturally();
						}
					}
				}
			}

			if (material == WHEAT) {
				for (int x = -radius; x <= radius; x++) {
					for (int z = -radius; z <= radius; z++) {
						if (world.getBlockAt(event.getBlock().getLocation().clone().add(new Vector(x, 0, z))).getState().getBlockData().getAsString().equals("minecraft:wheat[age=7]")) {
							world.getBlockAt(event.getBlock().getLocation().clone().add(new Vector(x, 0, z))).breakNaturally();
						}
					}
				}
			}
		}
	}

	public void setRadius(int value) {
		radius = value;
	}

	private final List<Material> grass = Misc.list(
			GRASS,
			TALL_GRASS,
			SUNFLOWER,
			DANDELION,
			POPPY,
			BLUE_ORCHID,
			ALLIUM,
			AZURE_BLUET,
			RED_TULIP,
			ORANGE_TULIP,
			WHITE_TULIP,
			PINK_TULIP,
			OXEYE_DAISY,
			LILAC,
			ROSE_BUSH,
			PEONY,
			LARGE_FERN,
			CORNFLOWER,
			LILY_OF_THE_VALLEY,
			WITHER_ROSE,
			BAMBOO,
			BAMBOO_SAPLING,
			ACACIA_SAPLING,
			BIRCH_SAPLING,
			DARK_OAK_SAPLING,
			JUNGLE_SAPLING,
			OAK_SAPLING
	);

	@Override
	public void enable() {
		super.enable();
		Bukkit.addRecipe(recipe1);
		Bukkit.addRecipe(recipe2);
	}

	@Override
	public void disable() {
		super.disable();
		Bukkit.removeRecipe(key1);
		Bukkit.removeRecipe(key2);
	}
}




















