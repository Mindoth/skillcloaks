package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
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

import static net.minecraft.item.CrossbowItem.getChargeDuration;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class FletchingCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.fletching_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslationTextComponent("curios.modifiers.cloak").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("+" + (SkillcloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(TextFormatting.BLUE)
                    .append(new TranslationTextComponent("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "cloak_armor").toString(), SkillcloaksCommonConfig.CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @SubscribeEvent
    public static void onCrossbowUseFinish(final LivingEntityUseItemEvent.Stop event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (!player.level.isClientSide) {
                ItemStack pStack = event.getItem();
                boolean flag = player.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
                Item crossbow = pStack.getItem();
                ItemStack pAmmoStack = player.getProjectile(pStack);

                if (!pAmmoStack.isEmpty() || flag) {
                    if (pAmmoStack.isEmpty()) {
                        pAmmoStack = new ItemStack(Items.ARROW);
                    }

                    if (crossbow instanceof CrossbowItem) {
                        int pCount = event.getDuration();
                        float f = (float) (pStack.getUseDuration() - pCount) / (float) getChargeDuration(pStack);
                        if (f >= 1.0f) {
                            boolean flag1 = player.abilities.instabuild || (pAmmoStack.getItem() instanceof ArrowItem && ((ArrowItem) pAmmoStack.getItem()).isInfinite(pAmmoStack, pStack, player));
                            if (!flag1 && !player.abilities.instabuild) {

                                Random r = new Random();
                                double randomValue = r.nextDouble();

                                if ( ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.FLETCHING_CLOAK.get(), player).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent() ) && randomValue < SkillcloaksCommonConfig.ARROW_RETURN_CHANCE.get() ) {
                                    ItemStack returnArrow = new ItemStack(pAmmoStack.getItem(), 1);
                                    PotionUtils.setPotion(returnArrow, PotionUtils.getPotion(pAmmoStack));
                                    PotionUtils.setCustomEffects(returnArrow, PotionUtils.getCustomEffects(pAmmoStack));
                                    ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), returnArrow);
                                    drop.setDeltaMovement(0, 0, 0);
                                    drop.setNoPickUpDelay();
                                    player.level.addFreshEntity(drop);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /*
        //Damage by arrow is 0 and moves target towards shooter
        @SubscribeEvent
        public static void onArrowHit(final LivingAttackEvent event) {
            World world = event.getEntityLiving().level;
            if ( !world.isClientSide && ( event.getSource().getEntity() instanceof LivingEntity) ) {
                LivingEntity entity = event.getEntityLiving();
                LivingEntity player = (LivingEntity)event.getSource().getEntity();
                if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.FLETCHING_CLOAK.get(), (LivingEntity)event.getSource().getEntity()).isPresent() ) {
                    if ( player.level.equals(world) ) {
                        if ( event.getSource().getDirectEntity() instanceof ArrowEntity && !(entity instanceof PlayerEntity) ) {
                            event.setCanceled(true);
                            event.getSource().getDirectEntity().remove();
                            Vector3d origin = new Vector3d(player.getX(), player.getY() + player.getEyeHeight() - 0.25, player.getZ());
                            entity.setDeltaMovement((origin.x - entity.getX()) / 5, Math.min((origin.y - entity.getY()) / 5, 2), (origin.z - entity.getZ()) / 5);
                            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundCategory.PLAYERS, 1, 0.1F);
                        }
                    }
                }
            }
        }*/
}
