package net.mindoth.skillcloaks.registries;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.item.HoodItem;
import net.mindoth.skillcloaks.item.cloak.*;
import net.mindoth.skillcloaks.item.hood.*;
import net.mindoth.skillcloaks.item.sack.*;
import net.mindoth.skillcloaks.itemgroup.SkillcloaksItemGroup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkillcloaksItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Skillcloaks.MOD_ID);

    public static final RegistryObject<Item> BROWN_SACK = ITEMS.register("brown_sack", BrownSackItem::new);
    public static final RegistryObject<Item> GREEN_SACK = ITEMS.register("green_sack", GreenSackItem::new);
    public static final RegistryObject<Item> RED_SACK = ITEMS.register("red_sack", RedSackItem::new);
    public static final RegistryObject<Item> BLUE_SACK = ITEMS.register("blue_sack", BlueSackItem::new);
    public static final RegistryObject<Item> BLACK_SACK = ITEMS.register("black_sack", BlackSackItem::new);

    //Hoods
    public static final RegistryObject<Item> AGILITY_HOOD = ITEMS.register("agility_hood",
            () -> new AgilityHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> ATTACK_HOOD = ITEMS.register("attack_hood",
            () -> new AttackHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> CONSTRUCTION_HOOD = ITEMS.register("construction_hood",
            () -> new ConstructionHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> COOKING_HOOD = ITEMS.register("cooking_hood",
            () -> new CookingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> CRAFTING_HOOD = ITEMS.register("crafting_hood",
            () -> new CraftingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> DEFENCE_HOOD = ITEMS.register("defence_hood",
            () -> new DefenceHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> FARMING_HOOD = ITEMS.register("farming_hood",
            () -> new FarmingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> FIREMAKING_HOOD = ITEMS.register("firemaking_hood",
            () -> new FiremakingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> FISHING_HOOD = ITEMS.register("fishing_hood",
            () -> new FishingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> FLETCHING_HOOD = ITEMS.register("fletching_hood",
            () -> new FletchingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> HERBLORE_HOOD = ITEMS.register("herblore_hood",
            () -> new HerbloreHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> HITPOINTS_HOOD = ITEMS.register("hitpoints_hood",
            () -> new HitpointsHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> HUNTER_HOOD = ITEMS.register("hunter_hood",
            () -> new HunterHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> MAGIC_HOOD = ITEMS.register("magic_hood",
            () -> new MagicHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> MINING_HOOD = ITEMS.register("mining_hood",
            () -> new MiningHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> PRAYER_HOOD = ITEMS.register("prayer_hood",
            () -> new PrayerHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> RANGING_HOOD = ITEMS.register("ranging_hood",
            () -> new RangingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> RUNECRAFT_HOOD = ITEMS.register("runecraft_hood",
            () -> new RunecraftHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SLAYER_HOOD = ITEMS.register("slayer_hood",
            () -> new SlayerHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SMITHING_HOOD = ITEMS.register("smithing_hood",
            () -> new SmithingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> STRENGTH_HOOD = ITEMS.register("strength_hood",
            () -> new StrengthHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> THIEVING_HOOD = ITEMS.register("thieving_hood",
            () -> new ThievingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> WOODCUTTING_HOOD = ITEMS.register("woodcutting_hood",
            () -> new WoodcuttingHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> MAX_HOOD = ITEMS.register("max_hood",
            () -> new MaxHoodItem(HoodItem.MaterialHood.HOOD, ArmorItem.Type.HELMET, new Item.Properties()));

    //Cloaks
    //When making a new cloak, create required fields to: textures, model, lang, AbstractClientPlayerMixin, ClientRenderers,
    public static final RegistryObject<CurioItem> AGILITY_CLOAK = ITEMS.register("agility_cloak", AgilityCloakItem::new);
    public static final RegistryObject<CurioItem> ATTACK_CLOAK = ITEMS.register("attack_cloak", AttackCloakItem::new);
    public static final RegistryObject<CurioItem> CONSTRUCTION_CLOAK = ITEMS.register("construction_cloak", ConstructionCloakItem::new);
    public static final RegistryObject<CurioItem> COOKING_CLOAK = ITEMS.register("cooking_cloak", CookingCloakItem::new);
    public static final RegistryObject<CurioItem> CRAFTING_CLOAK = ITEMS.register("crafting_cloak", CraftingCloakItem::new);
    public static final RegistryObject<CurioItem> DEFENCE_CLOAK = ITEMS.register("defence_cloak", DefenceCloakItem::new);
    public static final RegistryObject<CurioItem> FARMING_CLOAK = ITEMS.register("farming_cloak", FarmingCloakItem::new);
    public static final RegistryObject<CurioItem> FIREMAKING_CLOAK = ITEMS.register("firemaking_cloak", FiremakingCloakItem::new);
    public static final RegistryObject<CurioItem> FISHING_CLOAK = ITEMS.register("fishing_cloak", FishingCloakItem::new);
    public static final RegistryObject<CurioItem> FLETCHING_CLOAK = ITEMS.register("fletching_cloak", FletchingCloakItem::new);
    public static final RegistryObject<CurioItem> HERBLORE_CLOAK = ITEMS.register("herblore_cloak", HerbloreCloakItem::new);
    public static final RegistryObject<CurioItem> HITPOINTS_CLOAK = ITEMS.register("hitpoints_cloak", HitpointsCloakItem::new);
    public static final RegistryObject<CurioItem> HUNTER_CLOAK = ITEMS.register("hunter_cloak", HunterCloakItem::new);
    public static final RegistryObject<CurioItem> MAGIC_CLOAK = ITEMS.register("magic_cloak", MagicCloakItem::new);
    public static final RegistryObject<CurioItem> MINING_CLOAK = ITEMS.register("mining_cloak", MiningCloakItem::new);
    public static final RegistryObject<CurioItem> PRAYER_CLOAK = ITEMS.register("prayer_cloak", PrayerCloakItem::new);
    public static final RegistryObject<CurioItem> RANGING_CLOAK = ITEMS.register("ranging_cloak", RangingCloakItem::new);
    public static final RegistryObject<CurioItem> RUNECRAFT_CLOAK = ITEMS.register("runecraft_cloak", RunecraftCloakItem::new);
    public static final RegistryObject<CurioItem> SLAYER_CLOAK = ITEMS.register("slayer_cloak", SlayerCloakItem::new);
    public static final RegistryObject<CurioItem> SMITHING_CLOAK = ITEMS.register("smithing_cloak", SmithingCloakItem::new);
    public static final RegistryObject<CurioItem> STRENGTH_CLOAK = ITEMS.register("strength_cloak", StrengthCloakItem::new);
    public static final RegistryObject<CurioItem> THIEVING_CLOAK = ITEMS.register("thieving_cloak", ThievingCloakItem::new);
    public static final RegistryObject<CurioItem> WOODCUTTING_CLOAK = ITEMS.register("woodcutting_cloak", WoodcuttingCloakItem::new);
    public static final RegistryObject<CurioItem> MAX_CLOAK = ITEMS.register("max_cloak", MaxCloakItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
