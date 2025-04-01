package com.asecave.weirdcontraptionfinalgoal.items;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;

import net.minecraft.world.item.ItemStack;

public class SequenceAssemblyFoilItem extends SequencedAssemblyItem {

	public SequenceAssemblyFoilItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	@Override
	public boolean isFoil(ItemStack pStack) {
		return true;
	}
}
