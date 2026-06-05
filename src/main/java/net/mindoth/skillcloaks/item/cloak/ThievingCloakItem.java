package net.mindoth.skillcloaks.item.cloak;

import net.mindoth.skillcloaks.Skillcloaks;
import net.mindoth.skillcloaks.config.ModCommonConfig;
import net.mindoth.skillcloaks.item.CurioItem;
import net.mindoth.skillcloaks.registries.ModItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = Skillcloaks.MOD_ID)
public class ThievingCloakItem extends CurioItem {

    public ThievingCloakItem(String name) {
        super(name);
    }

    @SuppressWarnings("ALL")
    @SubscribeEvent
    public static void visibilityModifier(LivingEvent.LivingVisibilityEvent event) {
        if ( CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), ModItems.THIEVING_CLOAK.get()).isPresent()
                || CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), ModItems.MAX_CLOAK.get()).isPresent() ) {
            event.modifyVisibility(ModCommonConfig.THIEVING_MULTIPLIER.get());
        }
    }
}
