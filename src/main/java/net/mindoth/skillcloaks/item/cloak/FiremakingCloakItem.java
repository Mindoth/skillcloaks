package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class FiremakingCloakItem extends CurioItem {
    public static final UUID JAPE_UUID = UUID.fromString("ae250004-ff71-4a34-8894-57781e64f597");

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) {
            if (ModList.get().isLoaded("lucent") && SkillcloaksCommonConfig.LUCENT_COMPAT.get()) {
                tooltip.add(Component.translatable("tooltip.skillcloaks.firemaking_cloak_lucent"));
            }
            else tooltip.add(Component.translatable("tooltip.skillcloaks.firemaking_cloak"));
        }

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(Component.translatable("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("+" + (SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(Component.translatable("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "cloak_armor").toString(), SkillcloaksCommonConfig.SKILL_CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @SubscribeEvent
    public static void placeTorchWithStick(final PlayerInteractEvent.RightClickBlock event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getEntity();
        Level world = player.level;
        ItemStack itemStack = event.getItemStack();
        if ( !world.isClientSide ) {
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.FIREMAKING_CLOAK.get()).isPresent()
                    || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) {
                if ( itemStack.is(Tags.Items.RODS_WOODEN) ) {
                    BlockHitResult rtr = event.getHitVec();
                    BlockPos pos = rtr.getBlockPos();
                    Direction face = rtr.getDirection();
                    BlockState torchState = Blocks.TORCH.defaultBlockState();
                    BlockPos setBlockPos = getPosOfFace(pos, face);
                    if ( face == Direction.UP && torchState.canSurvive(world, setBlockPos) ) {
                        world.playSound(null, setBlockPos.getX(), setBlockPos.getY(), setBlockPos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1, 0.8f);
                        if ( !player.getAbilities().instabuild ) {
                            if ( (new Random().nextInt(100) + 1) <= SkillcloaksCommonConfig.FIREMAKING_STICK_CHANCE.get() ) {
                                itemStack.shrink(1);
                            }
                        }
                        world.setBlock(setBlockPos, torchState, 3);
                    }
                    else if ( face != Direction.DOWN ) {
                        torchState = Blocks.WALL_TORCH.defaultBlockState();
                        if ( torchState.setValue(HORIZONTAL_FACING, face).canSurvive(world, setBlockPos) ) {
                            world.playSound(null, setBlockPos.getX(), setBlockPos.getY(), setBlockPos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1, 0.8f);
                            if ( !player.getAbilities().instabuild ) {
                                if ( (new Random().nextInt(100) + 1) <= SkillcloaksCommonConfig.FIREMAKING_STICK_CHANCE.get() ) {
                                    itemStack.shrink(1);
                                }
                            }
                            world.setBlock(setBlockPos, torchState.setValue(HORIZONTAL_FACING, face), 3);
                        }
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

    @SubscribeEvent
    public static void onPlayerWalk(final TickEvent.PlayerTickEvent event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.player;
        Level world = player.level;
        if ( !world.isClientSide ) {
            if (player.tickCount % 10 == 0) {
                if (CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.FIREMAKING_CLOAK.get()).isPresent()
                        || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent()) {
                    if (Objects.equals(player.getUUID(), JAPE_UUID)) {
                        if (player.getLightLevelDependentMagicValue() < 0.28f) {
                            //Particles
                            ServerLevel level = (ServerLevel) world;
                            for (int i = 0; i < 8; ++i) {
                                level.addParticle(ParticleTypes.FLAME, player.getX() - 0.5f + player.getRandom().nextFloat(), player.getY() + 0.1f,
                                        player.getZ() - 0.5f + player.getRandom().nextFloat(), 0, 0, 0);
                            }
                        }
                    }
                }
            }
        }
    }
}
