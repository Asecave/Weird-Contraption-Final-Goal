package com.asecave.weirdcontraptionfinalgoal.blocks;

import com.asecave.weirdcontraptionfinalgoal.WeirdContraptionFinalGoal;
import com.asecave.weirdcontraptionfinalgoal.blocks.engine_casing.EngineCasingBlockEntity;
import com.asecave.weirdcontraptionfinalgoal.blocks.resonance_engine.ResonanceEngineBlockEntity;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class WCFGBlockEntities {
	public static final BlockEntityEntry<ResonanceEngineBlockEntity> RESONANCE_ENGINE = WeirdContraptionFinalGoal.CREATE_REGISTRATE
			.blockEntity("resonance_engine", ResonanceEngineBlockEntity::new)
			.visual(() -> SingleAxisRotatingVisual.ofZ(AllPartialModels.SHAFT_HALF), false)
			.validBlocks(WCFGBlocks.RESONANCE_ENGINE)
			.register();

	public static final BlockEntityEntry<EngineCasingBlockEntity> ENGINE_CASING = WeirdContraptionFinalGoal.CREATE_REGISTRATE
			.blockEntity("engine_casing", EngineCasingBlockEntity::new)
			.validBlocks(WCFGBlocks.ENGINE_CASING)
			.register();
	
	public static void register() {}
}
