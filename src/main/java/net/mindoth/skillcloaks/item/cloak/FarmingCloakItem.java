package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class FarmingCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.farming_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslationTextComponent("curios.modifiers.cloak").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("+" + (SkillcloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(TextFormatting.BLUE)
                    .append(new TranslationTextComponent("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "cloak_armor").toString(), SkillcloaksCommonConfig.CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }



    @SubscribeEvent
    public static void onPlayerUseFarming(final PlayerInteractEvent.RightClickBlock event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        PlayerEntity player = event.getPlayer();
        World level = event.getWorld();
        BlockPos pos = event.getPos();
        if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.FARMING_CLOAK.get(), player).isPresent()
                || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent() ) {
            if ( event.getItemStack().getItem() instanceof BoneMealItem ) {
                if ( pos == player.getEntity().blockPosition() || level.getBlockState(pos).getBlock() instanceof IGrowable ) {
                    event.setCancellationResult(ActionResultType.SUCCESS);
                    event.setCanceled(true);
                }
                BlockPos.Mutable mutableblockpos = new BlockPos.Mutable();

                int xRange = SkillcloaksCommonConfig.FARMING_RANGE.get();
                int zRange = SkillcloaksCommonConfig.FARMING_RANGE.get();

                for (int xPos = pos.getX() - xRange; xPos <= pos.getX() + xRange; xPos++)
                    for (int zPos = pos.getZ() - zRange; zPos <= pos.getZ() + zRange; zPos++) {

                        BlockPos position = mutableblockpos.set(xPos, pos.getY(), zPos);

                        Block block = level.getBlockState(position).getBlock();

                        if ( block instanceof IGrowable ) {
                            BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), level, position, player);
                            if ( !level.isClientSide ) {
                                addGrowthParticles((ServerWorld) level, position, (ServerPlayerEntity) player);
                            }
                        }
                    }
                if ( !player.isCreative() ) event.getItemStack().shrink(1);
            }
        }
    }

    private static void addGrowthParticles(ServerWorld level, BlockPos pos, ServerPlayerEntity player) {
        Random random = new Random();
        int numParticles = 2;

        BlockState blockstate = level.getBlockState(pos);
        if (!blockstate.isAir(level, pos)) {
            double d0 = 0.5D;
            double d1;
            if (blockstate.is(Blocks.WATER)) {
                numParticles *= 3;
                d1 = 1.0D;
                d0 = 3.0D;
            }
            else if (blockstate.isSolidRender(level, pos)) {
                pos = pos.above();
                numParticles *= 3;
                d0 = 3.0D;
                d1 = 1.0D;
            }
            else {
                d1 = 1.0D;
            }
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, numParticles, 0.0D, 0.0D, 0.5, 0.5);

            for (int i = 0; i < numParticles; ++i) {
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextGaussian() * 0.02D;
                double d4 = random.nextGaussian() * 0.02D;
                double d5 = 0.5D - d0;
                double x = pos.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                double y = pos.getY() + random.nextDouble() * d1;
                double z = pos.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                if (!level.getBlockState((new BlockPos(x, y, z)).below()).isAir()) {
                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, numParticles, d2, d3, d4, 0.5);
                }
            }
        }
    }
}
