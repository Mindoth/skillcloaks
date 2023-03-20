package net.mindoth.skillcloaks.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.caelus.api.CaelusApi;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CloakRenderer implements CurioRenderer {

    private final ResourceLocation texture;
    private final BipedModel<LivingEntity> model;

    public CloakRenderer(String texturePath, BipedModel<LivingEntity> model) {
        this(new ResourceLocation(SkillCloaks.MOD_ID, String.format("textures/entity/curio/%s.png", texturePath)), model);
    }

    public CloakRenderer(ResourceLocation texture, BipedModel<LivingEntity> model) {
        this.texture = texture;
        this.model = model;
    }

    protected ResourceLocation getTexture() {
        return texture;
    }

    protected BipedModel<LivingEntity> getModel() {
        return model;
    }

    @Override
    public final void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ticks, float headYaw, float headPitch, ItemStack stack) {
        BipedModel<LivingEntity> model = getModel();

        if ( !SkillCloaksCommonConfig.COSMETIC_ONLY.get() && entity.isInvisible() && (CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.THIEVING_CLOAK.get(), entity).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillCloaksItems.MAX_CLOAK.get(), entity).isPresent()) ) return;

        if ( ModList.get().isLoaded("caelus") ) {
            if (!CaelusApi.canElytraFly(entity)) {
                model.setupAnim(entity, limbSwing, limbSwingAmount, ticks, headYaw, headPitch);
                model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                ICurio.RenderHelper.followBodyRotations(entity, model);
                if (EnchantmentHelper.getEnchantments(entity.getItemBySlot(EquipmentSlotType.CHEST)).isEmpty()) {
                    render(matrixStack, buffer, light, stack.hasFoil());
                }
                else render(matrixStack, buffer, light, true);
            }
        }
        else {
            if (!(entity.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof ElytraItem)) {
                model.setupAnim(entity, limbSwing, limbSwingAmount, ticks, headYaw, headPitch);
                model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                ICurio.RenderHelper.followBodyRotations(entity, model);
                if (EnchantmentHelper.getEnchantments(entity.getItemBySlot(EquipmentSlotType.CHEST)).isEmpty()) {
                    render(matrixStack, buffer, light, stack.hasFoil());
                }
                else render(matrixStack, buffer, light, true);
            }
        }
    }

    protected void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, boolean hasFoil) {
        RenderType renderType = model.renderType(getTexture());
        IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}