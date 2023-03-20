package net.mindoth.skillcloaks.compat.lucent;

import com.legacy.lucent.api.EntityBrightness;
import com.legacy.lucent.api.plugin.ILucentPlugin;
import com.legacy.lucent.api.plugin.LucentPlugin;
import net.mindoth.skillcloaks.SkillCloaks;
import net.mindoth.skillcloaks.config.SkillCloaksCommonConfig;
import net.mindoth.skillcloaks.registries.SkillCloaksItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

@LucentPlugin
public class CloakLucentPlugin implements ILucentPlugin {

    @Override
    public String ownerModID() {
        return SkillCloaks.MOD_ID;
    }

    @Override
    public void getEntityLightLevel(EntityBrightness entityBrightness) {
        if (SkillCloaksCommonConfig.COSMETIC_ONLY.get()) return;
        Entity entity = entityBrightness.getEntity();

        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (ModList.get().isLoaded("curios")) {
                if (CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.FIREMAKING_CLOAK.get()).isPresent()) {
                    entityBrightness.setLightLevel(15);
                }
                if (CuriosApi.getCuriosHelper().findFirstCurio(player, SkillCloaksItems.MAX_CLOAK.get()).isPresent()) {
                    entityBrightness.setLightLevel(15);
                }
            }
        }
    }
}
