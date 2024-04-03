package tfar.entity303null;

import tfar.entity303null.platform.Services;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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