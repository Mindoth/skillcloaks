package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class DefenceCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.defence_cloak"));

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

    private static final String TAG_DEFENCE_COOLDOWN = ("preventDeath");

    //Prevent death
    @SubscribeEvent
    public static void onDamageEvent(final LivingDamageEvent event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        if ( !event.getEntityLiving().level.isClientSide ) {
            LivingEntity player = event.getEntityLiving();
            CompoundNBT playerData = player.getPersistentData();
            CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);

            //Check if the damage is lethal and that the player has the correct cloak equipped
            if ( ( event.getAmount() >= event.getEntityLiving().getHealth() )   &&   ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.DEFENCE_CLOAK.get(), player).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent() ) ) {

                //Check for cooldown
                if ( data.getInt(TAG_DEFENCE_COOLDOWN) <= 0 ) {
                    event.setAmount(0);
                    player.setHealth(1.0F);
                    player.removeAllEffects();
                    player.addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
                    player.addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
                    player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
                    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundCategory.PLAYERS, 1, 1);

                    //Add cooldown of 24000
                    data.putInt(TAG_DEFENCE_COOLDOWN, 24000);
                    playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);
                }
            }
        }
    }

    //Defence cloak timer
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
        if ( !player.level.isClientSide ) {

            if ( player.tickCount % 2 == 0 ) {
                //Check for cooldown
                if (data.getInt(TAG_DEFENCE_COOLDOWN) > 0) {

                    //Lower cooldown by 1 each tick
                    data.putInt(TAG_DEFENCE_COOLDOWN, data.getInt(TAG_DEFENCE_COOLDOWN) - 1);
                    playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);
                }
            }

            //Inform the player that the cloak has recharged when 1 is left in cooldown so chat doesn't get spammed
            if ( data.getInt(TAG_DEFENCE_COOLDOWN) == 1 ) {
                player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.defence.recharged"), true);
                player.playNotifySound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundCategory.PLAYERS, 1, 1);
            }

            //Remove the cooldown tag
            if ( data.getInt(TAG_DEFENCE_COOLDOWN) <= 0 ) {
                data.remove(TAG_DEFENCE_COOLDOWN);
            }
        }
    }

    //Warn the player when they equip the cloak while on cooldown
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        if ( livingEntity instanceof PlayerEntity ) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            CompoundNBT playerData = player.getPersistentData();
            CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            if ( !player.level.isClientSide && ( data.getInt(TAG_DEFENCE_COOLDOWN) > 0 ) ) {
                int totalSecs = data.getInt(TAG_DEFENCE_COOLDOWN) / 20;
                int mins = (totalSecs % 3600) / 60;
                int secs = totalSecs % 60;
                if ( mins > 0 ) {
                    player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.defence.cooldown")
                            .append(new TranslationTextComponent(mins + "m " + secs + "s")), true);
                }
                else player.displayClientMessage(new TranslationTextComponent("message.skillcloaks.defence.cooldown")
                        .append(new TranslationTextComponent( secs + "s")), true);
                player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE, SoundCategory.PLAYERS, 1, 0.5f);
            }
        }
    }
}
