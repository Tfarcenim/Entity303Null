package tfar.entity303null.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tfar.entity303null.Entity303Null;
import tfar.entity303null.entity.goals.LookAtPlayerGoal;
import tfar.entity303null.entity.goals.BeingLookedAtGoal;
import tfar.entity303null.entity.goals.LookforPlayerGoal;

import java.util.List;

public class Entity_303 extends PathfinderMob implements CanLookAt {
    public Entity_303(EntityType<? extends PathfinderMob> $$0, Level level) {
        super($$0, level);
    }


    SpawnStage spawnStage;
    protected boolean staredAt;

    long age;
    long lifespan = Entity303Null.ENTITY_303_LIFESPAN;
    int ticksLookedAt;

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 96).add(Attributes.MOVEMENT_SPEED, 0.23).add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    /**
     * Checks to see if this enderman should be attacking this player
     */
    @Override
    public boolean isLookingAtMe(Player pPlayer) {
        Vec3 vec3 = pPlayer.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(this.getX() - pPlayer.getX(), this.getEyeY() - pPlayer.getEyeY(), this.getZ() - pPlayer.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > 1.0D - 0.025D / d0 && pPlayer.hasLineOfSight(this);
    }


    @Override
    public void setStaredAt(boolean staredAt) {
        this.staredAt = staredAt;
    }

    @Override
    public boolean isStaredAt() {
        return staredAt;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtPlayerGoal<>(this));
        this.targetSelector.addGoal(1, new BeingLookedAtGoal<>(this, e -> true));
        this.targetSelector.addGoal(2, new LookforPlayerGoal<>(this, e -> true));
    }

    @Override
    public void setTarget(@Nullable LivingEntity living) {
        if (living == null) {
            setStaredAt(false);
        }
        super.setTarget(living);
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        if (age >= lifespan) {
            despawn();
        }
        if (staredAt) {
            ticksLookedAt++;
        }
        if (ticksLookedAt >= 10) {
            despawn();
        }
    }

    BlockPos findSafeSignPos(BlockState state) {
        BlockPos pos = blockPosition();
        if (state.canSurvive(level,pos.below())) {
            return pos;
        }

        pos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING,pos);

        if (state.canSurvive(level,pos.below())) {
            return pos;
        }
        return null;
    }


    void despawn() {
        if (!level.isClientSide) {
            switch (spawnStage) {

                case one -> {
                }
                case two -> {
                    if (random.nextInt(10) == 0) {
                        placeSign();
                    }
                }
                case three -> {
                    if (random.nextInt(5) == 0) {
                        placeSign();
                    }
                }
            }
            discard();
        }
    }

    ///note, most messages can only be 10 characters per line
    public static final List<String[]> messages = List.of(
            new String[]{"I'm always",
                    "watching"},
            new String[]{"I know your","secrets"},
            new String[]{"I'm closer","than you","think"},
            new String[]{"I'm behind","every corner"}
    );

    void placeSign() {
        BlockState state = Blocks.BIRCH_SIGN.defaultBlockState();
        BlockPos pos = findSafeSignPos(state);
        if (pos != null && !level.isOutsideBuildHeight(pos)) {
            level.setBlock(pos, state, 3);
            String[] message = messages.get(random.nextInt(messages.size()));
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SignBlockEntity signBlockEntity) {
                for (int i = 0; i < message.length;i++) {
                    signBlockEntity.setMessage(i, Component.literal(message[i]));
                }
                signBlockEntity.setChanged();
            }
            level.setBlock(pos.relative(Direction.NORTH), Blocks.REDSTONE_TORCH.defaultBlockState(), 3);
        }
    }

    public void setSpawnStage(SpawnStage spawnStage) {
        this.spawnStage = spawnStage;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putLong("age", age);
        tag.putInt("stage", spawnStage.ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        age = tag.getLong("age");
        spawnStage = SpawnStage.values()[tag.getInt("stage")];
    }

}
