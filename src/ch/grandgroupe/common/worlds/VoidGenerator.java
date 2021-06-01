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
	
	@NotNull
	@Override
	public ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.BiomeGrid biome) {
		ChunkData chunkData = createChunkData(world);
		chunkData.setRegion(0, 0, 0, 16, 256, 16, Material.AIR);
		return chunkData;
	}
	
	@NotNull
	@Override
	public List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
		return Misc.list();
	}
}
