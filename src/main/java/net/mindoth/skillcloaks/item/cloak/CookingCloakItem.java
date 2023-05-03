package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class CookingCloakItem extends CurioItem {
    //Most of the code is in skillcloaks/network/message/CloakAbilityPacket
    public static final UUID MINDOTH_UUID = UUID.fromString("3e2da4bc-fb81-4750-a5d5-dd34e3d28b0f");

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(Component.translatable("tooltip.skillcloaks.cooking_cloak"));

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

    private static CampfireCookingRecipe getCampfireCookingRecipe(Player player, Container inv)
    {
        return player.level.getRecipeManager().getRecipeFor(RecipeType.CAMPFIRE_COOKING, inv, player.level).orElse(null);
    }

    @SubscribeEvent
    public static void onPlayerUseCookingBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getEntity();
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.COOKING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) {

            ItemStack mainHandItemStack = player.getMainHandItem();
            ItemStack offHandItemStack = player.getOffhandItem();
            Container slotInv = new SimpleContainer(mainHandItemStack);
            Container slotInvOff = new SimpleContainer(offHandItemStack);
            CampfireCookingRecipe recipe = getCampfireCookingRecipe(player, slotInv);
            CampfireCookingRecipe recipeOff = getCampfireCookingRecipe(player, slotInvOff);

            if ((recipe != null || recipeOff != null)
                    && (mainHandItemStack.getItem() instanceof FlintAndSteelItem || offHandItemStack.getItem() instanceof FlintAndSteelItem)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerUseCooking(final PlayerInteractEvent.RightClickItem event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getEntity();
        Level world = player.level;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.COOKING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) {

            ItemStack mainHandItemStack = player.getMainHandItem();
            ItemStack offHandItemStack = player.getOffhandItem();
            Container slotInv = new SimpleContainer(mainHandItemStack);
            Container slotInvOff = new SimpleContainer(offHandItemStack);
            CampfireCookingRecipe recipe = getCampfireCookingRecipe(player, slotInv);
            CampfireCookingRecipe recipeOff = getCampfireCookingRecipe(player, slotInvOff);

            if ( (recipe != null || recipeOff != null)
                    && (mainHandItemStack.getItem() instanceof FlintAndSteelItem || offHandItemStack.getItem() instanceof FlintAndSteelItem)) {
                event.setCanceled(true);
                //Check if Flint and Steel in offhand
                if (offHandItemStack.getItem() instanceof FlintAndSteelItem) {
                    int size = mainHandItemStack.getCount();
                    for (int i = 0; i < size; ++i) {
                        //Check if Flint and Steel has durability
                        if (offHandItemStack.getItem() instanceof FlintAndSteelItem && offHandItemStack.getDamageValue() <= offHandItemStack.getMaxDamage()) {
                            //Get result
                            ItemStack result = recipe.assemble(slotInv, world.registryAccess());
                            if (!result.isEmpty()) {
                                mainHandItemStack.shrink(1);
                                ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), result);
                                drop.setDeltaMovement(0, 0, 0);
                                drop.setNoPickUpDelay();
                                player.level.addFreshEntity(drop);

                                offHandItemStack.hurtAndBreak(1, player, (holder) -> holder.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                            }
                        }
                    }

                    if (Objects.equals(player.getUUID(), MINDOTH_UUID)) {
                        //Particles
                        if (!world.isClientSide) {
                            ServerLevel level = (ServerLevel) world;
                            for (int i = 0; i < 8; ++i) {
                                level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0.1f);
                            }
                            for (int i = 0; i < 4; ++i) {
                                level.sendParticles(ParticleTypes.FLAME, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0.1f);
                            }
                        }

                        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1, 1);

                        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1, 1);
                    }
                    else {
                        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1, 1);
                    }
                }
                else if (mainHandItemStack.getItem() instanceof FlintAndSteelItem) {
                    int size = offHandItemStack.getCount();
                    for (int i = 0; i < size; ++i) {
                        //Check if Flint and Steel has durability
                        if (mainHandItemStack.getItem() instanceof FlintAndSteelItem && mainHandItemStack.getDamageValue() <= mainHandItemStack.getMaxDamage()) {
                            //Get result
                            ItemStack result = recipeOff.assemble(slotInv, world.registryAccess());
                            if (!result.isEmpty()) {
                                offHandItemStack.shrink(1);
                                ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), result);
                                drop.setDeltaMovement(0, 0, 0);
                                drop.setNoPickUpDelay();
                                player.level.addFreshEntity(drop);
                                mainHandItemStack.hurtAndBreak(1, player, (holder) -> holder.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                            }
                        }
                    }

                    if (Objects.equals(player.getUUID(), MINDOTH_UUID)) {
                        //Particles
                        if (!world.isClientSide) {
                            ServerLevel level = (ServerLevel) world;
                            for (int i = 0; i < 8; ++i) {
                                level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0.1f);
                            }
                            for (int i = 0; i < 4; ++i) {
                                level.sendParticles(ParticleTypes.FLAME, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0.1f);
                            }
                        }

                        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1, 1);

                        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1, 1);
                    }
                    else {
                        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1, 1);
                    }
                }
            }
        }
    }
}
