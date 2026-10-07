package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.misc.LavaSwim;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 原版 LivingEntity 负责生物实体的核心行为与移动计算。
 * 本 Mixin 在熔岩旅行逻辑中调整移动阻尼，并将符合装备条件的实体转发至水中旅行物理。
 * 效果是允许装备特定装备的实体在熔岩中使用水中移动逻辑并优化输入响应。
 */
@Mixin(value = LivingEntity.class, remap = false)
public abstract class MixinLivingEntity extends Entity implements Attackable, ILivingEntityExtension {

    /**
     * 构造器，签名与父类 Entity 保持兼容。
     */
    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * 原版目标方法按指定装备槽位返回对应的物品堆。
     * 此 Shadow 用于在 Mixin 中检查实体腿部装备。
     */
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    /**
     * 原版目标方法返回实体当前是否处于跳跃状态。
     * 此 Shadow 用于在 Mixin 中读取实体的垂直移动输入倾向。
     */
    @Shadow
    public abstract boolean isJumping();


    @Invoker("travelInWater")
    protected abstract void callTravelInWater(Vec3 input, double baseGravity, boolean isFalling, double oldY);

    /**
     * 原版 travelInLava 负责处理实体在熔岩内的整体旅行与移动。
     * 此 Inject 在方法头部检查实体是否穿戴护腿、处于熔岩且不在水中；若成立则调用 travelInWater 并取消后续熔岩移动逻辑。
     * 效果是使符合条件的实体复用水中移动物理，其他情况下继续执行原版熔岩移动。
     */
    @Inject(method = "travelInLava", at = @At("HEAD"), cancellable = true)
    private void lavafishing$lavaSwimTravel(
            Vec3 input,
            double baseGravity,
            boolean isFalling,
            double oldY,
            CallbackInfo callbackInfo
    ) {
        if (LavaSwim.canUseLavaWaterTravel(LivingEntity.class.cast(this))) {
            this.callTravelInWater(input, baseGravity, isFalling, oldY);
            callbackInfo.cancel();
        }
    }

    @Inject(
            method = "getFluidFallingAdjustedMovement",
            at = @At("RETURN"),
            cancellable = true
    )
    private void lavafishing$lavaSwimGravity(
            double baseGravity,
            boolean isFalling,
            Vec3 movement,
            CallbackInfoReturnable<Vec3> callbackInfo
    ) {
        LivingEntity entity = LivingEntity.class.cast(this);

        if (baseGravity != 0.0
                && entity.isSprinting()
                && !entity.isEyeInFluid(FluidTags.LAVA)
                && LavaSwim.canUseLavaWaterTravel(entity)) {
            callbackInfo.setReturnValue(
                    callbackInfo.getReturnValue().add(0.0, -baseGravity / 4.0, 0.0)
            );
        }
    }
}
