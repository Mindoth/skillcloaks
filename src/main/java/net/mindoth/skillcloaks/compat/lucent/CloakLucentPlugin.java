package net.mindoth.skillcloaks.compat.lucent;

import com.legacy.lucent.api.EntityBrightness;
import com.legacy.lucent.api.plugin.ILucentPlugin;
import com.legacy.lucent.api.plugin.LucentPlugin;
import net.mindoth.skillcloaks.config.SkillcloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillcloaksItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

@LucentPlugin
public class CloakLucentPlugin implements ILucentPlugin {

    @Override
    public void getEntityLightLevel(EntityBrightness entityBrightness) {
        if (SkillcloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Entity entity = entityBrightness.getEntity();

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            if (ModList.get().isLoaded("curios") && ModList.get().isLoaded("lucent")) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.FIREMAKING_CLOAK.get(), player).isPresent()) {
                    entityBrightness.setLightLevel(15);
                }
                if (CuriosApi.getCuriosHelper().findEquippedCurio(SkillcloaksItems.MAX_CLOAK.get(), player).isPresent()) {
                    entityBrightness.setLightLevel(15);
                }
            }
        }
    }
}
