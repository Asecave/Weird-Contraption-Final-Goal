package com.asecave.weirdcontraptionfinalgoal;

import java.util.ArrayList;
import java.util.List;

import com.asecave.weirdcontraptionfinalgoal.blocks.resonance_engine.ResonanceEngineBlock;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeirdContraptionFinalGoal.MODID)
public class Events {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void addToItemTooltip(ItemTooltipEvent event) {
    	
    	if (event.getEntity() == null)
            return;
    	
    	if (event.getItemStack().getItem() instanceof BlockItem bi && bi.getBlock() instanceof ResonanceEngineBlock) {
    		boolean hasGoggles = GogglesItem.isWearingGoggles(event.getEntity());
    		List<Component> tooltip = new ArrayList<Component>();
    		if (Screen.hasShiftDown()) {
    			tooltip.add(Component.translatable("tooltip.weirdcontraptionfinalgoal.resonance_engine_summary1").withStyle(ChatFormatting.GRAY));
    			tooltip.add(Component.translatable("tooltip.weirdcontraptionfinalgoal.resonance_engine_summary2").withStyle(ChatFormatting.GRAY));
    		} else {
    			tooltip.add(CreateLang.translateDirect("tooltip.holdForDescription", Component.translatable("create.tooltip.keyShift").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
    		}
    		tooltip.add(Component.empty());
    		tooltip.add(Component.translatable("create.tooltip.capacityProvided").withStyle(ChatFormatting.GRAY));
    		
    		MutableComponent component = Component.literal(TooltipHelper.makeProgressBar(3, 3));   
    		component.append(hasGoggles ? Component.empty() : Component.translatable("create.tooltip.capacityProvided.high")).withStyle(IRotate.StressImpact.LOW.getAbsoluteColor());
    		
    		if (hasGoggles) {
    			component.append(CreateLang.text("16x ")
                        .add(CreateLang.translate("generic.unit.rpm"))
                        .component());
    		}
    		tooltip.add(component);
    		if (hasGoggles) {
	    		tooltip.add(Component.literal(" -> ")
	    		.append(CreateLang.translate("tooltip.up_to", CreateLang.number(1048576)).add(CreateLang.translate("generic.unit.stress")).component()).withStyle(ChatFormatting.DARK_GRAY));
    		}
    		
    		event.getToolTip().addAll(1, tooltip);
    	}
    	
        
    }
}
