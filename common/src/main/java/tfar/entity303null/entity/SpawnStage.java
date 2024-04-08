package tfar.entity303null.entity;

import net.minecraft.world.level.Level;

public enum SpawnStage {
    one(0),two(10),three(20);
    private final long day;

    SpawnStage(long day) {
        this.day = day;
    }

    public static SpawnStage getStage(Level level) {
        long days = level.getGameTime() / 24000;
        SpawnStage start = null;
        for (SpawnStage spawnStage : SpawnStage.values()) {
            if (days < spawnStage.day) {
                break;
            }
            start = spawnStage;
        }
        return start;
    }

}
