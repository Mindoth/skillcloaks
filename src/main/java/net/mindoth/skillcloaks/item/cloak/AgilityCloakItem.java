package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.network.message.DoubleJumpPacket;
import net.mindoth.skillcloaks.network.message.SkillcloaksNetwork;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AgilityCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslationTextComponent("tooltip.skillcloaks.agility_cloak"));

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

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent();
    }

    protected <T extends Event, S extends LivingEntity> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        MinecraftForge.EVENT_BUS.addListener(priority, true, eventClass, event -> {
            S wearer = wearerSupplier.apply(event);
            if (isEquippedBy(wearer)) {
                listener.accept(event, wearer);
            }
        });
    }

    protected <T extends Event, S extends LivingEntity> void addListener(Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        addListener(EventPriority.NORMAL, eventClass, listener, wearerSupplier);
    }

    protected <T extends LivingEvent> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(priority, eventClass, listener, LivingEvent::getEntityLiving);
    }

    protected <T extends LivingEvent> void addListener(Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(EventPriority.NORMAL, eventClass, listener);
    }



    //AGILITY CAPE
    public AgilityCloakItem() {
        MinecraftForge.EVENT_BUS.register(new DoubleJumpHandler());
        addListener(EventPriority.HIGHEST, LivingFallEvent.class, this::onLivingFall);
    }

    @Override
    public void jump(PlayerEntity player) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        player.fallDistance = 0;

        double upwardsMotion = 0.5;

        Vector3d motion = player.getDeltaMovement();
        double motionMultiplier = 0;
        float direction = (float) (player.yRot * Math.PI / 180);
        player.setDeltaMovement(player.getDeltaMovement().add(
                -MathHelper.sin(direction) * motionMultiplier,
                upwardsMotion - motion.y,
                MathHelper.cos(direction) * motionMultiplier)
        );

        player.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(player);

        player.awardStat(Stats.JUMP);

        player.causeFoodExhaustion(0.05F);

        player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1, 1);
    }

    private void onLivingFall(LivingFallEvent event, LivingEntity wearer) {
        event.setDistance(Math.max(0, event.getDistance() - 3));
    }

    private class DoubleJumpHandler {
        @OnlyIn(Dist.CLIENT)
        private boolean canDoubleJump;

        @OnlyIn(Dist.CLIENT)
        private boolean hasReleasedJumpKey;

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        @SuppressWarnings("unused")
        public void onClientTick(TickEvent.ClientTickEvent event) {
            if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
            ClientPlayerEntity player = Minecraft.getInstance().player;

            // noinspection ConstantConditions
            if ( event.phase == TickEvent.Phase.END && player != null && player.input != null ) {
                if ( (player.isOnGround() || player.onClimbable()) && !player.isInWater() ) {
                    hasReleasedJumpKey = false;
                    canDoubleJump = true;
                }
                else if ( !player.input.jumping ) {
                    hasReleasedJumpKey = true;
                }
                else if ( !player.abilities.flying && canDoubleJump && hasReleasedJumpKey ) {
                    canDoubleJump = false;
                    if ( CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.AGILITY_CLOAK.get(), player).isPresent() || CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent() ) {
                        SkillcloaksNetwork.CHANNEL.sendToServer(new DoubleJumpPacket());
                        jump(player);
                    }
                }
            }
        }
    }
}