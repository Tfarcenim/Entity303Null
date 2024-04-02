package tfar.entity303null.mixin;

import tfar.entity303null.Entity303Null;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    
    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {
        
        Entity303Null.LOG.info("This line is printed by an example mod common mixin!");
        Entity303Null.LOG.info("MC Version: {}", Minecraft.getInstance().getVersionType());
    }
}