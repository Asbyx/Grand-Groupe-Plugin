package ch.grandgroupe.common.worlds;

import ch.grandgroupe.common.utils.Misc;
import org.bukkit.*;
import org.bukkit.generator.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class VoidGenerator extends ChunkGenerator
{
	@Override
	public boolean shouldGenerateCaves() {
		return false;
	}
	@Override
	public boolean shouldGenerateDecorations() {
		return false;
	}
	@Override
	public boolean shouldGenerateMobs() {
		return false;
	}
	@Override
	public boolean shouldGenerateStructures() {
		return false;
	}

	@Override
	public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z, ChunkGenerator.@NotNull BiomeGrid biome) {
		ChunkData chunkData = createChunkData(world);
		chunkData.setRegion(0, 0, 0, 16, 256, 16, Material.AIR);
		return chunkData;
	}
	
	@SuppressWarnings("SpellCheckingInspection")
	@Override
	public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
		return Misc.list();
	}
}
