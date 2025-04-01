package com.asecave.weirdcontraptionfinalgoal;

import com.asecave.weirdcontraptionfinalgoal.blocks.WCFGBlockEntities;
import com.asecave.weirdcontraptionfinalgoal.blocks.WCFGBlocks;
import com.asecave.weirdcontraptionfinalgoal.items.WCFGCreativeTabs;
import com.asecave.weirdcontraptionfinalgoal.items.WCFGItems;
import com.asecave.weirdcontraptionfinalgoal.sound.WCFGSounds;
import com.simibubi.create.foundation.data.CreateRegistrate;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WeirdContraptionFinalGoal.MODID)
public class WeirdContraptionFinalGoal {

	public static final String MODID = "weirdcontraptionfinalgoal";
	public static final CreateRegistrate CREATE_REGISTRATE = CreateRegistrate.create(MODID)
			.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

	public WeirdContraptionFinalGoal() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		CREATE_REGISTRATE.registerEventListeners(modEventBus);

		WCFGCreativeTabs.register(modEventBus);
		WCFGItems.register();
		WCFGBlocks.register();
		WCFGBlockEntities.register();
		WCFGSounds.register(modEventBus);
		modEventBus.addListener(this::commonSetup);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {

	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {

		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {

		}
	}
}
