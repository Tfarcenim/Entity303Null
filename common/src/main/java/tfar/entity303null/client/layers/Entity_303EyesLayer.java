package tfar.entity303null.client.layers;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import tfar.entity303null.Entity303Null;

public class Entity_303EyesLayer<T extends LivingEntity, M extends PlayerModel<T>> extends EyesLayer<T, M> {

    private static final RenderType ENTITY_303_EYES = RenderType.eyes(new ResourceLocation(Entity303Null.MOD_ID,"textures/entity/entity_303_eyes.png"));
    public Entity_303EyesLayer(RenderLayerParent<T,M> $$0) {
        super($$0);
    }

    @Override
    public RenderType renderType() {
        return ENTITY_303_EYES;
    }
}
