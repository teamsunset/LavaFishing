package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.registry.ModItems;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements Attackable, IForgeLivingEntity {

    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow(remap = false)
    protected abstract boolean isAffectedByFluids();

    @Shadow(remap = false)
    public abstract boolean canStandOnFluid(FluidState pFluidState);

    @Shadow(remap = false)
    public abstract ItemStack getItemBySlot(EquipmentSlot equipmentSlot);

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"), remap = false)
    public void travel(Vec3 pTravelVector, CallbackInfo ci) {
        if (this.isControlledByLocalInstance()) {
            if (this.isInLava() && this.isAffectedByFluids() && !this.canStandOnFluid(this.level().getFluidState(this.blockPosition()))) {
                if (this.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PROMETHIUM_LEGGINGS.get())) {
                    this.moveRelative(0.06F, pTravelVector);
                }
            }
        }
    }
}