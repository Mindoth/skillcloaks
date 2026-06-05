package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Collection;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class HunterCloakItem extends CurioItem {
    
    public HunterCloakItem(String name) {
        super(name);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHunterCloakDrops(final LivingDropsEvent event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity player = (LivingEntity)event.getSource().getEntity();
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.HUNTER_CLOAK.get()).isPresent() 
                    || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {
                Collection<ItemEntity> drops = event.getDrops();
                for (ItemEntity drop : drops) {
                    drop.moveTo(player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z);
                    drop.setDeltaMovement(0, 0, 0);
                    drop.setNoPickUpDelay();
                }
            }
        }
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHunterCloakXpDrop(final LivingExperienceDropEvent event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( event.getAttackingPlayer() instanceof Player) {
            Player player = event.getAttackingPlayer();
            if ( CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.HUNTER_CLOAK.get()).isPresent()
                    || CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.MAX_CLOAK.get()).isPresent() ) {
                Level world = player.level();
                int experience = event.getDroppedExperience();
                ExperienceOrb experienceOrbEntity = new ExperienceOrb(world, player.getBoundingBox().getCenter().x, player.getBoundingBox().getCenter().y, player.getBoundingBox().getCenter().z, experience);
                experienceOrbEntity.setDeltaMovement(0, 0, 0);
                world.addFreshEntity(experienceOrbEntity);
                event.setCanceled(true);
            }
        }
    }
}
