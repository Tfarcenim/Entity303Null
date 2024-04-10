package tfar.entity303null.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tfar.entity303null.entity.Entity_303;
import tfar.entity303null.entity.Null;

public class ModEntities {

    public static final EntityType<Entity_303> ENTITY_303 = EntityType.Builder.of(Entity_303::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F).clientTrackingRange(8).build("");

    public static final EntityType<Null> NULL = EntityType.Builder.of(Null::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F).clientTrackingRange(8).build("");

}
