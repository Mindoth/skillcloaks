package net.mindoth.skillcloaks.item.armor;

import net.mindoth.skillcloaks.Skillcloaks;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class HoodItem extends ModArmorItem {

    private final String path;

    public HoodItem(Holder<ArmorMaterial> material, Type type, Properties properties, String path) {
        super(material, type, properties);
        this.path = path;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        ResourceLocation result = ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, "textures/models/armor/" + this.path + ".png");
        return path != null ? result : layer.texture(innerModel);
    }
}
