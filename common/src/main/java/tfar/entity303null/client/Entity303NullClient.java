package tfar.entity303null.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import tfar.entity303null.Entity303Null;
import tfar.entity303null.init.ModEntities;

public class Entity303NullClient {

    public static void registerRenderers() {
        EntityRenderers.register(ModEntities.ENTITY_303, (EntityRendererProvider.Context context) -> new SimplePlayerRenderer<>(context,
                false,new ResourceLocation(Entity303Null.MOD_ID, "textures/entity/entity_303.png")));

        EntityRenderers.register(ModEntities.NULL, (EntityRendererProvider.Context context) -> new SimplePlayerRenderer<>(context,
                false,new ResourceLocation(Entity303Null.MOD_ID, "textures/entity/null.png")));
    }

}
