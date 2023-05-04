package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class InfernalCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() ) {
            if ( SkillcloaksCommonConfig.INFERNAL_CLOAK_ARMOR.get() > 0 || SkillcloaksCommonConfig.INFERNAL_CLOAK_ATTACK.get() > 0 ) {
                tooltip.add(Component.translatable("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
                if ( SkillcloaksCommonConfig.INFERNAL_CLOAK_ARMOR.get() > 0 ) {
                    tooltip.add(Component.literal("+" + (SkillcloaksCommonConfig.INFERNAL_CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                            .append(Component.translatable("tooltip.skillcloaks.armor_value")));
                }
                if ( SkillcloaksCommonConfig.INFERNAL_CLOAK_ATTACK.get() > 0 ) {
                    tooltip.add(Component.literal("+" + (SkillcloaksCommonConfig.INFERNAL_CLOAK_ATTACK.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                            .append(Component.translatable("tooltip.skillcloaks.attack_value")));
                }
            }
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.INFERNAL_CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "infernal_cloak_armor").toString(), SkillcloaksCommonConfig.INFERNAL_CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
            result.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "infernal_cloak_attack").toString(), SkillcloaksCommonConfig.INFERNAL_CLOAK_ATTACK.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }
}
