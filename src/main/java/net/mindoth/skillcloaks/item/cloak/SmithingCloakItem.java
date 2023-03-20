package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SkillCloaks.MOD_ID)
public class SmithingCloakItem extends CurioItem {
    //Most of the code is in skillcloaks/network/message/CloakAbilityPacket

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslatableComponent("tooltip.skillcloaks.smithing_cloak"));

        if ( !SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslatableComponent("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(new TextComponent("+" + (SkillCloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(new TranslatableComponent("tooltip.skillcloaks.armor_value")));
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

    private static BlastingRecipe getBlastingRecipe(Player player, Container inv) {
        return player.level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, inv, player.level).orElse(null);
    }

    @SubscribeEvent
    public static void onPlayerUseSmithingBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getPlayer();
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.SMITHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.MAX_CLOAK.get()).isPresent() ) {

            ItemStack mainHandItemStack = player.getMainHandItem();
            ItemStack offHandItemStack = player.getOffhandItem();
            Container slotInv = new SimpleContainer(mainHandItemStack);
            Container slotInvOff = new SimpleContainer(offHandItemStack);
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
        Player player = event.getPlayer();
        Level world = player.level;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.SMITHING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.MAX_CLOAK.get()).isPresent() ) {

            ItemStack mainHandItemStack = player.getMainHandItem();
            ItemStack offHandItemStack = player.getOffhandItem();
            Container slotInv = new SimpleContainer(mainHandItemStack);
            Container slotInvOff = new SimpleContainer(offHandItemStack);
            BlastingRecipe recipe = getBlastingRecipe(player, slotInv);
            BlastingRecipe recipeOff = getBlastingRecipe(player, slotInvOff);

            if ( (recipe != null || recipeOff != null)
                    && (mainHandItemStack.getItem() instanceof FlintAndSteelItem || offHandItemStack.getItem() instanceof FlintAndSteelItem)) {
                event.setCanceled(true);
                //Check if Flint and Steel in offhand
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

                                offHandItemStack.hurtAndBreak(1, player, (holder) -> holder.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                            }
                        }
                    }
                    //Sound
                    world.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1, 1);
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
                                mainHandItemStack.hurtAndBreak(1, player, (holder) -> holder.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                            }
                        }
                    }
                    //Sound
                    world.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1, 1);
                }
            }
        }
    }
}
