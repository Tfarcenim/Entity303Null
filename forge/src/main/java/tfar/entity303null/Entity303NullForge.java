package tfar.entity303null;

import net.minecraftforge.fml.common.Mod;

@Mod(Entity303Null.MOD_ID)
public class Entity303NullForge {
    
    public Entity303NullForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        Entity303Null.LOG.info("Hello Forge world!");
        Entity303Null.init();
        
    }
}