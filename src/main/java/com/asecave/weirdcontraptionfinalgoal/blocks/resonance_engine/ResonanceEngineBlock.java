package com.asecave.weirdcontraptionfinalgoal.blocks.resonance_engine;

import org.jetbrains.annotations.Nullable;

import com.asecave.weirdcontraptionfinalgoal.blocks.WCFGBlockEntities;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.createmod.catnip.lang.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;

public class ResonanceEngineBlock extends KineticBlock implements IBE<ResonanceEngineBlockEntity> {

	public static enum ResonanceEngineState implements StringRepresentable {
		INCOMPLETE, VERIFYING, STARTING, RUNNING;

		@Override
		public String getSerializedName() {
			return Lang.asId(name());
		}
	}

	public static final EnumProperty<ResonanceEngineState> RESONANCE_ENGINE_STATE = EnumProperty
			.create("resonance_engine_state", ResonanceEngineState.class);
	public static final EnumProperty<Direction> DIRECTION = EnumProperty.create("direction", Direction.class);

	public ResonanceEngineBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(RESONANCE_ENGINE_STATE, ResonanceEngineState.INCOMPLETE)
				.setValue(DIRECTION, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
		pBuilder.add(RESONANCE_ENGINE_STATE);
		pBuilder.add(DIRECTION);
	}

	@Override
	public @Nullable PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction preferred = Direction.fromYRot(context.getRotation());
		return defaultBlockState().setValue(DIRECTION, preferred);
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
			BlockHitResult pHit) {

		if (pLevel.isClientSide())
			return InteractionResult.PASS;

		if (pHand != InteractionHand.MAIN_HAND)
			return InteractionResult.PASS;

		if (!pPlayer.getMainHandItem().isEmpty())
			return InteractionResult.PASS;

		if (pLevel.getBlockState(pPos).getValue(RESONANCE_ENGINE_STATE) != ResonanceEngineState.INCOMPLETE)
			return InteractionResult.PASS;

		if (!((ResonanceEngineBlockEntity) pLevel.getBlockEntity(pPos)).verifyAndBindStructure()) {
			pPlayer.displayClientMessage(Component.translatable("messages.weirdcontraptionfinalgoal.incomplete_message")
					.setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)), true);
			return InteractionResult.SUCCESS;
		}

		pLevel.setBlock(pPos, pState.setValue(RESONANCE_ENGINE_STATE, ResonanceEngineState.VERIFYING), UPDATE_ALL);

		return InteractionResult.SUCCESS;
	}

	

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face == Direction.UP;
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return Axis.Y;
	}

	@Override
	public Class<ResonanceEngineBlockEntity> getBlockEntityClass() {
		return ResonanceEngineBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends ResonanceEngineBlockEntity> getBlockEntityType() {
		return WCFGBlockEntities.RESONANCE_ENGINE.get();
	}

	@Override
	public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
		return false;
	}
}
