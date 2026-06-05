package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Random;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class FarmingCloakItem extends CurioItem {

    public FarmingCloakItem(String name) {
        super(name);
    }

    @SubscribeEvent
    public static void doBonemealEvent(final PlayerInteractEvent.RightClickBlock event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.FARMING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {
            if ( event.getItemStack().getItem() instanceof BoneMealItem) {
                if ( pos == player.getOnPos() || level.getBlockState(pos).getBlock() instanceof BonemealableBlock) {
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
                BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();

                int xRange = ModCommonConfig.FARMING_RANGE.get();
                int zRange = ModCommonConfig.FARMING_RANGE.get();

                for (int xPos = pos.getX() - xRange; xPos <= pos.getX() + xRange; xPos++)
                    for (int zPos = pos.getZ() - zRange; zPos <= pos.getZ() + zRange; zPos++) {

                        BlockPos position = mutableblockpos.set(xPos, pos.getY(), zPos);

                        Block block = level.getBlockState(position).getBlock();

                        if ( block instanceof BonemealableBlock ) {
                            BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), level, position, player);
                            if ( !level.isClientSide ) {
                                addGrowthParticles((ServerLevel) level, position);
                            }
                            level.playSound(null, position,
                                    SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
                        }
                    }
                if ( !player.isCreative() ) event.getItemStack().shrink(1);
            }
        }
    }

    private static void addGrowthParticles(ServerLevel level, BlockPos pos) {
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
