package net.mindoth.skillcloaks.registries;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.item.armor.HoodItem;
import net.mindoth.skillcloaks.item.armor.ModArmorMaterials;
import net.mindoth.skillcloaks.item.cloak.*;
import net.mindoth.skillcloaks.item.sack.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Skillcloaks.MOD_ID);

    public static final DeferredItem<Item> BROWN_SACK = ITEMS.register("brown_sack", BrownSackItem::new);
    public static final DeferredItem<Item> GREEN_SACK = ITEMS.register("green_sack", GreenSackItem::new);
    public static final DeferredItem<Item> RED_SACK = ITEMS.register("red_sack", RedSackItem::new);
    public static final DeferredItem<Item> BLUE_SACK = ITEMS.register("blue_sack", BlueSackItem::new);
    public static final DeferredItem<Item> BLACK_SACK = ITEMS.register("black_sack", BlackSackItem::new);

    //Hoods
    public static final DeferredItem<Item> AGILITY_HOOD = ITEMS.register("agility_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "agility_layer_1"));

    public static final DeferredItem<Item> ATTACK_HOOD = ITEMS.register("attack_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "attack_layer_1"));

    public static final DeferredItem<Item> CONSTRUCTION_HOOD = ITEMS.register("construction_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "construction_layer_1"));

    public static final DeferredItem<Item> COOKING_HOOD = ITEMS.register("cooking_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "cooking_layer_1"));

    public static final DeferredItem<Item> CRAFTING_HOOD = ITEMS.register("crafting_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "crafting_layer_1"));

    public static final DeferredItem<Item> DEFENCE_HOOD = ITEMS.register("defence_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "defence_layer_1"));

    public static final DeferredItem<Item> FARMING_HOOD = ITEMS.register("farming_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "farming_layer_1"));

    public static final DeferredItem<Item> FIREMAKING_HOOD = ITEMS.register("firemaking_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "firemaking_layer_1"));

    public static final DeferredItem<Item> FISHING_HOOD = ITEMS.register("fishing_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "fishing_layer_1"));

    public static final DeferredItem<Item> FLETCHING_HOOD = ITEMS.register("fletching_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "fletching_layer_1"));

    public static final DeferredItem<Item> HERBLORE_HOOD = ITEMS.register("herblore_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "herblore_layer_1"));

    public static final DeferredItem<Item> HITPOINTS_HOOD = ITEMS.register("hitpoints_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "hitpoints_layer_1"));

    public static final DeferredItem<Item> HUNTER_HOOD = ITEMS.register("hunter_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "hunter_layer_1"));

    public static final DeferredItem<Item> MAGIC_HOOD = ITEMS.register("magic_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "magic_layer_1"));

    public static final DeferredItem<Item> MINING_HOOD = ITEMS.register("mining_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "mining_layer_1"));

    public static final DeferredItem<Item> PRAYER_HOOD = ITEMS.register("prayer_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "prayer_layer_1"));

    public static final DeferredItem<Item> RANGING_HOOD = ITEMS.register("ranging_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "ranging_layer_1"));

    public static final DeferredItem<Item> RUNECRAFT_HOOD = ITEMS.register("runecraft_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "runecraft_layer_1"));

    public static final DeferredItem<Item> SLAYER_HOOD = ITEMS.register("slayer_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "slayer_layer_1"));

    public static final DeferredItem<Item> SMITHING_HOOD = ITEMS.register("smithing_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "smithing_layer_1"));

    public static final DeferredItem<Item> STRENGTH_HOOD = ITEMS.register("strength_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "strength_layer_1"));

    public static final DeferredItem<Item> THIEVING_HOOD = ITEMS.register("thieving_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "thieving_layer_1"));

    public static final DeferredItem<Item> WOODCUTTING_HOOD = ITEMS.register("woodcutting_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "woodcutting_layer_1"));

    public static final DeferredItem<Item> MAX_HOOD = ITEMS.register("max_hood",
            () -> new HoodItem(ModArmorMaterials.HOOD, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(0), "max_layer_1"));
    
    //Cloaks
    //When making a new cloak, create required fields to: textures, model, lang, AbstractClientPlayerMixin, ClientRenderers,
    public static final DeferredItem<CurioItem> AGILITY_CLOAK = ITEMS.register("agility_cloak", () -> new CurioItem("agility_cloak"));
    public static final DeferredItem<CurioItem> ATTACK_CLOAK = ITEMS.register("attack_cloak", () -> new CurioItem("attack_cloak"));
    public static final DeferredItem<CurioItem> CONSTRUCTION_CLOAK = ITEMS.register("construction_cloak", () -> new CurioItem("construction_cloak"));
    public static final DeferredItem<CurioItem> COOKING_CLOAK = ITEMS.register("cooking_cloak", () -> new CookingCloakItem("cooking_cloak"));
    public static final DeferredItem<CurioItem> CRAFTING_CLOAK = ITEMS.register("crafting_cloak", () -> new CurioItem("crafting_cloak"));
    public static final DeferredItem<CurioItem> DEFENCE_CLOAK = ITEMS.register("defence_cloak", () -> new DefenceCloakItem("defence_cloak"));
    public static final DeferredItem<CurioItem> FARMING_CLOAK = ITEMS.register("farming_cloak", () -> new FarmingCloakItem("farming_cloak"));
    public static final DeferredItem<CurioItem> FIREMAKING_CLOAK = ITEMS.register("firemaking_cloak", () -> new FiremakingCloakItem("firemaking_cloak"));
    public static final DeferredItem<CurioItem> FISHING_CLOAK = ITEMS.register("fishing_cloak", () -> new FishingCloakItem("fishing_cloak"));
    public static final DeferredItem<CurioItem> FLETCHING_CLOAK = ITEMS.register("fletching_cloak", () -> new FletchingCloakItem("fletching_cloak"));
    public static final DeferredItem<CurioItem> HERBLORE_CLOAK = ITEMS.register("herblore_cloak", () -> new HerbloreCloakItem("herblore_cloak"));
    public static final DeferredItem<CurioItem> HITPOINTS_CLOAK = ITEMS.register("hitpoints_cloak", () -> new HitpointsCloakItem("hitpoints_cloak"));
    public static final DeferredItem<CurioItem> HUNTER_CLOAK = ITEMS.register("hunter_cloak", () -> new HunterCloakItem("hunter_cloak"));
    public static final DeferredItem<CurioItem> MAGIC_CLOAK = ITEMS.register("magic_cloak", () -> new MagicCloakItem("magic_cloak"));
    public static final DeferredItem<CurioItem> MINING_CLOAK = ITEMS.register("mining_cloak", () -> new MiningCloakItem("mining_cloak"));
    public static final DeferredItem<CurioItem> PRAYER_CLOAK = ITEMS.register("prayer_cloak", () -> new PrayerCloakItem("prayer_cloak"));
    public static final DeferredItem<CurioItem> RANGING_CLOAK = ITEMS.register("ranging_cloak", () -> new RangingCloakItem("ranging_cloak"));
    public static final DeferredItem<CurioItem> RUNECRAFT_CLOAK = ITEMS.register("runecraft_cloak", () -> new CurioItem("runecraft_cloak"));
    public static final DeferredItem<CurioItem> SLAYER_CLOAK = ITEMS.register("slayer_cloak", () -> new SlayerCloakItem("slayer_cloak"));
    public static final DeferredItem<CurioItem> SMITHING_CLOAK = ITEMS.register("smithing_cloak", () -> new SmithingCloakItem("smithing_cloak"));
    public static final DeferredItem<CurioItem> STRENGTH_CLOAK = ITEMS.register("strength_cloak", () -> new StrengthCloakItem("strength_cloak"));
    public static final DeferredItem<CurioItem> THIEVING_CLOAK = ITEMS.register("thieving_cloak", () -> new ThievingCloakItem("thieving_cloak"));
    public static final DeferredItem<CurioItem> WOODCUTTING_CLOAK = ITEMS.register("woodcutting_cloak", () -> new WoodcuttingCloakItem("woodcutting_cloak"));
    public static final DeferredItem<CurioItem> MAX_CLOAK = ITEMS.register("max_cloak", () -> new MaxCloakItem("max_cloak"));
}
