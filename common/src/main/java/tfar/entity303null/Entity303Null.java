package tfar.entity303null;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.entity303null.entity.Entity_303;
import tfar.entity303null.init.ModEntities;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class Entity303Null {

    public static final String MOD_ID = "entity303null";
    public static final String MOD_NAME = "Entity303Null";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {

    }

    public static int ENTITY_303_LIFESPAN = 1200;
    public static int SPAWN_ATTEMPT_FREQUENCY = 1;

    public static int SPAWN_COOLDOWN = 1200;
    static int cooldown;
    public static int MIN_SPAWN_DISTANCE = 64;
    public static int MAX_SPAWN_DISTANCE = 96;
    public static void playerTick(ServerPlayer player) {
        ServerLevel serverLevel = player.getLevel();
        if (cooldown > 0) {
            cooldown--;
        }
        if (cooldown <= 0 && serverLevel.getGameTime() % SPAWN_ATTEMPT_FREQUENCY == 0 && !serverLevel.isDay()) {
            RandomSource randomSource = serverLevel.random;
            double angle = 360 * randomSource.nextDouble();
            double distance = MIN_SPAWN_DISTANCE + (MAX_SPAWN_DISTANCE - MIN_SPAWN_DISTANCE) * randomSource.nextDouble();
            Vec3 playerPos = player.position();
            Vec2 attempt = addPolar(playerPos,distance,angle);
            int height = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) attempt.x, (int) attempt.y);
            BlockPos spawnPos = new BlockPos(attempt.x,height,attempt.y);
            Entity_303 entity303 = (Entity_303) ModEntities.ENTITY_303.spawn(serverLevel,null,null,spawnPos, MobSpawnType.EVENT,false,false);
            cooldown = SPAWN_COOLDOWN;
        }
    }

    //x = r cos θ , y = r sin θ
    public static Vec2 addPolar(Vec3 vec3, double radius, double angle) {
        double x = radius * Math.cos(angle * Math.PI/180);
        double z = radius * Math.sin(angle * Math.PI/180);

        return new Vec2((float) (x + vec3.x), (float) (z + vec3.z));
    }

}

/*
*
* Entity 1: Entity303

Description: Entity303 will stalk the player from a distance, similar to the "End Of Herobrine" mod but won't attack the
* player and have glowing red eyes that emit a light source (like the redstone torch).
*  I'd like a 3 stage aggression step as following: Stage 1: Stalking you from a distance, further away than Herobrine would normally would,
* behind trees and structures etc but disappears very quickly when in frame. Stage 2: Both stalking you from a distance and will occasionally
*  get closer, and rarely leave randomized messages on birch signs with messages saying "I'm always watching", "I know your secrets",
*  "I'm closer than you think" and  "I'm behind every corner" and a single redstone torch will appear next to the sign.
* and Stage 3: All of the above but will spawn in player built structures (inside behind windows, outside behind windows, and in the corner of your eye.
*  Entity 303 cannot spawn past a certain y level. When Entity303 disappears he will play a random cave sound, if the player doesn't look at him,
*  he disappears after 20 seconds.


Entity 2: Null

Description: Null will appear rarely keeping a bit of distance away from the player and always will leave dark oak signs with the word "Null"
*   or "System.out.println("An error occurred during deletion: ") "Null", and "OpenGL Error: Failed to load entity "Null" (randomized) after an encounter.
*  Walking too close to Null will cause it to disappear, looking at Null in the eyes will cause it to disappear and give you a 10 second blindness
* and darkness effect, his eyes will glow white so they are visable at night (similar to how the endermen eyes work). No stages,
*  and Null cannot spawn in caves or below a certain y level and can appear in player structures. When Null disappears he will play a random cave sound,
* if the player doesn't look at him, he disappears after 20 seconds.
*/