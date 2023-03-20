package net.mindoth.skillcloaks.client.model;


import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public class CloakModel extends HumanoidModel<LivingEntity> {
    private final ModelPart cloak = body.getChild("cloak");

    public CloakModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
        super(part, renderType);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body, leftArm, rightArm);
    }

    public static MeshDefinition createCloak() {
        MeshDefinition mesh = createMesh(new CubeDeformation(1), 0);

        mesh.getRoot().getChild("body").addOrReplaceChild("cloak", CubeListBuilder.create()
                .texOffs(0, 41)
                .addBox(-4.5F, 0, 0, 9.0F, 21.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.99F
        ));

        mesh.getRoot().getChild("right_arm").addOrReplaceChild("leftPad", CubeListBuilder.create()
                .texOffs(16, 59)
                .addBox(-4.9003F, -3, -2.0F, 5.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3F
        ));

        mesh.getRoot().getChild("left_arm").addOrReplaceChild("rightPad", CubeListBuilder.create()
                .texOffs(20, 41)
                .addBox(-0.0997F, -3, -2.0F, 5.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3F
        ));
        return mesh;
    }

    @Override
    public void prepareMobModel(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        if ( entity instanceof AbstractClientPlayer player ) {
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

            if ( player.getPose() == Pose.FALL_FLYING || player.getPose() == Pose.SWIMMING || player.isVisuallyCrawling() ) {
                cloak.xRot = Math.min((6 + f2 / 2 + f1) / 180 * (float) Math.PI, 0.2f);
            }
            else {
                cloak.xRot = (6 + f2 / 2 + f1) / 180 * (float) Math.PI;
            }
            //cloak.yRot = (f3 / 2.0F) / 180 * (float) Math.PI;
            //cloak.zRot = (f3 / 2.0F) / 180 * (float) Math.PI;
        }
    }
}