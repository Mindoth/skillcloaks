package net.mindoth.skillcloaks.item.hood;

import net.mindoth.skillcloaks.item.HoodItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class PrayerHoodItem extends HoodItem {
    public PrayerHoodItem(ArmorMaterial pMaterial, Type pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "skillcloaks:textures/models/armor/prayer_layer_1.png";
    }
}
