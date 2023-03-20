package net.mindoth.skillcloaks.item.hood;

import net.mindoth.skillcloaks.item.HoodItem;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

public class RunecraftHoodItem extends HoodItem {
    public RunecraftHoodItem(IArmorMaterial pMaterial, EquipmentSlotType pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "skillcloaks:textures/models/armor/runecraft_layer_1.png";
    }
}
