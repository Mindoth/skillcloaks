package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class HitpointsCloakItem extends CurioItem {

    public HitpointsCloakItem(String name) {
        super(name);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void onPlayerHeal(final LivingHealEvent event) {
        if ( ModCommonConfig.COSMETIC_ONLY.get() ) return;
        if ( CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), ModItems.HITPOINTS_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), ModItems.MAX_CLOAK.get()).isPresent() ) {
            event.setAmount(event.getAmount() * 2);
        }
    }
}
