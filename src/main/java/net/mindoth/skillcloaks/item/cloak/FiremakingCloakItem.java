package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class FiremakingCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() ) {
            if ( SkillcloaksCommonConfig.FIREMAKING_TORCH.get() ) {
                tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.firemaking_cloak"));
            }
            if ( ModList.get().isLoaded("lucent") && SkillcloaksCommonConfig.LUCENT_COMPAT.get() ) {
                tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.firemaking_cloak_lucent"));
            }
        }

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
    public static void placeTorchWithStick(final PlayerInteractEvent.RightClickBlock event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if (!SkillcloaksCommonConfig.FIREMAKING_TORCH.get()) return;
        PlayerEntity player = (PlayerEntity)event.getEntity();
        World world = player.level;
        ItemStack itemStack = event.getItemStack();
        if ( !world.isClientSide ) {
            if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.FIREMAKING_CLOAK.get(), player).isPresent()
                    || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent() ) {
                if ( itemStack.getItem().is(Tags.Items.RODS_WOODEN) ) {
                    BlockRayTraceResult rtr = event.getHitVec();
                    BlockPos pos = rtr.getBlockPos();
                    Direction face = rtr.getDirection();
                    BlockState torchState = Blocks.TORCH.defaultBlockState();
                    BlockPos setBlockPos = getPosOfFace(pos, face);
                    boolean flag = false;
                    if ( face == Direction.UP && torchState.canSurvive(world, setBlockPos) ) {
                        if ( !player.abilities.instabuild ) {
                            double r = player.getRandom().nextDouble();
                            if ( r <= SkillcloaksCommonConfig.FIREMAKING_STICK_CHANCE.get() && SkillcloaksCommonConfig.FIREMAKING_STICK_CHANCE.get() > 0.0 ) {
                                itemStack.shrink(1);
                            }
                        }
                        world.setBlock(setBlockPos, torchState, 3);
                        flag = true;
                    }
                    else if ( face != Direction.DOWN ) {
                        torchState = Blocks.WALL_TORCH.defaultBlockState();
                        if ( torchState.setValue(HORIZONTAL_FACING, face).canSurvive(world, setBlockPos) ) {
                            if ( !player.abilities.instabuild ) {
                                double r = player.getRandom().nextDouble();
                                if ( r <= SkillcloaksCommonConfig.FIREMAKING_STICK_CHANCE.get() && SkillcloaksCommonConfig.FIREMAKING_STICK_CHANCE.get() > 0.0 ) {
                                    itemStack.shrink(1);
                                }
                            }
                            world.setBlock(setBlockPos, torchState.setValue(HORIZONTAL_FACING, face), 3);
                            flag = true;
                        }
                    }
                    if ( flag ) {
                        world.playSound(null,
                                setBlockPos.getX(),
                                setBlockPos.getY(),
                                setBlockPos.getZ(),
                                SoundEvents.WOOD_PLACE, SoundCategory.BLOCKS, 1, 0.8f);
                    }
                }
            }
        }
    }

    private static BlockPos getPosOfFace(BlockPos blockPos, Direction face) {
        switch (face) {
            case UP : return blockPos.above();
            case EAST : return blockPos.east();
            case WEST : return blockPos.west();
            case SOUTH : return blockPos.south();
            case NORTH : return blockPos.north();
            case DOWN : return blockPos.below();
        };
        return blockPos;
    }
}
