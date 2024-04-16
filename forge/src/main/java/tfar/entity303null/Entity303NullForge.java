package tfar.entity303null;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import tfar.entity303null.client.Entity303NullClientForge;
import tfar.entity303null.entity.Entity_303;
import tfar.entity303null.entity.Null;
import tfar.entity303null.init.ModEntities;

@Mod(Entity303Null.MOD_ID)
public class Entity303NullForge {
    
    public Entity303NullForge() {
        ModLoadingContext.get().registerConfig(Type.SERVER, SERVER_SPEC);
        IEventBus bus  = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::register);
        bus.addListener(this::attributes);
        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(Entity303NullClientForge::registerRenderers);
        }

        MinecraftForge.EVENT_BUS.addListener(this::playerTick);
        Entity303Null.init();
    }

    public static final ModConfigs.Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<ModConfigs.Server, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(ModConfigs.Server::new);
        SERVER_SPEC = specPair2.getRight();
        SERVER = specPair2.getLeft();
    }

    private void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer player && player.level.dimension() == Level.OVERWORLD && event.phase == TickEvent.Phase.START) {
            Entity303Null.playerTick(player);
        }
    }


    private void register(RegisterEvent event) {
        event.register(Registry.ENTITY_TYPE_REGISTRY,new ResourceLocation(Entity303Null.MOD_ID,"entity_303"),() -> ModEntities.ENTITY_303);
        event.register(Registry.ENTITY_TYPE_REGISTRY,new ResourceLocation(Entity303Null.MOD_ID,"null"), () -> ModEntities.NULL);
    }

    private void attributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ENTITY_303, Entity_303.createAttributes().build());
        event.put(ModEntities.NULL, Null.createAttributes().build());
    }

}