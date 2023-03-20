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
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

@Mod.EventBusSubscriber(modid = SkillCloaks.MOD_ID)
public class RangingCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslatableComponent("tooltip.skillcloaks.ranging_cloak"));

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

    @SubscribeEvent
    public static void onItemUseFinish(final LivingEntityUseItemEvent.Stop event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if (event.getEntityLiving() instanceof Player player) {
            if (!player.level.isClientSide) {
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

                                Random r = new Random();
                                double randomValue = r.nextDouble();

                                if ( ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.RANGING_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.MAX_CLOAK.get()).isPresent() ) && randomValue < SkillCloaksCommonConfig.ARROW_RETURN_CHANCE.get()) {
                                    ItemStack returnArrow = new ItemStack(itemstack.getItem(), 1);
                                    PotionUtils.setPotion(returnArrow, PotionUtils.getPotion(itemstack));
                                    PotionUtils.setCustomEffects(returnArrow, PotionUtils.getCustomEffects(itemstack));
                                    if (player.getInventory().getFreeSlot() > -1) {
                                        player.addItem(returnArrow);
                                    } else player.spawnAtLocation(returnArrow, 0);

                                /*
                                if ( itemstack.getCount() != itemstack.getMaxStackSize() ) {
                                    itemstack.grow(1);
                                }
                                if ( player.getInventory().getFreeSlot() > -1 ) {
                                    player.addItem(returnArrow);
                                }
                                else if ( player.getOffhandItem().getItem().equals(itemstack.getItem()) || player.getOffhandItem().isEmpty() ) {
                                    player.setItemSlot(EquipmentSlot.OFFHAND, returnArrow);
                                    if (!player.level.isClientSide) System.out.println("Offhand new item");
                                }
                                else {
                                    player.spawnAtLocation(returnArrow);
                                }
                                */
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
