package club.redux.sunset.lavafishing.mixin;

import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements Attackable, ILivingEntityExtension {

    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

//    @Shadow
//    protected abstract boolean isAffectedByFluids();
//
//    @Shadow
//    public abstract boolean canStandOnFluid(FluidState pFluidState);
//
//    @Shadow
//    public abstract ItemStack getItemBySlot(EquipmentSlot equipmentSlot);
//
//    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"))
//    public void travel(Vec3 pTravelVector, CallbackInfo ci) {
//        if (this.isControlledByLocalInstance()) {
//            if (this.isInLava() && this.isAffectedByFluids() && !this.canStandOnFluid(this.level().getFluidState(this.blockPosition()))) {
//                if (this.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PROMETHIUM_LEGGINGS.get())) {
//                    this.moveRelative(0.08F, pTravelVector);
//                }
//            }
//        }
//    }
}