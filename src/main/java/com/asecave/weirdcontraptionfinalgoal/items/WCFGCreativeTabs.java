package com.asecave.weirdcontraptionfinalgoal.items;

import com.asecave.weirdcontraptionfinalgoal.WeirdContraptionFinalGoal;
import com.asecave.weirdcontraptionfinalgoal.blocks.WCFGBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class WCFGCreativeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, WeirdContraptionFinalGoal.MODID);

	public static final RegistryObject<CreativeModeTab> WCFG_TAB = CREATIVE_MODE_TABS.register("wcfg_tab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(WCFGItems.PULSATING_ROSE_QUARTZ.get()))
			.title(Component.translatable("creativetab.wcfg_tab"))
			.displayItems((pParameters, pOutput) -> {
				pOutput.accept(WCFGItems.LIVING_CINDER.get());
				pOutput.accept(WCFGItems.PULSATING_SPLINTERS.get());
				pOutput.accept(WCFGItems.PULSATING_ROSE_QUARTZ.get());
				pOutput.accept(WCFGItems.ECHO_ROD.get());
				pOutput.accept(WCFGItems.REDUNDANCY_ENGINE.get());
				pOutput.accept(WCFGItems.UNCOMPRESSED_ENRICHED_CORE.get());
				pOutput.accept(WCFGItems.ENRICHED_CORE.get());
				pOutput.accept(WCFGBlocks.RESONANCE_ENGINE.get());
				pOutput.accept(WCFGBlocks.ENGINE_CASING.get());
			})
			.build());
	
	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
