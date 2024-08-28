package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.api.mixin.IMixinProxyAbstractFish;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Debug(export = true)
@Mixin(value = AbstractFish.class)
public abstract class MixinAbstractFish extends WaterAnimal implements Bucketable, IMixinProxyAbstractFish {
    protected MixinAbstractFish(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    @Override
    @SuppressWarnings("AddedMixinMembersNamePattern")
    public void aiStepFromMob() {
        super.aiStep();
    }

    @Unique
    @Override
    @SuppressWarnings("AddedMixinMembersNamePattern")
    public void travelFromLivingEntity(@NotNull Vec3 vec3) {
        super.travel(vec3);
    }
}
