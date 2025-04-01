package com.asecave.weirdcontraptionfinalgoal.items;

import com.asecave.weirdcontraptionfinalgoal.WeirdContraptionFinalGoal;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class WCFGItems {

	private static final CreateRegistrate REGISTRATE = WeirdContraptionFinalGoal.CREATE_REGISTRATE;

	public static final ItemEntry<Item> LIVING_CINDER = REGISTRATE.item("living_cinder", Item::new).register();

	public static final ItemEntry<FoilItem> PULSATING_SPLINTERS = REGISTRATE.item("pulsating_splinters", FoilItem::new)
			.properties(p -> p.rarity(Rarity.UNCOMMON)).register();

	public static final ItemEntry<FoilItem> PULSATING_ROSE_QUARTZ = REGISTRATE
			.item("pulsating_rose_quartz", FoilItem::new).properties(p -> p.rarity(Rarity.UNCOMMON)).register();

	public static final ItemEntry<Item> ECHO_ROD = REGISTRATE.item("echo_rod", Item::new)
			.properties(p -> p.rarity(Rarity.RARE)).register();

	public static final ItemEntry<Item> REDUNDANCY_ENGINE = REGISTRATE.item("redundancy_engine", Item::new).register();

	public static final ItemEntry<Item> UNCOMPRESSED_ENRICHED_CORE = REGISTRATE
			.item("uncompressed_enriched_core", Item::new).properties(p -> p.rarity(Rarity.UNCOMMON)).register();

	public static final ItemEntry<FoilItem> ENRICHED_CORE = REGISTRATE.item("enriched_core", FoilItem::new)
			.properties(p -> p.rarity(Rarity.RARE)).register();

	public static final ItemEntry<SequenceAssemblyFoilItem> UNFINISHED_PULSATING_ROSE_QUATZ = REGISTRATE
			.item("unfinished_pulsating_rose_quartz", SequenceAssemblyFoilItem::new)
			.properties(p -> p.rarity(Rarity.RARE)).register();

	public static void register() {}
}
