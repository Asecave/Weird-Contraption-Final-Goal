package com.asecave.weirdcontraptionfinalgoal.blocks.engine_casing;

import com.asecave.weirdcontraptionfinalgoal.blocks.resonance_engine.ResonanceEngineBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EngineCasingBlockEntity extends BlockEntity {
	
	private BlockPos enginePos = null;

	public EngineCasingBlockEntity(BlockEntityType<? extends EngineCasingBlockEntity> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}
	
	public void setEnginePos(BlockPos pos) {
		enginePos = pos;
	}
	
	public void destroy() {
		if (getLevel().isClientSide())
			return;
		
		if (enginePos == null)
			return;
		
		if (getLevel().getBlockEntity(enginePos) instanceof ResonanceEngineBlockEntity engine)
			engine.stopEngine(true);
		else
			enginePos = null;
	}
}
