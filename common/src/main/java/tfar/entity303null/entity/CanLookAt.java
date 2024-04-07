package tfar.entity303null.entity;

import net.minecraft.world.entity.player.Player;

public interface CanLookAt {
    boolean isLookingAtMe(Player player);
    void setStaredAt(boolean staredAt);
    boolean isStaredAt();
}
