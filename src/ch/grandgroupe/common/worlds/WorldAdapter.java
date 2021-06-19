package ch.grandgroupe.common.worlds;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.generator.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.bukkit.util.*;
import org.jetbrains.annotations.*;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("ALL") public class WorldAdapter implements World
{
	protected World world;
	
	public WorldAdapter() {
		this(null);
	}
	public WorldAdapter(World world) {
		this.world = world;
	}
	
	@NotNull
	@Override
	public Block getBlockAt(int i, int i1, int i2) {
		return world.getBlockAt(i, i1, i2);
	}
	@NotNull
	@Override
	public Block getBlockAt(@NotNull Location location) {
		return world.getBlockAt(location);
	}
	@Override
	public int getHighestBlockYAt(int i, int i1) {
		return world.getHighestBlockYAt(i, i1);
	}
	@Override
	public int getHighestBlockYAt(@NotNull Location location) {
		return world.getHighestBlockYAt(location);
	}
	@NotNull
	@Override
	public Block getHighestBlockAt(int i, int i1) {
		return world.getHighestBlockAt(i, i1);
	}
	@NotNull
	@Override
	public Block getHighestBlockAt(@NotNull Location location) {
		return world.getHighestBlockAt(location);
	}
	@Override
	public int getHighestBlockYAt(int i, int i1, @NotNull HeightMap heightMap) {
		return world.getHighestBlockYAt(i, i1, heightMap);
	}
	@Override
	public int getHighestBlockYAt(@NotNull Location location, @NotNull HeightMap heightMap) {
		return world.getHighestBlockYAt(location, heightMap);
	}
	@NotNull
	@Override
	public Block getHighestBlockAt(int i, int i1, @NotNull HeightMap heightMap) {
		return world.getHighestBlockAt(i, i1, heightMap);
	}
	@NotNull
	@Override
	public Block getHighestBlockAt(@NotNull Location location, @NotNull HeightMap heightMap) {
		return world.getHighestBlockAt(location, heightMap);
	}
	@NotNull
	@Override
	public Chunk getChunkAt(int i, int i1) {
		return world.getChunkAt(i, i1);
	}
	@NotNull
	@Override
	public Chunk getChunkAt(@NotNull Location location) {
		return world.getChunkAt(location);
	}
	@NotNull
	@Override
	public Chunk getChunkAt(@NotNull Block block) {
		return world.getChunkAt(block);
	}
	@Override
	public boolean isChunkLoaded(@NotNull Chunk chunk) {
		return world.isChunkLoaded(chunk);
	}
	@NotNull
	@Override
	public Chunk[] getLoadedChunks() {
		return new Chunk[0];
	}
	@Override
	public void loadChunk(@NotNull Chunk chunk) {
	
	}
	@Override
	public boolean isChunkLoaded(int i, int i1) {
		return world.isChunkLoaded(i, i1);
	}
	@Override
	public boolean isChunkGenerated(int i, int i1) {
		return world.isChunkGenerated(i, i1);
	}
	@Override
	public boolean isChunkInUse(int i, int i1) {
		return world.isChunkInUse(i, i1);
	}
	@Override
	public void loadChunk(int i, int i1) {
	
	}
	@Override
	public boolean loadChunk(int i, int i1, boolean b) {
		return world.loadChunk(i, i1, b);
	}
	@Override
	public boolean unloadChunk(@NotNull Chunk chunk) {
		return world.unloadChunk(chunk);
	}
	@Override
	public boolean unloadChunk(int i, int i1) {
		return world.unloadChunk(i, i1);
	}
	@Override
	public boolean unloadChunk(int i, int i1, boolean b) {
		return world.unloadChunk(i, i1, b);
	}
	@Override
	public boolean unloadChunkRequest(int i, int i1) {
		return world.unloadChunkRequest(i, i1);
	}
	@Override
	public boolean regenerateChunk(int i, int i1) {
		return world.regenerateChunk(i, i1);
	}
	@Override
	public boolean refreshChunk(int i, int i1) {
		return world.refreshChunk(i, i1);
	}
	@Override
	public boolean isChunkForceLoaded(int i, int i1) {
		return world.isChunkForceLoaded(i, i1);
	}
	@Override
	public void setChunkForceLoaded(int i, int i1, boolean b) {
	
	}
	@NotNull
	@Override
	public Collection<Chunk> getForceLoadedChunks() {
		return null;
	}
	@Override
	public boolean addPluginChunkTicket(int i, int i1, @NotNull Plugin plugin) {
		return world.addPluginChunkTicket(i, i1, plugin);
	}
	@Override
	public boolean removePluginChunkTicket(int i, int i1, @NotNull Plugin plugin) {
		return world.removePluginChunkTicket(i, i1, plugin);
	}
	@Override
	public void removePluginChunkTickets(@NotNull Plugin plugin) {
	
	}
	@NotNull
	@Override
	public Collection<Plugin> getPluginChunkTickets(int i, int i1) {
		return null;
	}
	@NotNull
	@Override
	public Map<Plugin, Collection<Chunk>> getPluginChunkTickets() {
		return null;
	}
	@NotNull
	@Override
	public Item dropItem(@NotNull Location location, @NotNull ItemStack itemStack) {
		return world.dropItem(location, itemStack);
	}
	@NotNull
	@Override
	public Item dropItem(@NotNull Location location, @NotNull ItemStack itemStack, @Nullable Consumer<Item> consumer) {
		return null;
	}
	@NotNull
	@Override
	public Item dropItemNaturally(@NotNull Location location, @NotNull ItemStack itemStack) {
		return world.dropItemNaturally(location, itemStack);
	}
	@NotNull
	@Override
	public Item dropItemNaturally(@NotNull Location location, @NotNull ItemStack itemStack, @Nullable Consumer<Item> consumer) {
		return null;
	}
	@NotNull
	@Override
	public Arrow spawnArrow(@NotNull Location location, @NotNull Vector vector, float v, float v1) {
		return world.spawnArrow(location, vector, v, v1);
	}
	@NotNull
	@Override
	public <T extends AbstractArrow> T spawnArrow(@NotNull Location location, @NotNull Vector vector, float v, float v1, @NotNull Class<T> aClass) {
		return null;
	}
	@Override
	public boolean generateTree(@NotNull Location location, @NotNull TreeType treeType) {
		return world.generateTree(location, treeType);
	}
	@Override
	public boolean generateTree(@NotNull Location location, @NotNull TreeType treeType, @NotNull BlockChangeDelegate blockChangeDelegate) {
		return world.generateTree(location, treeType, blockChangeDelegate);
	}
	@NotNull
	@Override
	public Entity spawnEntity(@NotNull Location location, @NotNull EntityType entityType) {
		return world.spawnEntity(location, entityType);
	}
	@NotNull
	@Override
	public LightningStrike strikeLightning(@NotNull Location location) {
		return world.strikeLightning(location);
	}
	@NotNull
	@Override
	public LightningStrike strikeLightningEffect(@NotNull Location location) {
		return world.strikeLightningEffect(location);
	}
	@NotNull
	@Override
	public List<Entity> getEntities() {
		return null;
	}
	@NotNull
	@Override
	public List<LivingEntity> getLivingEntities() {
		return null;
	}
	@NotNull
	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(@NotNull Class<T>... classes) {
		return null;
	}
	@NotNull
	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(@NotNull Class<T> aClass) {
		return null;
	}
	@NotNull
	@Override
	public Collection<Entity> getEntitiesByClasses(@NotNull Class<?>... classes) {
		return null;
	}
	@NotNull
	@Override
	public List<Player> getPlayers() {
		return null;
	}
	@NotNull
	@Override
	public Collection<Entity> getNearbyEntities(@NotNull Location location, double v, double v1, double v2) {
		return null;
	}
	@NotNull
	@Override
	public Collection<Entity> getNearbyEntities(@NotNull Location location, double v, double v1, double v2, @Nullable Predicate<Entity> predicate) {
		return null;
	}
	@NotNull
	@Override
	public Collection<Entity> getNearbyEntities(@NotNull BoundingBox boundingBox) {
		return null;
	}
	@NotNull
	@Override
	public Collection<Entity> getNearbyEntities(@NotNull BoundingBox boundingBox, @Nullable Predicate<Entity> predicate) {
		return null;
	}
	@Nullable
	@Override
	public RayTraceResult rayTraceEntities(@NotNull Location location, @NotNull Vector vector, double v) {
		return world.rayTraceEntities(location, vector, v);
	}
	@Nullable
	@Override
	public RayTraceResult rayTraceEntities(@NotNull Location location, @NotNull Vector vector, double v, double v1) {
		return world.rayTraceEntities(location, vector, v, v1);
	}
	@Nullable
	@Override
	public RayTraceResult rayTraceEntities(@NotNull Location location, @NotNull Vector vector, double v, @Nullable Predicate<Entity> predicate) {
		return null;
	}
	@Nullable
	@Override
	public RayTraceResult rayTraceEntities(@NotNull Location location, @NotNull Vector vector, double v, double v1, @Nullable Predicate<Entity> predicate) {
		return null;
	}
	@Nullable
	@Override
	public RayTraceResult rayTraceBlocks(@NotNull Location location, @NotNull Vector vector, double v) {
		return world.rayTraceBlocks(location, vector, v);
	}
	@Nullable
	@Override
	public RayTraceResult rayTraceBlocks(@NotNull Location location, @NotNull Vector vector, double v, @NotNull FluidCollisionMode fluidCollisionMode) {
		return world.rayTraceBlocks(location, vector, v, fluidCollisionMode);
	}
	@Nullable
	@Override
	public RayTraceResult rayTraceBlocks(@NotNull Location location, @NotNull Vector vector, double v, @NotNull FluidCollisionMode fluidCollisionMode, boolean b) {
		return world.rayTraceBlocks(location, vector, v, fluidCollisionMode, b);
	}
	@Nullable
	@Override
	public RayTraceResult rayTrace(@NotNull Location location, @NotNull Vector vector, double v, @NotNull FluidCollisionMode fluidCollisionMode, boolean b, double v1, @Nullable Predicate<Entity> predicate) {
		return null;
	}
	@NotNull
	@Override
	public String getName() {
		return world.getName();
	}
	@NotNull
	@Override
	public UUID getUID() {
		return world.getUID();
	}
	@NotNull
	@Override
	public Location getSpawnLocation() {
		return world.getSpawnLocation();
	}
	@Override
	public boolean setSpawnLocation(@NotNull Location location) {
		return world.setSpawnLocation(location);
	}
	@Override
	public boolean setSpawnLocation(int i, int i1, int i2, float v) {
		return world.setSpawnLocation(i, i1, i2, v);
	}
	@Override
	public boolean setSpawnLocation(int i, int i1, int i2) {
		return world.setSpawnLocation(i, i1, i2);
	}
	@Override
	public long getTime() {
		return world.getTime();
	}
	@Override
	public void setTime(long l) {
	
	}
	@Override
	public long getFullTime() {
		return world.getFullTime();
	}
	@Override
	public void setFullTime(long l) {
	
	}
	@Override
	public long getGameTime() {
		return world.getGameTime();
	}
	@Override
	public boolean hasStorm() {
		return world.hasStorm();
	}
	@Override
	public void setStorm(boolean b) {
	
	}
	@Override
	public int getWeatherDuration() {
		return world.getWeatherDuration();
	}
	@Override
	public void setWeatherDuration(int i) {
	
	}
	@Override
	public boolean isThundering() {
		return world.isThundering();
	}
	@Override
	public void setThundering(boolean b) {
	
	}
	@Override
	public int getThunderDuration() {
		return world.getThunderDuration();
	}
	@Override
	public void setThunderDuration(int i) {
	
	}
	@Override
	public boolean isClearWeather() {
		return world.isClearWeather();
	}
	@Override
	public int getClearWeatherDuration() {
		return world.getClearWeatherDuration();
	}
	@Override
	public void setClearWeatherDuration(int i) {
	
	}
	@Override
	public boolean createExplosion(double v, double v1, double v2, float v3) {
		return world.createExplosion(v, v1, v2, v3);
	}
	@Override
	public boolean createExplosion(double v, double v1, double v2, float v3, boolean b) {
		return world.createExplosion(v, v1, v2, v3, b);
	}
	@Override
	public boolean createExplosion(double v, double v1, double v2, float v3, boolean b, boolean b1) {
		return world.createExplosion(v, v1, v2, v3, b, b1);
	}
	@Override
	public boolean createExplosion(double v, double v1, double v2, float v3, boolean b, boolean b1, @Nullable Entity entity) {
		return world.createExplosion(v, v1, v2, v3, b, b1, entity);
	}
	@Override
	public boolean createExplosion(@NotNull Location location, float v) {
		return world.createExplosion(location, v);
	}
	@Override
	public boolean createExplosion(@NotNull Location location, float v, boolean b) {
		return world.createExplosion(location, v, b);
	}
	@Override
	public boolean createExplosion(@NotNull Location location, float v, boolean b, boolean b1) {
		return world.createExplosion(location, v, b, b1);
	}
	@Override
	public boolean createExplosion(@NotNull Location location, float v, boolean b, boolean b1, @Nullable Entity entity) {
		return world.createExplosion(location, v, b, b1, entity);
	}
	@NotNull
	@Override
	public Environment getEnvironment() {
		return world.getEnvironment();
	}
	@Override
	public long getSeed() {
		return world.getSeed();
	}
	@Override
	public boolean getPVP() {
		return world.getPVP();
	}
	@Override
	public void setPVP(boolean b) {
	
	}
	@Nullable
	@Override
	public ChunkGenerator getGenerator() {
		return world.getGenerator();
	}
	@Override
	public void save() {
	
	}
	@NotNull
	@Override
	public List<BlockPopulator> getPopulators() {
		return null;
	}
	@NotNull
	@Override
	public <T extends Entity> T spawn(@NotNull Location location, @NotNull Class<T> aClass) throws IllegalArgumentException {
		return null;
	}
	@NotNull
	@Override
	public <T extends Entity> T spawn(@NotNull Location location, @NotNull Class<T> aClass, @Nullable Consumer<T> consumer) throws IllegalArgumentException {
		return null;
	}
	@NotNull
	@Override
	public FallingBlock spawnFallingBlock(@NotNull Location location, @NotNull MaterialData materialData) throws IllegalArgumentException {
		return null;
	}
	@NotNull
	@Override
	public FallingBlock spawnFallingBlock(@NotNull Location location, @NotNull BlockData blockData) throws IllegalArgumentException {
		return null;
	}
	@NotNull
	@Override
	public FallingBlock spawnFallingBlock(@NotNull Location location, @NotNull Material material, byte b) throws IllegalArgumentException {
		return null;
	}
	@Override
	public void playEffect(@NotNull Location location, @NotNull Effect effect, int i) {
	
	}
	@Override
	public void playEffect(@NotNull Location location, @NotNull Effect effect, int i, int i1) {
	
	}
	@Override
	public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, @Nullable T t) {
	
	}
	@Override
	public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, @Nullable T t, int i) {
	
	}
	@NotNull
	@Override
	public ChunkSnapshot getEmptyChunkSnapshot(int i, int i1, boolean b, boolean b1) {
		return world.getEmptyChunkSnapshot(i, i1, b, b1);
	}
	@Override
	public void setSpawnFlags(boolean b, boolean b1) {
	
	}
	@Override
	public boolean getAllowAnimals() {
		return world.getAllowAnimals();
	}
	@Override
	public boolean getAllowMonsters() {
		return world.getAllowMonsters();
	}
	@NotNull
	@Override
	public Biome getBiome(int i, int i1) {
		return world.getBiome(i, i1);
	}
	@NotNull
	@Override
	public Biome getBiome(int i, int i1, int i2) {
		return world.getBiome(i, i1, i2);
	}
	@Override
	public void setBiome(int i, int i1, @NotNull Biome biome) {
	
	}
	@Override
	public void setBiome(int i, int i1, int i2, @NotNull Biome biome) {
	
	}
	@Override
	public double getTemperature(int i, int i1) {
		return world.getTemperature(i, i1);
	}
	@Override
	public double getTemperature(int i, int i1, int i2) {
		return world.getTemperature(i, i1, i2);
	}
	@Override
	public double getHumidity(int i, int i1) {
		return world.getHumidity(i, i1);
	}
	@Override
	public double getHumidity(int i, int i1, int i2) {
		return world.getHumidity(i, i1, i2);
	}
	@Override
	public int getMinHeight() {
		return world.getMinHeight();
	}
	@Override
	public int getMaxHeight() {
		return world.getMaxHeight();
	}
	@Override
	public int getSeaLevel() {
		return world.getSeaLevel();
	}
	@Override
	public boolean getKeepSpawnInMemory() {
		return world.getKeepSpawnInMemory();
	}
	@Override
	public void setKeepSpawnInMemory(boolean b) {
	
	}
	@Override
	public boolean isAutoSave() {
		return world.isAutoSave();
	}
	@Override
	public void setAutoSave(boolean b) {
	
	}
	@NotNull
	@Override
	public Difficulty getDifficulty() {
		return world.getDifficulty();
	}
	@Override
	public void setDifficulty(@NotNull Difficulty difficulty) {
	
	}
	@NotNull
	@Override
	public File getWorldFolder() {
		return world.getWorldFolder();
	}
	@Nullable
	@Override
	public WorldType getWorldType() {
		return world.getWorldType();
	}
	@Override
	public boolean canGenerateStructures() {
		return world.canGenerateStructures();
	}
	@Override
	public boolean isHardcore() {
		return world.isHardcore();
	}
	@Override
	public void setHardcore(boolean b) {
	
	}
	@Override
	public long getTicksPerAnimalSpawns() {
		return world.getTicksPerAnimalSpawns();
	}
	@Override
	public void setTicksPerAnimalSpawns(int i) {
	
	}
	@Override
	public long getTicksPerMonsterSpawns() {
		return world.getTicksPerMonsterSpawns();
	}
	@Override
	public void setTicksPerMonsterSpawns(int i) {
	
	}
	@Override
	public long getTicksPerWaterSpawns() {
		return world.getTicksPerWaterSpawns();
	}
	@Override
	public void setTicksPerWaterSpawns(int i) {
	
	}
	@Override
	public long getTicksPerWaterAmbientSpawns() {
		return world.getTicksPerWaterAmbientSpawns();
	}
	@Override
	public void setTicksPerWaterAmbientSpawns(int i) {
	
	}
	@Override
	public long getTicksPerAmbientSpawns() {
		return world.getTicksPerAmbientSpawns();
	}
	@Override
	public void setTicksPerAmbientSpawns(int i) {
	
	}
	@Override
	public int getMonsterSpawnLimit() {
		return world.getMonsterSpawnLimit();
	}
	@Override
	public void setMonsterSpawnLimit(int i) {
	
	}
	@Override
	public int getAnimalSpawnLimit() {
		return world.getAnimalSpawnLimit();
	}
	@Override
	public void setAnimalSpawnLimit(int i) {
	
	}
	@Override
	public int getWaterAnimalSpawnLimit() {
		return world.getWaterAnimalSpawnLimit();
	}
	@Override
	public void setWaterAnimalSpawnLimit(int i) {
	
	}
	@Override
	public int getWaterAmbientSpawnLimit() {
		return world.getWaterAmbientSpawnLimit();
	}
	@Override
	public void setWaterAmbientSpawnLimit(int i) {
	
	}
	@Override
	public int getAmbientSpawnLimit() {
		return world.getAmbientSpawnLimit();
	}
	@Override
	public void setAmbientSpawnLimit(int i) {
	
	}
	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, float v, float v1) {
	
	}
	@Override
	public void playSound(@NotNull Location location, @NotNull String s, float v, float v1) {
	
	}
	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory soundCategory, float v, float v1) {
	
	}
	@Override
	public void playSound(@NotNull Location location, @NotNull String s, @NotNull SoundCategory soundCategory, float v, float v1) {
	
	}
	@NotNull
	@Override
	public String[] getGameRules() {
		return new String[0];
	}
	@Nullable
	@Override
	public String getGameRuleValue(@Nullable String s) {
		return world.getGameRuleValue(s);
	}
	@Override
	public boolean setGameRuleValue(@NotNull String s, @NotNull String s1) {
		return world.setGameRuleValue(s, s1);
	}
	@Override
	public boolean isGameRule(@NotNull String s) {
		return world.isGameRule(s);
	}
	@Nullable
	@Override
	public <T> T getGameRuleValue(@NotNull GameRule<T> gameRule) {
		return null;
	}
	@Nullable
	@Override
	public <T> T getGameRuleDefault(@NotNull GameRule<T> gameRule) {
		return null;
	}
	@Override
	public <T> boolean setGameRule(@NotNull GameRule<T> gameRule, @NotNull T t) {
		return false;
	}
	@NotNull
	@Override
	public WorldBorder getWorldBorder() {
		return world.getWorldBorder();
	}
	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i) {
	
	}
	@Override
	public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i) {
	
	}
	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, @Nullable T t) {
	
	}
	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, @Nullable T t) {
	
	}
	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2) {
	
	}
	@Override
	public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5) {
	
	}
	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, @Nullable T t) {
	
	}
	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, @Nullable T t) {
	
	}
	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, double v3) {
	
	}
	@Override
	public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6) {
	
	}
	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, double v3, @Nullable T t) {
	
	}
	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6, @Nullable T t) {
	
	}
	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, double v3, @Nullable T t, boolean b) {
	
	}
	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6, @Nullable T t, boolean b) {
	
	}
	@Nullable
	@Override
	public Location locateNearestStructure(@NotNull Location location, @NotNull StructureType structureType, int i, boolean b) {
		return world.locateNearestStructure(location, structureType, i, b);
	}
	@Override
	public int getViewDistance() {
		return world.getViewDistance();
	}
	@NotNull
	@Override
	public Spigot spigot() {
		return world.spigot();
	}
	@Nullable
	@Override
	public Raid locateNearestRaid(@NotNull Location location, int i) {
		return world.locateNearestRaid(location, i);
	}
	@NotNull
	@Override
	public List<Raid> getRaids() {
		return null;
	}
	@Nullable
	@Override
	public DragonBattle getEnderDragonBattle() {
		return world.getEnderDragonBattle();
	}
	@Override
	public void setMetadata(@NotNull String s, @NotNull MetadataValue metadataValue) {
	
	}
	@NotNull
	@Override
	public List<MetadataValue> getMetadata(@NotNull String s) {
		return null;
	}
	@Override
	public boolean hasMetadata(@NotNull String s) {
		return world.hasMetadata(s);
	}
	@Override
	public void removeMetadata(@NotNull String s, @NotNull Plugin plugin) {
	
	}
	@Override
	public void sendPluginMessage(@NotNull Plugin plugin, @NotNull String s, @NotNull byte[] bytes) {
	
	}
	@NotNull
	@Override
	public Set<String> getListeningPluginChannels() {
		return null;
	}
}
