package tfar.entity303null.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import tfar.entity303null.entity.CanLookAt;

import java.util.EnumSet;

public class LookAtPlayerGoal<T extends PathfinderMob & CanLookAt> extends Goal {
      private final T mob;
      @Nullable
      private LivingEntity target;
      int ticksLookedAt;

      public LookAtPlayerGoal(T mob) {
         this.mob = mob;
         this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
      }

      /**
       * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
       * method as well.
       */
      @Override
      public boolean canUse() {
         this.target = this.mob.getTarget();
          if (this.target instanceof Player) {
            // double d0 = this.target.distanceToSqr(this.mob);
             return this.mob.isLookingAtMe((Player) this.target);
          } else {
             return false;
          }
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      @Override
      public void start() {
         this.mob.getNavigation().stop();
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      @Override
      public void tick() {
         this.mob.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
         ticksLookedAt++;
      }
   }