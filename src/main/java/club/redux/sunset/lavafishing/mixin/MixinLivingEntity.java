package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.registry.ModItems;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, remap = false)
public abstract class MixinLivingEntity extends Entity implements Attackable, ILivingEntityExtension {

    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Inject(method = "travel", at = @At("HEAD"))
    private void lavafishing$applyPromethiumLeggingsBoost(
            Vec3 input,
            CallbackInfo callbackInfo
    ) {
        if (this.isLocalInstanceAuthoritative()
                && this.isInLava()
                && this.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.INSTANCE.getPROMETHIUM_LEGGINGS().get())) {
            this.moveRelative(0.08F, input);
        }
    }
}
