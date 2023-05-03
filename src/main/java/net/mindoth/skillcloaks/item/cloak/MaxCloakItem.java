package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class MaxCloakItem extends CurioItem {

    private static final String TAG_DEFENCE_COOLDOWN = ("preventDeath");

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslatableComponent("tooltip.skillcloaks.max_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslatableComponent("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(new TextComponent("+" + (SkillcloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(new TranslatableComponent("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    //Gives the curio one level of fortune by default
    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return 0;
        else return 1;
    }

    @Override
    public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return ICurio.DropRule.DEFAULT;
        else return ICurio.DropRule.ALWAYS_KEEP;
    }

    //Warn the player when they equip the cloak while on cooldown
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        if ( livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            CompoundTag playerData = player.getPersistentData();
            CompoundTag data = playerData.getCompound(Player.PERSISTED_NBT_TAG);
            if ( !player.level.isClientSide && ( data.getInt(TAG_DEFENCE_COOLDOWN) > 0 ) ) {
                int totalSecs = data.getInt(TAG_DEFENCE_COOLDOWN) / 20;
                int mins = (totalSecs % 3600) / 60;
                int secs = totalSecs % 60;
                if ( mins > 0 ) {
                    player.displayClientMessage(new TranslatableComponent("message.skillcloaks.defence.cooldown")
                            .append(new TextComponent(mins + "m " + secs + "s")), true);
                }
                else player.displayClientMessage(new TranslatableComponent("message.skillcloaks.defence.cooldown")
                        .append(new TextComponent( secs + "s")), true);
                player.playNotifySound(SoundEvents.NOTE_BLOCK_SNARE, SoundSource.PLAYERS, 1, 0.5f);
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) result.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "max_cloak_knockback_resistance").toString(), SkillcloaksCommonConfig.STRENGTH_KNOCKBACK_RESISTANCE.get(), AttributeModifier.Operation.ADDITION));
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) result.put(Attributes.LUCK, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "fishing_cloak_luck").toString(), SkillcloaksCommonConfig.FISHING_LUCK.get(), AttributeModifier.Operation.ADDITION));

        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(Skillcloaks.MOD_ID, "cloak_armor").toString(), SkillcloaksCommonConfig.CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }
}
