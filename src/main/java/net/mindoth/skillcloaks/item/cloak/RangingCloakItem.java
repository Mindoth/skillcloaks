package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class RangingCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(Component.translatable("tooltip.skillcloaks.ranging_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(Component.translatable("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("+" + (SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(Component.translatable("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "cloak_armor").toString(), SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @SubscribeEvent
    public static void onItemUseFinish(final LivingEntityUseItemEvent.Stop event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if (event.getEntity() instanceof Player player) {
            if (!player.level().isClientSide) {
                ItemStack pStack = event.getItem();
                boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
                Item bow = pStack.getItem();
                ItemStack itemstack = player.getProjectile(pStack);


                if (!itemstack.isEmpty() || flag) {
                    if (itemstack.isEmpty()) {
                        itemstack = new ItemStack(Items.ARROW);
                    }

                    int i = 72000 - event.getDuration();
                    float f = BowItem.getPowerForTime(i);
                    if (bow instanceof BowItem) {
                        if (!((double) f < 0.1D)) {
                            boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, pStack, player));
                            if (!flag1 && !player.getAbilities().instabuild) {

                                double randomValue = new Random().nextDouble();

                                if ( ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.RANGING_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) && randomValue <= SkillcloaksCommonConfig.ARROW_RETURN_CHANCE.get() && SkillcloaksCommonConfig.ARROW_RETURN_CHANCE.get() > 0.0 ) {
                                    ItemStack returnArrow = new ItemStack(itemstack.getItem(), 1);
                                    PotionUtils.setPotion(returnArrow, PotionUtils.getPotion(itemstack));
                                    PotionUtils.setCustomEffects(returnArrow, PotionUtils.getCustomEffects(itemstack));
                                    ItemEntity drop = new ItemEntity(player.level(), player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z, returnArrow);
                                    drop.setDeltaMovement(0, 0, 0);
                                    drop.setNoPickUpDelay();
                                    player.level().addFreshEntity(drop);
                                }

                                if (itemstack.isEmpty()) {
                                    player.getInventory().removeItem(itemstack);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
