package ch.grandgroupe.common.worlds;

import ch.grandgroupe.common.utils.Misc;
import org.bukkit.*;
import org.bukkit.generator.*;

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
	public ChunkData generateChunkData(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biome) {
		ChunkData chunkData = createChunkData(world);
		chunkData.setRegion(0, 0, 0, 16, 256, 16, Material.AIR);
		return chunkData;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Misc.list();
	}
}
