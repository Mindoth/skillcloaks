package net.mindoth.skillcloaks.client.model;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public class CloakModel extends BipedModel<LivingEntity> {
    private final ModelRenderer cloak;

    protected CloakModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType, 1, 0, 64, 64);
        setAllVisible(true);

        ModelRenderer rightPad = new ModelRenderer(this);
        ModelRenderer leftPad = new ModelRenderer(this);
        cloak = new ModelRenderer(this);
        cloak.setPos(0, 0, 1.99F);
        body.addChild(cloak);
        rightArm.addChild(rightPad);
        leftArm.addChild(leftPad);

        //RightPad
        rightPad.texOffs(16, 59);
        rightPad.addBox(-4.9003F, -3, -2.0F, 5.0F, 1.0F, 4.0F, 0.0F, false);
        rightPad.zRot += 0.3F;

        //LeftPad
        leftPad.texOffs(20, 41);
        leftPad.addBox(-0.0997F, -3, -2.0F, 5.0F, 1.0F, 4.0F, 0.0F, false);
        leftPad.zRot -= 0.3F;

        //Cloak
        cloak.texOffs(0, 41);
        cloak.addBox(-4.5F, 0, 0, 9.0F, 21.0F, 1.0F);
    }

    @Override
    public void prepareMobModel(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        if ( entity instanceof AbstractClientPlayerEntity ) {
            AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) entity;
            double x = MathHelper.lerp(partialTicks, player.xCloakO, player.xCloak) - MathHelper.lerp(partialTicks, player.xo, player.getX());
            double y = MathHelper.lerp(partialTicks, player.yCloakO, player.yCloak) - MathHelper.lerp(partialTicks, player.yo, player.getY());
            double z = MathHelper.lerp(partialTicks, player.zCloakO, player.zCloak) - MathHelper.lerp(partialTicks, player.zo, player.getZ());
            float f = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO);
            double d3 = MathHelper.sin(f * ((float) Math.PI / 180));
            double d4 = -MathHelper.cos(f * ((float) Math.PI / 180));
            float f1 = (float) y * 10;
            f1 = MathHelper.clamp(f1, -6, 32);
            float f2 = (float) (x * d3 + z * d4) * 100;
            f2 = MathHelper.clamp(f2, 0, 150);
            if (f2 < 0) {
                f2 = 0;
            }

            float f4 = MathHelper.lerp(partialTicks, player.oBob, player.bob);
            f1 = f1 + MathHelper.sin(MathHelper.lerp(partialTicks, player.walkDistO, player.walkDist) * 6) * 32 * f4;


            if ( player.getPose() == Pose.FALL_FLYING || player.getPose() == Pose.SWIMMING || player.isVisuallyCrawling() ) {
                cloak.xRot = Math.min((6 + f2 / 2 + f1) / 180 * (float) Math.PI, 0.2f);
            }
            else {
                cloak.xRot = (6 + f2 / 2 + f1) / 180 * (float) Math.PI;
            }
        }
    }

    public static CloakModel cloak(Function<ResourceLocation, RenderType> renderType) {
        return new CloakModel(renderType);
    }
}