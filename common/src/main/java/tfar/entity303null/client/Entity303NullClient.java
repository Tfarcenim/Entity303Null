package tfar.entity303null.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import tfar.entity303null.init.ModEntities;

public class Entity303NullClient {

    public static void registerRenderers() {
        EntityRenderers.register(ModEntities.ENTITY_303, (EntityRendererProvider.Context context) -> new Entity_303Renderer(context,false));
    }

}
