package com.asecave.weirdcontraptionfinalgoal.blocks.engine_casing;

import org.jetbrains.annotations.Nullable;

import com.asecave.weirdcontraptionfinalgoal.blocks.WCFGBlockEntities;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class EngineCasingBlock extends Block implements IBE<EngineCasingBlockEntity> {

	public EngineCasingBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public @Nullable PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Override
	public Class<EngineCasingBlockEntity> getBlockEntityClass() {
		return EngineCasingBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends EngineCasingBlockEntity> getBlockEntityType() {
		return WCFGBlockEntities.ENGINE_CASING.get();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
		if (pLevel.getBlockEntity(pPos) instanceof EngineCasingBlockEntity be)
			be.destroy();
		super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
	}
}
