package net.mindoth.skillcloaks.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.registries.ModItems;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CloakRenderer implements ICurioRenderer {

    private final ResourceLocation texture;
    private final HumanoidModel<LivingEntity> model;

    public CloakRenderer(String texturePath, HumanoidModel<LivingEntity> model) {
        this(ResourceLocation.fromNamespaceAndPath(Skillcloaks.MOD_ID, String.format("textures/entity/curio/%s.png", texturePath)), model);
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

    @SuppressWarnings("deprecation")
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack matrixStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource renderTypeBuffer,
            int light, float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        HumanoidModel<LivingEntity> model = getModel();

        if ( !ModCommonConfig.COSMETIC_ONLY.get() && slotContext.entity().isInvisible()
                && (CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(), ModItems.THIEVING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(), ModItems.MAX_CLOAK.get()).isPresent()) ) return;

        if ( !(slotContext.entity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem) ) {
            model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
            ICurioRenderer.followBodyRotations(slotContext.entity(), model);
            if ( !EnchantmentHelper.hasAnyEnchantments(slotContext.entity().getItemBySlot(EquipmentSlot.CHEST)) ) {
                render(matrixStack, renderTypeBuffer, light, stack.hasFoil());
            }
            else render(matrixStack, renderTypeBuffer, light, true);
        }
    }

    protected void render(PoseStack matrixStack, MultiBufferSource buffer, int light, boolean hasFoil) {
        RenderType renderType = model.renderType(getTexture());
        VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, -1);
    }
}
