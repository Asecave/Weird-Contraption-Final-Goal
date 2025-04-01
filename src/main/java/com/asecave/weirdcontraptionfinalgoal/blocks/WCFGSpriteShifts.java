package com.asecave.weirdcontraptionfinalgoal.blocks;

import com.asecave.weirdcontraptionfinalgoal.WeirdContraptionFinalGoal;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;

import net.minecraft.resources.ResourceLocation;

public class WCFGSpriteShifts {

	public static final CTSpriteShiftEntry ENGINE_CASING = getCT(AllCTTypes.OMNIDIRECTIONAL, "engine_casing");
	public static final CTSpriteShiftEntry ENGINE_CASING_SIDE = getCT(AllCTTypes.OMNIDIRECTIONAL, "engine_casing_side");

	private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
		return CTSpriteShifter.getCT(type, asResource("block/" + blockTextureName),
				asResource("block/" + blockTextureName + "_connected"));
	}

	private static ResourceLocation asResource(String name) {
		return ResourceLocation.fromNamespaceAndPath(WeirdContraptionFinalGoal.MODID, name);
	}
}
