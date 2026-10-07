package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.mixinproxy.IMixinProxyAbstractFish;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * 原版 AbstractFish 定义了水生鱼类实体的基础属性与行为。
 * 本 Mixin 实现 IMixinProxyAbstractFish 代理接口，提供直接转发至直接父类的方法。
 * 效果是允许模组实体在自定义逻辑中安全调用父类原版的实体更新与移动逻辑。
 */
@Mixin(value = AbstractFish.class)
public abstract class MixinAbstractFish extends WaterAnimal implements Bucketable, IMixinProxyAbstractFish {
    /**
     * 构造器，签名与父类 WaterAnimal 保持兼容。
     */
    protected MixinAbstractFish(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * 原版目标类中 aiStep 负责实体每刻（tick）的常规更新与状态处理。
     * 此代理方法由接口定义，实现内通过 super.aiStep() 将调用转发给直接父类。
     * 效果是使外部可通过代理接口触发鱼类父类的每刻更新逻辑。
     */
    @Unique
    @Override
    @SuppressWarnings("AddedMixinMembersNamePattern")
    public void aiStepFromMob() {
        super.aiStep();
    }

}
