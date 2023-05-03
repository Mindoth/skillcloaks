package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
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
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(Component.translatable("tooltip.skillcloaks.farming_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(Component.translatable("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("+" + (SkillcloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(Component.translatable("tooltip.skillcloaks.armor_value")));
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
    public static void doBonemealEvent(final PlayerInteractEvent.RightClickBlock event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.FARMING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) {
            if ( event.getItemStack().getItem().equals(Items.BONE_MEAL) ) {
                if ( pos == player.getOnPos() || level.getBlockState(pos).getBlock() instanceof BonemealableBlock ) {
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
                BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();

                int xRange = SkillcloaksCommonConfig.FARMING_RANGE.get();;
                int zRange = SkillcloaksCommonConfig.FARMING_RANGE.get();;

                for (int xPos = pos.getX() - xRange; xPos <= pos.getX() + xRange; xPos++)
                    for (int zPos = pos.getZ() - zRange; zPos <= pos.getZ() + zRange; zPos++) {

                            BlockPos position = mutableblockpos.set(xPos, pos.getY(), zPos);

                            Block block = level.getBlockState(position).getBlock();

                            if ( block instanceof BonemealableBlock ) {
                                BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), level, position, player);
                                if ( !level.isClientSide ) {
                                    addGrowthParticles((ServerLevel) level, position, (ServerPlayer) player);
                                }
                                level.playSound(null, position,
                                        SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
                            }
                    }
                if ( !player.isCreative() ) event.getItemStack().shrink(1);
            }
        }
    }

    private static void addGrowthParticles(ServerLevel level, BlockPos pos, ServerPlayer player) {
        Random random = new Random();
        int numParticles = 2;

        BlockState blockstate = level.getBlockState(pos);
        if (!blockstate.isAir()) {
            double d0 = 0.5D;
            double d1;
            if (blockstate.equals(Blocks.WATER)) {
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
                if (!level.getBlockState((new BlockPos((int)x, (int)y, (int)z)).below()).isAir()) {
                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, numParticles, d2, d3, d4, 0.5);
                }
            }
        }
    }
}
