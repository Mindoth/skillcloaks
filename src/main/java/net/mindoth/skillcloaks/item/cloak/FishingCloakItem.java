package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class FishingCloakItem extends CurioItem {

    public FishingCloakItem(String name) {
        super(name);
    }

    @Override
    protected void extraAttributes(Multimap<Holder<Attribute>, AttributeModifier> result, SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if ( !ModCommonConfig.COSMETIC_ONLY.get() ) {
            result.put(Attributes.LUCK, new AttributeModifier(id, ModCommonConfig.FISHING_LUCK.get(), AttributeModifier.Operation.ADD_VALUE));
        }
    }
}
