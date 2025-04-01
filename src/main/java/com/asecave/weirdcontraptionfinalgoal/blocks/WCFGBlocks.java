package com.asecave.weirdcontraptionfinalgoal.blocks;

import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

import java.util.function.Supplier;

import com.asecave.weirdcontraptionfinalgoal.WeirdContraptionFinalGoal;
import com.asecave.weirdcontraptionfinalgoal.blocks.engine_casing.EngineCasingBlock;
import com.asecave.weirdcontraptionfinalgoal.blocks.resonance_engine.ResonanceEngineBlock;
import com.asecave.weirdcontraptionfinalgoal.items.WCFGCreativeTabs;
import com.asecave.weirdcontraptionfinalgoal.sound.WCFGSounds;
import com.simibubi.create.AllTags.AllBlockTags;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.ModelGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.minecraft.world.item.Rarity;

public class WCFGBlocks {

	static {
		WeirdContraptionFinalGoal.CREATE_REGISTRATE.setCreativeTab(WCFGCreativeTabs.WCFG_TAB); 
	}

	public static final BlockEntry<ResonanceEngineBlock> RESONANCE_ENGINE = WeirdContraptionFinalGoal.CREATE_REGISTRATE
			.block("resonance_engine", ResonanceEngineBlock::new)
			.initialProperties(SharedProperties::netheriteMetal)
			.properties(p -> p.noOcclusion()
					.explosionResistance(1200f)
					.destroyTime(20f)
					.sound(WCFGSounds.ENGINE_CASING_SOUND))
			.transform(TagGen.pickaxeOnly())
			.onRegister(BlockStressValues.setGeneratorSpeed(256, false))
			.item()
			.properties(p -> p.rarity(Rarity.EPIC))
			.transform(ModelGen.customItemModel())
			.register();

	public static final BlockEntry<EngineCasingBlock> ENGINE_CASING = WeirdContraptionFinalGoal.CREATE_REGISTRATE
			.block("engine_casing", EngineCasingBlock::new)
			.transform(layeredCasing(() -> WCFGSpriteShifts.ENGINE_CASING_SIDE,
					() -> WCFGSpriteShifts.ENGINE_CASING))
			.properties(p -> p.explosionResistance(1200f)
					.destroyTime(20f)
					.sound(WCFGSounds.ENGINE_CASING_SOUND))
			.transform(TagGen.pickaxeOnly())
			.simpleItem()
			.register();
			
	public static <B extends EngineCasingBlock> NonNullUnaryOperator<BlockBuilder<B, CreateRegistrate>> layeredCasing(
			Supplier<CTSpriteShiftEntry> ct, Supplier<CTSpriteShiftEntry> ct2) {
			return b -> b.initialProperties(SharedProperties::netheriteMetal)
				.blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
					.cubeColumn(c.getName(), ct.get()
							.getOriginalResourceLocation(),
						ct2.get()
							.getOriginalResourceLocation())))
				.onRegister(connectedTextures(() -> new HorizontalCTBehaviour(ct.get(), ct2.get())))
				.onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct.get())))
				.tag(AllBlockTags.CASING.tag)
				.item()
				.tag(AllItemTags.CASING.tag)
				.build();
		}

	public static void register() {}
}
