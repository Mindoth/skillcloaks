package net.mindoth.skillcloaks.registries;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.item.HoodItem;
import net.mindoth.skillcloaks.item.hood.*;
import net.mindoth.skillcloaks.item.sack.*;
import net.mindoth.skillcloaks.item.cloak.*;
import net.mindoth.skillcloaks.itemgroup.SkillcloaksItemGroup;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SkillcloaksItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Skillcloaks.MOD_ID);

    //Sacks
    public static final RegistryObject<Item> BROWN_SACK = REGISTRY.register("brown_sack", BrownSackItem::new);
    public static final RegistryObject<Item> GREEN_SACK = REGISTRY.register("green_sack", GreenSackItem::new);
    public static final RegistryObject<Item> RED_SACK = REGISTRY.register("red_sack", RedSackItem::new);
    public static final RegistryObject<Item> BLUE_SACK = REGISTRY.register("blue_sack", BlueSackItem::new);
    public static final RegistryObject<Item> BLACK_SACK = REGISTRY.register("black_sack", BlackSackItem::new);

    //Hoods
    public static final RegistryObject<Item> AGILITY_HOOD = REGISTRY.register("agility_hood",
            () -> new AgilityHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> ATTACK_HOOD = REGISTRY.register("attack_hood",
            () -> new AttackHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> CONSTRUCTION_HOOD = REGISTRY.register("construction_hood",
            () -> new ConstructionHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> COOKING_HOOD = REGISTRY.register("cooking_hood",
            () -> new CookingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> CRAFTING_HOOD = REGISTRY.register("crafting_hood",
            () -> new CraftingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> DEFENCE_HOOD = REGISTRY.register("defence_hood",
            () -> new DefenceHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> FARMING_HOOD = REGISTRY.register("farming_hood",
            () -> new FarmingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> FIREMAKING_HOOD = REGISTRY.register("firemaking_hood",
            () -> new FiremakingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> FISHING_HOOD = REGISTRY.register("fishing_hood",
            () -> new FishingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> FLETCHING_HOOD = REGISTRY.register("fletching_hood",
            () -> new FletchingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> HERBLORE_HOOD = REGISTRY.register("herblore_hood",
            () -> new HerbloreHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> HITPOINTS_HOOD = REGISTRY.register("hitpoints_hood",
            () -> new HitpointsHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> HUNTER_HOOD = REGISTRY.register("hunter_hood",
            () -> new HunterHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> MAGIC_HOOD = REGISTRY.register("magic_hood",
            () -> new MagicHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> MINING_HOOD = REGISTRY.register("mining_hood",
            () -> new MiningHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> PRAYER_HOOD = REGISTRY.register("prayer_hood",
            () -> new PrayerHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> RANGING_HOOD = REGISTRY.register("ranging_hood",
            () -> new RangingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> RUNECRAFT_HOOD = REGISTRY.register("runecraft_hood",
            () -> new RunecraftHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> SLAYER_HOOD = REGISTRY.register("slayer_hood",
            () -> new SlayerHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> SMITHING_HOOD = REGISTRY.register("smithing_hood",
            () -> new SmithingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> STRENGTH_HOOD = REGISTRY.register("strength_hood",
            () -> new StrengthHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> THIEVING_HOOD = REGISTRY.register("thieving_hood",
            () -> new ThievingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> WOODCUTTING_HOOD = REGISTRY.register("woodcutting_hood",
            () -> new WoodcuttingHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    public static final RegistryObject<Item> MAX_HOOD = REGISTRY.register("max_hood",
            () -> new MaxHoodItem(HoodItem.MaterialHood.HOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SkillcloaksItemGroup.SKILL_CLOAKS_TAB).fireResistant().durability(0)));

    //Cloaks
    //When making a new cloak, create required fields to: Resources, item/cloak, AbstractClientPlayerMixin, ClientRenderers,
    public static final RegistryObject<CurioItem> AGILITY_CLOAK = REGISTRY.register("agility_cloak", AgilityCloakItem::new);
    public static final RegistryObject<CurioItem> ATTACK_CLOAK = REGISTRY.register("attack_cloak", AttackCloakItem::new);
    public static final RegistryObject<CurioItem> CONSTRUCTION_CLOAK = REGISTRY.register("construction_cloak", ConstructionCloakItem::new);
    public static final RegistryObject<CurioItem> COOKING_CLOAK = REGISTRY.register("cooking_cloak", CookingCloakItem::new);
    public static final RegistryObject<CurioItem> CRAFTING_CLOAK = REGISTRY.register("crafting_cloak", CraftingCloakItem::new);
    public static final RegistryObject<CurioItem> DEFENCE_CLOAK = REGISTRY.register("defence_cloak", DefenceCloakItem::new);
    public static final RegistryObject<CurioItem> FARMING_CLOAK = REGISTRY.register("farming_cloak", FarmingCloakItem::new);
    public static final RegistryObject<CurioItem> FIREMAKING_CLOAK = REGISTRY.register("firemaking_cloak", FiremakingCloakItem::new);
    public static final RegistryObject<CurioItem> FISHING_CLOAK = REGISTRY.register("fishing_cloak", FishingCloakItem::new);
    public static final RegistryObject<CurioItem> FLETCHING_CLOAK = REGISTRY.register("fletching_cloak", FletchingCloakItem::new);
    public static final RegistryObject<CurioItem> HERBLORE_CLOAK = REGISTRY.register("herblore_cloak", HerbloreCloakItem::new);
    public static final RegistryObject<CurioItem> HITPOINTS_CLOAK = REGISTRY.register("hitpoints_cloak", HitpointsCloakItem::new);
    public static final RegistryObject<CurioItem> HUNTER_CLOAK = REGISTRY.register("hunter_cloak", HunterCloakItem::new);
    public static final RegistryObject<CurioItem> MAGIC_CLOAK = REGISTRY.register("magic_cloak", MagicCloakItem::new);
    public static final RegistryObject<CurioItem> MINING_CLOAK = REGISTRY.register("mining_cloak", MiningCloakItem::new);
    public static final RegistryObject<CurioItem> PRAYER_CLOAK = REGISTRY.register("prayer_cloak", PrayerCloakItem::new);
    public static final RegistryObject<CurioItem> RANGING_CLOAK = REGISTRY.register("ranging_cloak", RangingCloakItem::new);
    public static final RegistryObject<CurioItem> RUNECRAFT_CLOAK = REGISTRY.register("runecraft_cloak", RunecraftCloakItem::new);
    public static final RegistryObject<CurioItem> SLAYER_CLOAK = REGISTRY.register("slayer_cloak", SlayerCloakItem::new);
    public static final RegistryObject<CurioItem> SMITHING_CLOAK = REGISTRY.register("smithing_cloak", SmithingCloakItem::new);
    public static final RegistryObject<CurioItem> STRENGTH_CLOAK = REGISTRY.register("strength_cloak", StrengthCloakItem::new);
    public static final RegistryObject<CurioItem> THIEVING_CLOAK = REGISTRY.register("thieving_cloak", ThievingCloakItem::new);
    public static final RegistryObject<CurioItem> WOODCUTTING_CLOAK = REGISTRY.register("woodcutting_cloak", WoodcuttingCloakItem::new);
    public static final RegistryObject<CurioItem> MAX_CLOAK = REGISTRY.register("max_cloak", MaxCloakItem::new);
}
