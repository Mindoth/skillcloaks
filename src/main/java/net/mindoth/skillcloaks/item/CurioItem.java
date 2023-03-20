package net.mindoth.skillcloaks.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mindoth.skillcloaks.client.CurioRenderers;
import net.mindoth.skillcloaks.item.SkillCloaksCurio;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;

public class CurioItem extends SkillCloaksCurio implements ICurioItem {

    @Override
    public boolean showAttributesTooltip(String identifier, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent();
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return CurioRenderers.getRenderer(this) != null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ticks, float headYaw, float headPitch, ItemStack stack) {
        CurioRenderers.getRenderer(this).render(identifier, index, matrixStack, buffer, light, entity, limbSwing, limbSwingAmount, partialTicks, ticks, headYaw, headPitch, stack);
    }

    public void jump(PlayerEntity player) {
    }
}
