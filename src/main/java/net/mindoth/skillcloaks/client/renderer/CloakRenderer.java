package net.mindoth.skillcloaks.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.caelus.api.CaelusApi;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CloakRenderer implements ICurioRenderer {

    private final ResourceLocation texture;
    private final HumanoidModel<LivingEntity> model;

    public CloakRenderer(String texturePath, HumanoidModel<LivingEntity> model) {
        this(new ResourceLocation(Skillcloaks.MOD_ID, String.format("textures/entity/curio/%s.png", texturePath)), model);
    }

    public CloakRenderer(ResourceLocation texture, HumanoidModel<LivingEntity> model) {
        this.texture = texture;
        this.model = model;
    }

    protected ResourceLocation getTexture() {
        return texture;
    }

    protected HumanoidModel<LivingEntity> getModel() {
        return model;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource multiBufferSource,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        HumanoidModel<LivingEntity> model = getModel();

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && slotContext.entity().isInvisible() && (CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(), SkillcloaksItems.THIEVING_CLOAK.get()).isPresent() || CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(), SkillcloaksItems.MAX_CLOAK.get()).isPresent()) ) return;


        if ( ModList.get().isLoaded("caelus") ) {
            if (!CaelusApi.getInstance().canFly(slotContext.entity())) {
                model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
                ICurioRenderer.followBodyRotations(slotContext.entity(), model);
                if (EnchantmentHelper.getEnchantments(slotContext.entity().getItemBySlot(EquipmentSlot.CHEST)).isEmpty()) {
                    render(poseStack, multiBufferSource, light, stack.hasFoil());
                } else render(poseStack, multiBufferSource, light, true);
            }
        }
        else {
            if (!(slotContext.entity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)) {
                model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
                ICurioRenderer.followBodyRotations(slotContext.entity(), model);
                if (EnchantmentHelper.getEnchantments(slotContext.entity().getItemBySlot(EquipmentSlot.CHEST)).isEmpty()) {
                    render(poseStack, multiBufferSource, light, stack.hasFoil());
                } else render(poseStack, multiBufferSource, light, true);
            }
        }
/*
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.0D, 0.125D);
        double x = Mth.lerp(partialTicks, player.xCloakO, player.xCloak) - Mth.lerp(partialTicks, player.xo, player.getX());
        double y = Mth.lerp(partialTicks, player.yCloakO, player.yCloak) - Mth.lerp(partialTicks, player.yo, player.getY());
        double z = Mth.lerp(partialTicks, player.zCloakO, player.zCloak) - Mth.lerp(partialTicks, player.zo, player.getZ());
        float f = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO);
        double d3 = Mth.sin(f * ((float) Math.PI / 180));
        double d4 = -Mth.cos(f * ((float) Math.PI / 180));
        float f1 = (float)y * 10.0F;
        f1 = Mth.clamp(f1, -6.0F, 32.0F);
        float f2 = (float)(x * d3 + z * d4) * 100.0F;
        f2 = Mth.clamp(f2, 0.0F, 150.0F);
        float f3 = (float)(x * d4 - z * d3) * 100.0F;
        f3 = Mth.clamp(f3, -20.0F, 20.0F);
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        float f4 = Mth.lerp(partialTicks, player.oBob, player.bob);
        f1 = f1 + Mth.sin(Mth.lerp(partialTicks, player.walkDistO, player.walkDist) * 6) * 32 * f4;
        if ( player.isCrouching() ) {
            poseStack.translate(0F,0.125F, 0F);
            f1 += 25.0F;
        }
        poseStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(f3 / 2.0F));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-(f3 / 2.0F)));
        poseStack.scale(2F,2F, 2F);
        RenderType renderType = model.renderType(getTexture());
        VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(multiBufferSource, renderType, false, false);
        model.renderToBuffer(poseStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
 */
    }

    protected void render(PoseStack matrixStack, MultiBufferSource buffer, int light, boolean hasFoil) {
        RenderType renderType = model.renderType(getTexture());
        VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
