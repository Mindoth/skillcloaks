package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class FiremakingCloakItem extends CurioItem {

    public FiremakingCloakItem(String name) {
        super(name);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void placeTorchWithStick(final PlayerInteractEvent.RightClickBlock event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( !ModCommonConfig.FIREMAKING_TORCH.get() ) return;
        Player player = event.getEntity();
        Level world = player.level();
        ItemStack itemStack = event.getItemStack();
        if ( !world.isClientSide ) {
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.FIREMAKING_CLOAK.get()).isPresent()
                    || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {
                if ( itemStack.is(Tags.Items.RODS_WOODEN) ) {
                    BlockHitResult rtr = event.getHitVec();
                    BlockPos pos = rtr.getBlockPos();
                    Direction face = rtr.getDirection();
                    BlockState torchState = Blocks.TORCH.defaultBlockState();
                    BlockPos setBlockPos = getPosOfFace(pos, face);
                    boolean flag = false;
                    if ( face == Direction.UP ) {
                        if ( world.getBlockState(pos).canBeReplaced() ) {
                            setBlockPos = pos;
                        }
                        if ( torchState.canSurvive(world, setBlockPos) ) {
                            if ( world.getBlockState(setBlockPos).canBeReplaced() ) {
                                if ( !player.getAbilities().instabuild ) {
                                    double r = player.getRandom().nextDouble();
                                    if ( r <= ModCommonConfig.FIREMAKING_STICK_CHANCE.get() && ModCommonConfig.FIREMAKING_STICK_CHANCE.get() > 0.0 ) {
                                        itemStack.shrink(1);
                                    }
                                }
                                world.setBlock(setBlockPos, torchState, 3);
                                flag = true;
                            }
                        }
                    }
                    else if ( face != Direction.DOWN ) {
                        if ( world.getBlockState(pos).canBeReplaced() ) {
                            setBlockPos = pos;
                        }
                        torchState = Blocks.WALL_TORCH.defaultBlockState();
                        if ( torchState.setValue(HORIZONTAL_FACING, face).canSurvive(world, setBlockPos) ) {
                            if ( world.getBlockState(setBlockPos).canBeReplaced() ) {
                                if ( !player.getAbilities().instabuild ) {
                                    double r = player.getRandom().nextDouble();
                                    if ( r <= ModCommonConfig.FIREMAKING_STICK_CHANCE.get() && ModCommonConfig.FIREMAKING_STICK_CHANCE.get() > 0.0 ) {
                                        itemStack.shrink(1);
                                    }
                                }
                                world.setBlock(setBlockPos, torchState.setValue(HORIZONTAL_FACING, face), 3);
                                flag = true;
                            }
                        }
                        else {
                            torchState = Blocks.TORCH.defaultBlockState();
                            if ( torchState.canSurvive(world, setBlockPos) ) {
                                if ( world.getBlockState(setBlockPos).canBeReplaced() ) {
                                    if ( !player.getAbilities().instabuild ) {
                                        double r = player.getRandom().nextDouble();
                                        if ( r <= ModCommonConfig.FIREMAKING_STICK_CHANCE.get() && ModCommonConfig.FIREMAKING_STICK_CHANCE.get() > 0.0 ) {
                                            itemStack.shrink(1);
                                        }
                                    }
                                    world.setBlock(setBlockPos, torchState, 3);
                                    flag = true;
                                }
                            }
                        }
                    }
                    if ( flag ) {
                        world.playSound(null,
                                setBlockPos.getX(),
                                setBlockPos.getY(),
                                setBlockPos.getZ(),
                                SoundEvents.WOOD_PLACE, SoundSource.PLAYERS, 1, 0.8f);
                    }
                }
            }
        }
    }

    private static BlockPos getPosOfFace(BlockPos blockPos, Direction face) {
        return switch (face) {
            case UP -> blockPos.above();
            case EAST -> blockPos.east();
            case WEST -> blockPos.west();
            case SOUTH -> blockPos.south();
            case NORTH -> blockPos.north();
            case DOWN -> blockPos.below();
        };
    }
}
