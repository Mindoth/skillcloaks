package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SkillCloaks.MOD_ID)
public class SmithingCloakItem extends CurioItem {
    //Most of the code is in skillcloaks\network\message\CloakAbilityPacket

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.smithing_cloak"));

        if ( !SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslationTextComponent("curios.modifiers.cloak").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("+" + (SkillCloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(TextFormatting.BLUE)
                    .append(new TranslationTextComponent("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(SkillCloaks.MOD_ID, "cloak_armor").toString(), SkillCloaksCommonConfig.CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    private static BlastingRecipe getBlastingRecipe(PlayerEntity player, IInventory inv) {
        return player.level.getRecipeManager().getRecipeFor(IRecipeType.BLASTING, inv, player.level).orElse(null);
    }

    @SubscribeEvent
    public static void onPlayerUseCookingBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        PlayerEntity player = event.getPlayer();
        if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.SMITHING_CLOAK.get(), player).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAX_CLOAK.get(), player).isPresent() ) {

            ItemStack mainHandItemStack = player.getMainHandItem();
            ItemStack offHandItemStack = player.getOffhandItem();
            IInventory slotInv = new Inventory(mainHandItemStack);
            IInventory slotInvOff = new Inventory(offHandItemStack);
            BlastingRecipe recipe = getBlastingRecipe(player, slotInv);
            BlastingRecipe recipeOff = getBlastingRecipe(player, slotInvOff);

            if ((recipe != null || recipeOff != null)
                    && (mainHandItemStack.getItem() instanceof FlintAndSteelItem || offHandItemStack.getItem() instanceof FlintAndSteelItem)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerUseSmithing(final PlayerInteractEvent.RightClickItem event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        PlayerEntity player = event.getPlayer();
        World world = player.level;
        if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.SMITHING_CLOAK.get(), player).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAX_CLOAK.get(), player).isPresent() ) {

            ItemStack mainHandItemStack = player.getMainHandItem();
            ItemStack offHandItemStack = player.getOffhandItem();
            IInventory slotInv = new Inventory(mainHandItemStack);
            IInventory slotInvOff = new Inventory(offHandItemStack);
            BlastingRecipe recipe = getBlastingRecipe(player, slotInv);
            BlastingRecipe recipeOff = getBlastingRecipe(player, slotInvOff);

            if ( (recipe != null || recipeOff != null)
                    && (mainHandItemStack.getItem() instanceof FlintAndSteelItem || offHandItemStack.getItem() instanceof FlintAndSteelItem)) {
                event.setCanceled(true);
                if (offHandItemStack.getItem() instanceof FlintAndSteelItem) {
                    int size = mainHandItemStack.getCount();
                    for (int i = 0; i < size; ++i) {
                        //Check if Flint and Steel has durability
                        if (offHandItemStack.getItem() instanceof FlintAndSteelItem && offHandItemStack.getDamageValue() <= offHandItemStack.getMaxDamage()) {
                            //Get result
                            ItemStack result = recipe.assemble(slotInv);
                            if (!result.isEmpty()) {
                                mainHandItemStack.shrink(1);
                                ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), result);
                                drop.setDeltaMovement(0, 0, 0);
                                drop.setNoPickUpDelay();
                                player.level.addFreshEntity(drop);

                                offHandItemStack.hurtAndBreak(1, player, (holder) -> holder.broadcastBreakEvent(EquipmentSlotType.OFFHAND));
                            }
                        }
                    }
                    //Sound
                    world.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1, 1);
                }
                else if (mainHandItemStack.getItem() instanceof FlintAndSteelItem) {
                    int size = offHandItemStack.getCount();
                    for (int i = 0; i < size; ++i) {
                        //Check if Flint and Steel has durability
                        if (mainHandItemStack.getItem() instanceof FlintAndSteelItem && mainHandItemStack.getDamageValue() <= mainHandItemStack.getMaxDamage()) {
                            //Get result
                            ItemStack result = recipeOff.assemble(slotInv);
                            if (!result.isEmpty()) {
                                offHandItemStack.shrink(1);
                                ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), result);
                                drop.setDeltaMovement(0, 0, 0);
                                drop.setNoPickUpDelay();
                                player.level.addFreshEntity(drop);
                                mainHandItemStack.hurtAndBreak(1, player, (holder) -> holder.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                            }
                        }
                    }
                    //Sound
                    world.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1, 1);
                }
            }
        }
    }
}
