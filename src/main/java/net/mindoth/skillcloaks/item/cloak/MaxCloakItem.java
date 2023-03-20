package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MaxCloakItem extends CurioItem {

    private static final String TAG_DEFENCE_COOLDOWN = ("preventDeath");

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.max_cloak"));

        if ( !SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslationTextComponent("curios.modifiers.cloak").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent("+" + (SkillCloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(TextFormatting.BLUE)
                    .append(new TranslationTextComponent("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    //Gives the curio one level of fortune by default
    @Override
    public int getFortuneBonus(String identifier, LivingEntity livingEntity, ItemStack curio, int index) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return 0;
        else return 1;
    }

    @Override
    public ICurio.DropRule getDropRule(LivingEntity livingEntity, ItemStack stack) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return ICurio.DropRule.DEFAULT;
        else return ICurio.DropRule.ALWAYS_KEEP;
    }

    //Warn the player when they equip the cloak while on cooldown
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            CompoundNBT playerData = player.getPersistentData();
            CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            if (!player.level.isClientSide && (data.getInt(TAG_DEFENCE_COOLDOWN) > 0)) {
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

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) result.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, new ResourceLocation(SkillCloaks.MOD_ID, "max_cloak_knockback_resistance").toString(), SkillCloaksCommonConfig.STRENGTH_KNOCKBACK_RESISTANCE.get(), AttributeModifier.Operation.ADDITION));
        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) result.put(Attributes.LUCK, new AttributeModifier(uuid, new ResourceLocation(SkillCloaks.MOD_ID, "fishing_cloak_luck").toString(), SkillCloaksCommonConfig.FISHING_LUCK.get(), AttributeModifier.Operation.ADDITION));

        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(SkillCloaks.MOD_ID, "cloak_armor").toString(), SkillCloaksCommonConfig.CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }
}