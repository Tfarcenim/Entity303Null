package tfar.entity303null.platform;

import tfar.entity303null.ModConfigs;
import tfar.entity303null.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public int getentity_303SpawnCooldown() {
        return ModConfigs.Server.entity_303_base_spawn_cooldown.get();
    }

    @Override
    public int getnullSpawnCooldown() {
        return ModConfigs.Server.null_base_spawn_cooldown.get();
    }
}