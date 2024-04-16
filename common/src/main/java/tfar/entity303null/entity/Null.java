package tfar.entity303null.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

public class Null extends PathfinderMob implements CanLookAt {
    public Null(EntityType<? extends PathfinderMob> $$0, Level $$1) {
        super($$0, $$1);
    }

    private BeingLookedAtGoal<Null> beingLookedAtGoal;

    boolean staredAt;

    long age;
    long lifespan = Entity303Null.NULL_LIFESPAN;
    int ticksLookedAt;


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 96).add(Attributes.MOVEMENT_SPEED, 0.23).add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtPlayerGoal<>(this));
        beingLookedAtGoal = new BeingLookedAtGoal<>(this, e -> true);
        this.targetSelector.addGoal(1, beingLookedAtGoal);
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
        if (!level.isClientSide) {
            Player player = this.level.getNearestPlayer(this, -1.0D);
            if (player != null && player.distanceToSqr(this) < Entity303Null.NULL_MIN_PLAYER_DISTANCE * Entity303Null.NULL_MIN_PLAYER_DISTANCE) {
                despawn();
                placeSign();
            } else if (staredAt) {
                ticksLookedAt++;
                if (ticksLookedAt >= 6) {
                    LivingEntity target = beingLookedAtGoal.getTarget();
                    if (target != null) {
                        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200));
                    }
                    despawn();
                    placeSign();
                }
            }
        }
    }

    void despawn() {
        if (!level.isClientSide) {
            discard();
            level.playSound(null,blockPosition(), SoundEvents.AMBIENT_CAVE, SoundSource.HOSTILE,5,1);
        }
    }

    ///note, most messages can only be 10 characters per line
    public static final List<String[]> messages = List.of(
            new String[]{"Null"},
            new String[]{"System.out.println","An error oc","curred during","deletion: Null"},
            new String[]{"OpenGL Error:","Failed to load","entity: \"Null\""}
    );

    void placeSign() {
        BlockState state = Blocks.DARK_OAK_SIGN.defaultBlockState();
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

    //always will leave dark oak signs with the word "Null"
    //*   or "System.out.println("An error occurred during deletion: ") "Null", and "OpenGL Error: Failed to load entity "Null" (randomized) after an encounter

    @Override
    public boolean isLookingAtMe(Player player) {
        Vec3 vec3 = player.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > 1.0D - 0.025D / d0 && player.hasLineOfSight(this);
    }

    @Override
    public void setStaredAt(boolean staredAt) {
        this.staredAt =staredAt;
    }

    @Override
    public boolean isStaredAt() {
        return staredAt;
    }
}
