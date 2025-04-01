package com.asecave.weirdcontraptionfinalgoal.blocks.resonance_engine;

import org.joml.Vector3f;

import com.asecave.weirdcontraptionfinalgoal.blocks.engine_casing.EngineCasingBlock;
import com.asecave.weirdcontraptionfinalgoal.blocks.engine_casing.EngineCasingBlockEntity;
import com.asecave.weirdcontraptionfinalgoal.blocks.resonance_engine.ResonanceEngineBlock.ResonanceEngineState;
import com.asecave.weirdcontraptionfinalgoal.sound.WCFGSounds;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ResonanceEngineBlockEntity extends GeneratingKineticBlockEntity {

	private EngineSound startSound;
	private EngineSound continuousSound;
	private EngineSound shutdownSound;

	private int ticksSinceStart = 0;
	private static final int TICKS_UNTIL_ENABLED = 888;

	public ResonanceEngineBlockEntity(BlockEntityType<? extends ResonanceEngineBlockEntity> type, BlockPos pos,
			BlockState state) {
		super(type, pos, state);
		startSound = new EngineSound(WCFGSounds.RESONANCE_ENGINE_STARTING.get(), SoundSource.BLOCKS, false);
		continuousSound = new EngineSound(WCFGSounds.RESONANCE_ENGINE_LOOP.get(), SoundSource.BLOCKS, true);
		shutdownSound = new EngineSound(WCFGSounds.RESONANCE_ENGINE_SHUTDOWN.get(), SoundSource.BLOCKS, false);

		startSound.setLocation(pos.getCenter());
		continuousSound.setLocation(pos.getCenter());
		shutdownSound.setLocation(pos.getCenter());
	}

	@Override
	public float getGeneratedSpeed() {
		if (getBlockState().getValue(ResonanceEngineBlock.RESONANCE_ENGINE_STATE) == ResonanceEngineState.RUNNING)
			return 256f;
		else
			return 0f;
	}

	@Override
	public float calculateAddedStressCapacity() {
		return 4096f;
	}

	@Override
	public void onChunkUnloaded() {
		super.onChunkUnloaded();
		startSound.stopSound();
		continuousSound.stopSound();
		shutdownSound.stopSound();
	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (getLevel().isClientSide())
			return;
		if (getBlockState().getValue(ResonanceEngineBlock.RESONANCE_ENGINE_STATE) != ResonanceEngineState.INCOMPLETE) {
			if (!verifyAndBindStructure()) {
				stopEngine(true);
			}
		}
	}

	boolean verifyAndBindStructure() {
		boolean xAxis = getBlockState().getValue(ResonanceEngineBlock.DIRECTION).getAxis() == Axis.X;
		Vec3i[] structurePositions = { new Vec3i(-1, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(-1, 0, 1),
				new Vec3i(-0, 0, -1), new Vec3i(0, 0, 1), new Vec3i(1, 0, -1), new Vec3i(1, 0, 0), new Vec3i(1, 0, 1),
				new Vec3i(-1, 1, -1), new Vec3i(-1, 1, 0), new Vec3i(-1, 1, 1), new Vec3i(-0, 1, -1),
				new Vec3i(0, 1, 1), new Vec3i(1, 1, -1), new Vec3i(1, 1, 0), new Vec3i(1, 1, 1),
				new Vec3i(xAxis ? 0 : 2, 0, xAxis ? 2 : 0), new Vec3i(xAxis ? 0 : 2, 1, xAxis ? 2 : 0),
				new Vec3i(xAxis ? 0 : -2, 0, xAxis ? -2 : 0), new Vec3i(xAxis ? 0 : -2, 1, xAxis ? -2 : 0), };

		boolean oneFailed = false;
		for (Vec3i coords : structurePositions) {
			BlockPos p = getBlockPos().offset(coords);
			if (getLevel().getBlockState(p).getBlock() instanceof AirBlock) {
				structureParticles(Minecraft.getInstance(), p, true);
				oneFailed = true;
			} else if (!(getLevel().getBlockState(p).getBlock() instanceof EngineCasingBlock)) {
				structureParticles(Minecraft.getInstance(), p, false);
				oneFailed = true;
			}
		}
		if (oneFailed)
			return false;

		for (Vec3i coords : structurePositions) {
			BlockPos p = getBlockPos().offset(coords);
			if (getLevel().getBlockEntity(p) instanceof EngineCasingBlockEntity casing) {
				casing.setEnginePos(getBlockPos());
			}
		}
		return true;
	}

	private void structureParticles(Minecraft mc, BlockPos p, boolean isAir) {
		final float pad = isAir ? 0.2f : -0.01f;
		final int density = isAir ? 5 : 10;
		DustParticleOptions options = new DustParticleOptions(new Vector3f(.9f, isAir ? .9f : .3f, .1f), .5f);
		for (int i = 0; i < density; i++) {
			float pos = ((float) i / density) * (1f - 2f * pad) + pad;

			mc.level.addParticle(options, pos + p.getX(), pad + p.getY(), pad + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, pad + p.getX(), pos + p.getY(), pad + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, pad + p.getX(), pad + p.getY(), pos + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, pos + p.getX(), (1f - pad) + p.getY(), pad + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, (1f - pad) + p.getX(), pos + p.getY(), pad + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, (1f - pad) + p.getX(), pad + p.getY(), pos + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, pos + p.getX(), pad + p.getY(), (1f - pad) + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, pad + p.getX(), pos + p.getY(), (1f - pad) + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, pad + p.getX(), (1f - pad) + p.getY(), pos + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, pos + p.getX(), (1f - pad) + p.getY(), (1f - pad) + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, (1f - pad) + p.getX(), pos + p.getY(), (1f - pad) + p.getZ(), 0, 0, 0);
			mc.level.addParticle(options, (1f - pad) + p.getX(), (1f - pad) + p.getY(), pos + p.getZ(), 0, 0, 0);
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (getLevel().isClientSide()) {
			switch (getBlockState().getValue(ResonanceEngineBlock.RESONANCE_ENGINE_STATE)) {
			case INCOMPLETE:
				ticksSinceStart = 0;
				break;
			case STARTING:
				ticksSinceStart++;
				
				if (ticksSinceStart % (1 + (TICKS_UNTIL_ENABLED - ticksSinceStart) / 20) == 0 && ticksSinceStart < TICKS_UNTIL_ENABLED - 15) {
					Vec3 particlePos = new Vec3(0f, 0f, 0f);
					particlePos = particlePos.offsetRandom(getLevel().getRandom(), 4f);
					particlePos = particlePos.multiply(1f, 0f, 1f);
					particlePos = particlePos.add(0f, 1.5f, 0f);
					particlePos = particlePos.add(getBlockPos().getCenter());
					spawnParticle(ParticleTypes.SONIC_BOOM, particlePos);
				}
				if (ticksSinceStart != TICKS_UNTIL_ENABLED - 1)
					break;
				Vec3 pos = getBlockPos().getCenter().add(0f, 1.5f, 0f);
				for (int i = 0; i < 200; i++) {
					float dx = (float) Mth.sin(i / 200f * Mth.TWO_PI) * 0.7f;
					float dy = (float) Mth.cos(i / 200f * Mth.TWO_PI) * 0.7f;
					getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y, pos.z, dx, 0f, dy);
					for (int j = 0; j < 3; j++) {
						float rand = getLevel().getRandom().nextFloat();
						dx *= 1f - (rand * rand);
						dy *= 1f - (rand * rand);
						getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y, pos.z, dx, 0f, dy);
					}
				
				}
				break;
			case RUNNING:
				ticksSinceStart++;
				if (ticksSinceStart % 4 != 0)
					break;
				Vec3 pos2 = new Vec3(1.6f, 0.5f, 0f);
				pos2 = pos2.add(0f, getLevel().getRandom().nextFloat() * 2f - 1f, 0f);
				pos2 = pos2.add(0f, 0f, getLevel().getRandom().nextFloat() * 3f - 1.5f);
				int side = getLevel().getRandom().nextInt(4);
				pos2 = pos2.yRot(Mth.HALF_PI * side);
				pos2 = pos2.add(getBlockPos().getCenter());
				spawnParticle(ParticleTypes.END_ROD, pos2);
				break;
			default:
				break;
			}
			
			return;
		}

		switch (getBlockState().getValue(ResonanceEngineBlock.RESONANCE_ENGINE_STATE)) {
		case INCOMPLETE:
			break;
		case VERIFYING:
			startSound.playSound();
			getLevel().setBlock(getBlockPos(), getBlockState().setValue(ResonanceEngineBlock.RESONANCE_ENGINE_STATE,
					ResonanceEngineState.STARTING), Block.UPDATE_ALL);
			break;
		case STARTING:
			ticksSinceStart++;

			if (ticksSinceStart < TICKS_UNTIL_ENABLED)
				return;

			continuousSound.playSound();
			getLevel().setBlock(getBlockPos(),
					getBlockState().setValue(ResonanceEngineBlock.RESONANCE_ENGINE_STATE, ResonanceEngineState.RUNNING),
					Block.UPDATE_ALL);
			updateGeneratedRotation();

			CommandSourceStack source = getLevel().getServer().createCommandSourceStack().withSuppressedOutput();

			String x = getBlockPos().getX() + " ";
			String y = getBlockPos().getY() + 1 + " ";
			String z = getBlockPos().getZ() + " ";
			
			String command = "summon item_display " + x + y + z + "{"
						+ "glow_color_override:1b,"
						+ "item_display:\"head\","
						+ "transformation:{"
							+ "left_rotation:[0f,0f,0f,1f],"
							+ "right_rotation:[0f,0f,0f,1f],"
							+ "translation:[0f,0f,0f],"
							+ "scale:[3.1f,2.1f,3.1f]"
						+ "},"
						+ "item:{"
							+ "id:\"minecraft:black_stained_glass\","
							+ "Count:1b,"
							+ "tag:{"
								+ "Enchantments:[{}]"
							+ "}"
						+ "}"
					+ "}";
			
			getLevel().getServer().getCommands().performPrefixedCommand(source, command);
			
			command = "summon item_display " + x + y + z + "{"
						+ "glow_color_override:1b,"
						+ "item_display:\"head\","
						+ "transformation:{"
							+ "left_rotation:[0f,0f,0f,1f],"
							+ "right_rotation:[0f,0f,0f,1f],"
							+ "translation:[0f,0f,0f],"
							+ "scale:[5.1f,2.095f,1.1f]"
						+ "},"
						+ "item:{"
							+ "id:\"minecraft:black_stained_glass\","
							+ "Count:1b,tag:{"
								+ "Enchantments:[{}]"
							+ "}"
						+ "}"
					+ "}";

			getLevel().getServer().getCommands().performPrefixedCommand(source, command);

			break;
		case RUNNING:
			if (!Minecraft.getInstance().getSoundManager().isActive(continuousSound) && getLevel().getRandom().nextInt(8) == 0)
				continuousSound.playSound();
			break;
		}
	}
	
	private void spawnParticle(ParticleOptions particleType, Vec3 pos) {
		getLevel().addParticle(particleType, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
	}

	@Override
	public void destroy() {
		if (!getLevel().isClientSide()) {
			stopEngine(false);
		}
		super.destroy();
	}

	public void stopEngine(boolean updateState) {
		startSound.stopSound();
		continuousSound.stopSound();
		if (getBlockState().getValue(ResonanceEngineBlock.RESONANCE_ENGINE_STATE) == ResonanceEngineState.RUNNING) {
			shutdownSound.playSound();
		}

		CommandSourceStack source = getLevel().getServer().createCommandSourceStack().withSuppressedOutput();
		String x = getBlockPos().getX() + " ";
		String y = getBlockPos().getY() + " ";
		String z = getBlockPos().getZ() + " ";
		String command = "execute positioned " + x + y + z + "run kill @e[type=minecraft:item_display,distance=..1]";
		getLevel().getServer().getCommands().performPrefixedCommand(source, command);
		
		if (!updateState)
			return;

		ticksSinceStart = 0;
		getLevel().setBlock(getBlockPos(),
				getBlockState().setValue(ResonanceEngineBlock.RESONANCE_ENGINE_STATE, ResonanceEngineState.INCOMPLETE),
				Block.UPDATE_ALL);
		updateGeneratedRotation();
	}

	class EngineSound extends AbstractTickableSoundInstance {

		protected EngineSound(SoundEvent event, SoundSource source, boolean loop) {
			super(event, source, SoundInstance.createUnseededRandom());
			looping = loop;
		}

		@Override
		public void tick() {
		}

		public void setLocation(Vec3 location) {
			x = location.x;
			y = location.y;
			z = location.z;
		}

		public void playSound() {
			Minecraft.getInstance().getSoundManager().play(this);
		}

		public void stopSound() {
			Minecraft.getInstance().getSoundManager().stop(this);
		}
	}
}
