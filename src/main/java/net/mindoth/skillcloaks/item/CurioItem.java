package net.mindoth.skillcloaks.item;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class CurioItem extends Item implements ICurioItem {

    private final String name;

    public CurioItem(String name) {
        super(new Item.Properties().stacksTo(1).fireResistant());
        this.name = name;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        if (!ModCommonConfig.COSMETIC_ONLY.get()) tooltip.add(Component.translatable("tooltip.skillcloaks." + this.name));

        if ( !ModCommonConfig.COSMETIC_ONLY.get() && ModCommonConfig.SKILL_CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(Component.translatable("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("+" + (ModCommonConfig.SKILL_CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(Component.translatable("tooltip.skillcloaks.armor_value")));
        }
        super.appendHoverText(stack, context, tooltip, flagIn);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> result = ICurioItem.super.getAttributeModifiers(slotContext, id, stack);

        if (!ModCommonConfig.COSMETIC_ONLY.get() && ModCommonConfig.SKILL_CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(id, ModCommonConfig.SKILL_CLOAK_ARMOR.get(), AttributeModifier.Operation.ADD_VALUE));
        }
        return result;
    }
}
