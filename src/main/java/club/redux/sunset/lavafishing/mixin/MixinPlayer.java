package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.misc.LavaSwim;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 原版 Player 负责玩家实体的通用状态管理与游泳状态更新。
 * 本 Mixin 在玩家满足特定熔岩游泳装备与环境条件时接管 updateSwimming。
 * 效果是允许装备齐全的玩家在熔岩中进入并保持游泳状态，且不影响常规水中游泳。
 */
@Mixin(value = Player.class, remap = false)
public abstract class MixinPlayer extends Entity {

    /**
     * 构造器，签名与父类 Entity 保持兼容。
     */
    public MixinPlayer(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * 原版目标方法返回玩家的能力集（如飞行、无敌等）。
     * 此 Shadow 用于在 Mixin 中判断玩家是否处于飞行状态以决定是否中断游泳。
     */
    @Shadow
    public abstract Abilities getAbilities();

    /**
     * 原版 updateSwimming 负责根据玩家当前所处介质、输入与姿态更新游泳布尔状态。
     * 此 Inject 在方法头部检查玩家是否处于熔岩中、非水中且穿戴熔岩游泳装备：
     * 1. 飞行时直接清除游泳状态；
     * 2. 已处于游泳状态时，只要冲刺即可维持游泳；
     * 3. 未处于游泳状态时，起游要求冲刺、眼睛浸入熔岩且所在方块流体为熔岩。
     * 条件满足后取消原版方法，未满足或处于水中时则继续执行原版逻辑。
     * 效果是使穿戴装备的玩家能够在熔岩中起游与保持游泳，同时完全保留原版水中行为。
     */
    @Inject(method = "updateSwimming", at = @At("HEAD"), cancellable = true)
    private void lavafishing$lavaSwimUpdate(CallbackInfo callbackInfo) {
        // 只有满足“可以在熔岩中像在水里一样游泳”的条件时，才接管移动逻辑；其他情况全部交给 Minecraft 原版处理。
        if (
                !this.isInLava() || this.isInWater() || !LavaSwim.hasLavaSwimArmor(Player.class.cast(this)) || this.isSpectator() || this.isPassenger()
        ) return;
        if (this.getAbilities().flying) {
            this.setSwimming(false);
        } else if (this.isSwimming()) {
            this.setSwimming(this.isSprinting());
        } else {
            this.setSwimming(
                    this.isSprinting() && this.isEyeInFluid(FluidTags.LAVA) && this.level().getFluidState(this.blockPosition()).is(FluidTags.LAVA)
            );
        }
        callbackInfo.cancel();
    }
}
