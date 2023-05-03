package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class MagicCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.magic_cloak"));

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
    public static void onPlayerUseMagic(final PlayerInteractEvent.RightClickItem event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        PlayerEntity player = event.getPlayer();
        if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAGIC_CLOAK.get(), player).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent() ) {
            ItemStack stack = event.getItemStack();
            if ( stack.isEnchanted() && player.isHolding(Items.BOOK) ) {
                if ( stack.isEnchanted() ) {
                    event.setCanceled(true);
                    if ( !player.level.isClientSide ) {
                        if ( player.getItemBySlot(EquipmentSlotType.MAINHAND).getItem() == Items.BOOK ) {
                            player.getItemBySlot(EquipmentSlotType.MAINHAND).shrink(1);
                        }
                        else {
                            player.getItemBySlot(EquipmentSlotType.OFFHAND).shrink(1);
                        }
                        //Map enchantments
                        Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(stack);
                        Iterator<Map.Entry<Enchantment, Integer>> enchantmentIterator = enchantmentMap.entrySet().iterator();
                        Map.Entry<Enchantment, Integer> entry = enchantmentIterator.next();
                        Enchantment enchantment = entry.getKey();
                        int enchantmentLvl = entry.getValue();
                        enchantmentMap.remove(enchantment, enchantmentLvl);
                        ItemStack dropItem = new ItemStack(Items.ENCHANTED_BOOK);
                        EnchantmentHelper.setEnchantments(ImmutableMap.of(enchantment, enchantmentLvl), dropItem);
                        EnchantmentHelper.setEnchantments(enchantmentMap, stack);

                        //Drop as item entity
                        ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), dropItem);
                        drop.setDeltaMovement(0, 0, 0);
                        drop.setNoPickUpDelay();
                        player.level.addFreshEntity(drop);

                        //Sound
                        player.level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1, 1);
                    }
                }
            }
        }
    }
}
