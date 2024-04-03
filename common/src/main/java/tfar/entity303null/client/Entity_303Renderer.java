package tfar.entity303null.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import tfar.entity303null.client.layers.Entity_303EyesLayer;
import tfar.entity303null.entity.Entity_303;

import java.util.Objects;

public class Entity_303Renderer extends LivingEntityRenderer<Entity_303, PlayerModel<Entity_303>> {
    public Entity_303Renderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, new PlayerModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR))));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new ArrowLayer<>(context, this));
        //this.addLayer(new Deadmau5EarsLayer(this));
        //this.addLayer(new CapeLayer(this));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        //this.addLayer(new ParrotOnShoulderLayer<>(this, context.getModelSet()));
        this.addLayer(new SpinAttackEffectLayer<>(this, context.getModelSet()));
        this.addLayer(new BeeStingerLayer<>(this));
        addLayer(new Entity_303EyesLayer<>(this));
    }

    @Override
    public void render(Entity_303 $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
        this.setModelProperties($$0);
        super.render($$0, $$1, $$2, $$3, $$4, $$5);
    }

    @Override
    public Vec3 getRenderOffset(Entity_303 $$0, float $$1) {
        return $$0.isCrouching() ? new Vec3(0.0, -0.125, 0.0) : super.getRenderOffset($$0, $$1);
    }

    private void setModelProperties(Entity_303 entity_303) {
        PlayerModel<Entity_303> model1 = this.getModel();
        if (entity_303.isSpectator()) {
            model1.setAllVisible(false);
            model1.head.visible = true;
            model1.hat.visible = true;
        } else {
            model1.setAllVisible(true);
            model1.hat.visible = true;//entity_303.isModelPartShown(PlayerModelPart.HAT);
            model1.jacket.visible = true; //entity_303.isModelPartShown(PlayerModelPart.JACKET);
            model1.leftPants.visible = true; //entity_303.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
            model1.rightPants.visible = true; //entity_303.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
            model1.leftSleeve.visible = true; //entity_303.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
            model1.rightSleeve.visible = true; //entity_303.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
            model1.crouching = entity_303.isCrouching();
            HumanoidModel.ArmPose $$2 = getArmPose(entity_303, InteractionHand.MAIN_HAND);
            HumanoidModel.ArmPose $$3 = getArmPose(entity_303, InteractionHand.OFF_HAND);
            if ($$2.isTwoHanded()) {
                $$3 = entity_303.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            }

            if (entity_303.getMainArm() == HumanoidArm.RIGHT) {
                model1.rightArmPose = $$2;
                model1.leftArmPose = $$3;
            } else {
                model1.rightArmPose = $$3;
                model1.leftArmPose = $$2;
            }
        }

    }

    private static HumanoidModel.ArmPose getArmPose(Entity_303 entity_303, InteractionHand $$1) {
        ItemStack $$2 = entity_303.getItemInHand($$1);
        if ($$2.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        } else {
            if (entity_303.getUsedItemHand() == $$1 && entity_303.getUseItemRemainingTicks() > 0) {
                UseAnim $$3 = $$2.getUseAnimation();
                if ($$3 == UseAnim.BLOCK) {
                    return HumanoidModel.ArmPose.BLOCK;
                }

                if ($$3 == UseAnim.BOW) {
                    return HumanoidModel.ArmPose.BOW_AND_ARROW;
                }

                if ($$3 == UseAnim.SPEAR) {
                    return HumanoidModel.ArmPose.THROW_SPEAR;
                }

                if ($$3 == UseAnim.CROSSBOW && $$1 == entity_303.getUsedItemHand()) {
                    return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                }

                if ($$3 == UseAnim.SPYGLASS) {
                    return HumanoidModel.ArmPose.SPYGLASS;
                }

                if ($$3 == UseAnim.TOOT_HORN) {
                    return HumanoidModel.ArmPose.TOOT_HORN;
                }
            } else if (!entity_303.swinging && $$2.is(Items.CROSSBOW) && CrossbowItem.isCharged($$2)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }

            return HumanoidModel.ArmPose.ITEM;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Entity_303 entity_303) {
        return entity_303.getSkinTextureLocation();
    }

    @Override
    protected void scale(Entity_303 $$0, PoseStack $$1, float $$2) {
        float $$3 = 0.9375F;
        $$1.scale(0.9375F, 0.9375F, 0.9375F);
    }

    public void renderRightHand(PoseStack $$0, MultiBufferSource $$1, int $$2, Entity_303 $$3) {
        this.renderHand($$0, $$1, $$2, $$3, this.model.rightArm, this.model.rightSleeve);
    }

    public void renderLeftHand(PoseStack $$0, MultiBufferSource $$1, int $$2, Entity_303 $$3) {
        this.renderHand($$0, $$1, $$2, $$3, this.model.leftArm, this.model.leftSleeve);
    }

    private void renderHand(PoseStack $$0, MultiBufferSource $$1, int $$2, Entity_303 entity_303, ModelPart $$4, ModelPart $$5) {
        PlayerModel<Entity_303> model1 = this.getModel();
        this.setModelProperties(entity_303);
        model1.attackTime = 0.0F;
        model1.crouching = false;
        model1.swimAmount = 0.0F;
        model1.setupAnim(entity_303, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        $$4.xRot = 0.0F;
        $$4.render($$0, $$1.getBuffer(RenderType.entitySolid(entity_303.getSkinTextureLocation())), $$2, OverlayTexture.NO_OVERLAY);
        $$5.xRot = 0.0F;
        $$5.render($$0, $$1.getBuffer(RenderType.entityTranslucent(entity_303.getSkinTextureLocation())), $$2, OverlayTexture.NO_OVERLAY);
    }

    @Override
    protected void setupRotations(Entity_303 $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
        float $$5 = $$0.getSwimAmount($$4);
        float $$14;
        float $$7;
        if ($$0.isFallFlying()) {
            super.setupRotations($$0, $$1, $$2, $$3, $$4);
            $$14 = (float)$$0.getFallFlyingTicks() + $$4;
            $$7 = Mth.clamp($$14 * $$14 / 100.0F, 0.0F, 1.0F);
            if (!$$0.isAutoSpinAttack()) {
                $$1.mulPose(Vector3f.XP.rotationDegrees($$7 * (-90.0F - $$0.getXRot())));
            }

            Vec3 $$8 = $$0.getViewVector($$4);
            Vec3 $$9 = $$0.getDeltaMovement();
            double $$10 = $$9.horizontalDistanceSqr();
            double $$11 = $$8.horizontalDistanceSqr();
            if ($$10 > 0.0 && $$11 > 0.0) {
                double $$12 = ($$9.x * $$8.x + $$9.z * $$8.z) / Math.sqrt($$10 * $$11);
                double $$13 = $$9.x * $$8.z - $$9.z * $$8.x;
                $$1.mulPose(Vector3f.YP.rotation((float)(Math.signum($$13) * Math.acos($$12))));
            }
        } else if ($$5 > 0.0F) {
            super.setupRotations($$0, $$1, $$2, $$3, $$4);
            $$14 = $$0.isInWater() ? -90.0F - $$0.getXRot() : -90.0F;
            $$7 = Mth.lerp($$5, 0.0F, $$14);
            $$1.mulPose(Vector3f.XP.rotationDegrees($$7));
            if ($$0.isVisuallySwimming()) {
                $$1.translate(0.0, -1.0, 0.30000001192092896);
            }
        } else {
            super.setupRotations($$0, $$1, $$2, $$3, $$4);
        }

    }
}
