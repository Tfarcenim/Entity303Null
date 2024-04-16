package tfar.entity303null;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfigs {

    public static class Server {
        public static ForgeConfigSpec.IntValue entity_303_base_spawn_cooldown;
        public static ForgeConfigSpec.IntValue null_base_spawn_cooldown;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("general");

            entity_303_base_spawn_cooldown = builder.comment("Base cooldown for entity_303 in ticks")
                    .defineInRange("entity_303_base_cooldown",12000,1,Integer.MAX_VALUE);
            null_base_spawn_cooldown = builder.comment("Base cooldown for null in ticks")
                    .defineInRange("null_base_cooldown",18000,1,Integer.MAX_VALUE);
            builder.pop();
        }
    }
}
