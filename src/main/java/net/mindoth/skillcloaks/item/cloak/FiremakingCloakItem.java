package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SkillCloaks.MOD_ID)
public class FiremakingCloakItem extends CurioItem {
    //Most of the code is in skillcloaks/network/message/CloakAbilityPacket
    public static final UUID JAPE_UUID = UUID.fromString("ae250004-ff71-4a34-8894-57781e64f597");

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get()) {
            if (ModList.get().isLoaded("lucent")) {
                tooltip.add(Component.translatable("tooltip.skillcloaks.firemaking_cloak_lucent"));
            }
            else tooltip.add(Component.translatable("tooltip.skillcloaks.firemaking_cloak"));
        }

        if ( !SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(Component.translatable("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("+" + (SkillCloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(Component.translatable("tooltip.skillcloaks.armor_value")));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);

        if (!SkillCloaksCommonConfig.COSMETIC_ONLY.get() && SkillCloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            result.put(Attributes.ARMOR, new AttributeModifier(uuid, new ResourceLocation(SkillCloaks.MOD_ID, "cloak_armor").toString(), SkillCloaksCommonConfig.CLOAK_ARMOR.get(), AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @SubscribeEvent
    public static void onPlayerWalk(final TickEvent.PlayerTickEvent event) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.player;
        Level world = player.level;
        if ( !world.isClientSide ) {
            if (player.tickCount % 10 == 0) {
                if (CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.FIREMAKING_CLOAK.get()).isPresent()
                        || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.MAX_CLOAK.get()).isPresent()) {
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
