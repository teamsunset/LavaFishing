package club.redux.sunset.lavafishing.mixin.client;

import club.redux.sunset.lavafishing.misc.LavaSwim;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 原版 LocalPlayer 负责客户端本地玩家的输入处理、冲刺/游泳判断及每刻更新。
 * 本 Mixin 在移动姿态判断、冲刺保持与起跑检查中接入熔岩游泳判定，并在特定输入下补充流体下沉行为。
 * 效果是确保本地客户端在熔岩中游泳时拥有正确的姿态、冲刺支持与主动下沉能力。
 */
@Mixin(value = LocalPlayer.class, remap = false)
public abstract class MixinLocalPlayer {

    /**
     * 原版 isMovingSlowly 负责判断玩家当前是否处于低速移动状态，内部调用 isVisuallyCrawling 检查爬行状态。
     * 此 Redirect 在该调用点排除了处于方向熔岩游泳的状态。
     * 效果是避免熔岩游泳被误判定为低速爬行，其他情况下保持原版视觉爬行判定不变。
     */
    @Redirect(method = "isMovingSlowly", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isVisuallyCrawling()Z"))
    private boolean lavafishing$excludeLavaSwimCrawling(LocalPlayer player) {
        return player.isVisuallyCrawling() && !LavaSwim.isDirectionalSwimming(player);
    }

    /**
     * 原版 shouldStopSwimSprinting 负责判断玩家是否应当脱离游泳冲刺状态，原逻辑检查玩家是否仍在水中（isInWater）。
     * 此 Redirect 在原检查基础上增加对方向熔岩游泳状态的判定。
     * 效果是使玩家在熔岩中进行方向游泳时能够维持冲刺状态，同时保留原有的在水中冲刺判定。
     */
    @Redirect(method = "shouldStopSwimSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z"))
    private boolean lavafishing$allowLavaSwimSprinting(LocalPlayer player) {
        return player.isInWater() || LavaSwim.isDirectionalSwimming(player);
    }

    /**
     * 原版 canStartSprinting 负责评估玩家当前是否满足启动冲刺的前提条件，其中涉及水下环境检查（isUnderWater）。
     * 此 Redirect 在水下判断的基础上，放宽允许满足熔岩游泳条件的玩家通过检查。
     * 效果是允许装备齐全的玩家在熔岩环境中成功启动冲刺，原版水下判断保持正常。
     */
    @Redirect(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUnderWater()Z"))
    private boolean lavafishing$allowSubmergedLavaSprint(LocalPlayer player) {
        return player.isUnderWater()
                || (!player.isSpectator() && !player.isPassenger() && LavaSwim.hasLavaSwimArmor(player) && player.isInLava() && player.isEyeInFluid(FluidTags.LAVA));
    }

    /**
     * 原版 aiStep 负责本地玩家每刻更新，包括按键输入解析、状态机流转与移动更新。
     * 此 Inject 注入在 isInWater 检查点，并在玩家处于熔岩中、非水中、穿戴熔岩游泳装备、按住下蹲键且受流体影响时，
     * 主动调用 NeoForge 的 sinkInFluid 触发熔岩下沉逻辑，不取消后续原版方法执行。
     * 效果是补充玩家在熔岩中按住下蹲时的主动下沉操作，原版每刻更新逻辑照常执行。
     */
    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z"))
    private void lavafishing$sinkInLava(CallbackInfo callbackInfo) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (player.isInLava() && !player.isInWater() && LavaSwim.hasLavaSwimArmor(player) && player.input.keyPresses.shift() && player.isAffectedByFluids()) {
            player.sinkInFluid(NeoForgeMod.LAVA_TYPE.value());
        }
    }
}
