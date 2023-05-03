package net.mindoth.skillcloaks.item.cloak;

import com.google.common.collect.Multimap;
import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class HerbloreCloakItem extends CurioItem {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        if (!SkillcloaksCommonConfig.COSMETIC_ONLY.get()) tooltip.add(new TranslatableComponent("tooltip.skillcloaks.herblore_cloak"));

        if ( !SkillcloaksCommonConfig.COSMETIC_ONLY.get() && SkillcloaksCommonConfig.CLOAK_ARMOR.get() > 0 ) {
            tooltip.add(new TranslatableComponent("curios.modifiers.cloak").withStyle(ChatFormatting.GRAY));
            tooltip.add(new TextComponent("+" + (SkillcloaksCommonConfig.CLOAK_ARMOR.get()).toString() + " ").withStyle(ChatFormatting.BLUE)
                    .append(new TranslatableComponent("tooltip.skillcloaks.armor_value")));
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
    public static void onPlayerUse(final PlayerInteractEvent.RightClickItem event) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Player player = event.getPlayer();
        if ( CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.HERBLORE_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(player, SkillcloaksItems.MAX_CLOAK.get()).isPresent() ) {

            ItemStack potionStack = BrewingRecipeRegistry.getOutput(event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND), event.getPlayer().getItemBySlot(EquipmentSlot.OFFHAND));
            ItemEntity drop = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), potionStack);

            ItemStack potionStack2 = BrewingRecipeRegistry.getOutput(event.getPlayer().getItemBySlot(EquipmentSlot.OFFHAND), event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND));
            ItemEntity drop2 = new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), potionStack2);

            if ( potionStack.getItem() instanceof PotionItem || potionStack2.getItem() instanceof PotionItem) {
                event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).shrink(1);
                event.getPlayer().getItemBySlot(EquipmentSlot.OFFHAND).shrink(1);
                drop.setDeltaMovement(0, 0, 0);
                drop.setNoPickUpDelay();
                player.level.addFreshEntity(drop);
                drop2.setDeltaMovement(0, 0, 0);
                drop2.setNoPickUpDelay();
                player.level.addFreshEntity(drop2);

                //Sound
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS, 1, 1);
            }
        }
    }
}