package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class SlayerCloakItem extends CurioItem {

    public SlayerCloakItem(String name) {
        super(name);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onSlayerHurt(final LivingDamageEvent.Pre event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( event.getEntity() instanceof Monster ) {
            Level world = event.getEntity().level();
            if ( !world.isClientSide ) {
                if ( event.getSource().getEntity() instanceof LivingEntity ) {
                    LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
                    if ( attacker != null && ( CuriosApi.getCuriosHelper().findFirstCurio(attacker, ModItems.SLAYER_CLOAK.get()).isPresent()
                            || CuriosApi.getCuriosHelper().findFirstCurio(attacker, ModItems.MAX_CLOAK.get()).isPresent() ) ) {
                        LivingEntity target = event.getEntity();
                        if ( (target.getHealth() <= target.getMaxHealth() * ModCommonConfig.SLAYER_THRESHOLD.get())
                                || (event.getOriginalDamage() >= target.getMaxHealth() * (1.0 - ModCommonConfig.SLAYER_THRESHOLD.get()))
                                || (event.getOriginalDamage() >= target.getHealth()) ) {
                            event.setNewDamage(Float.MAX_VALUE);

                            //Sound
                            world.playSound(null, target.getBoundingBox().getCenter().x, target.getBoundingBox().getCenter().y, target.getBoundingBox().getCenter().z,
                                    SoundEvents.BLAZE_HURT, SoundSource.PLAYERS, 1, 1);

                            //Particles
                            ServerLevel level = (ServerLevel)world;
                            for (int i = 0; i < 8; ++i) {
                                level.sendParticles(ParticleTypes.CRIT, target.getBoundingBox().getCenter().x, target.getBoundingBox().getCenter().y, target.getBoundingBox().getCenter().z, 10, 0, 0, 0, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
