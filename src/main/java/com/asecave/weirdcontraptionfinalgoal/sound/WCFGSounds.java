package com.asecave.weirdcontraptionfinalgoal.sound;

import com.asecave.weirdcontraptionfinalgoal.WeirdContraptionFinalGoal;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WCFGSounds {

	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
			.create(ForgeRegistries.SOUND_EVENTS, WeirdContraptionFinalGoal.MODID);

	public static final RegistryObject<SoundEvent> RESONANCE_ENGINE_STARTING = registerSoundEvents(
			"resonance_engine_starting");
	public static final RegistryObject<SoundEvent> RESONANCE_ENGINE_LOOP = registerSoundEvents("resonance_engine_loop");
	public static final RegistryObject<SoundEvent> RESONANCE_ENGINE_SHUTDOWN = registerSoundEvents(
			"resonance_engine_shutdown");

	public static final SoundType ENGINE_CASING_SOUND = new ForgeSoundType(1.0f, 0.75f,
			() -> SoundEvents.NETHERITE_BLOCK_BREAK, () -> SoundEvents.NETHERITE_BLOCK_STEP,
			() -> SoundEvents.NETHERITE_BLOCK_PLACE, () -> SoundEvents.NETHERITE_BLOCK_HIT,
			() -> SoundEvents.NETHERITE_BLOCK_FALL);

	private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
				ResourceLocation.fromNamespaceAndPath(WeirdContraptionFinalGoal.MODID, name)));
	}

	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}
}
