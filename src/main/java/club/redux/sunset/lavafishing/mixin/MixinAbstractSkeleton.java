package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.item.bullet.ItemBullet;
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot;
import club.redux.sunset.lavafishing.registry.ModItems;
import club.redux.sunset.lavafishing.registry.ModSoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Stream;

@Mixin(value = AbstractSkeleton.class, remap = false)
public abstract class MixinAbstractSkeleton extends Monster implements RangedAttackMob {
    protected MixinAbstractSkeleton(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "performRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/skeleton/AbstractSkeleton;getArrow(Lnet/minecraft/world/item/ItemStack;FLnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;"), cancellable = true)
    private void lavafishing$performSlingshotRangedAttack(LivingEntity target, float power, CallbackInfo ci) {
        ItemStack weapon = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem));
        ItemStack itemstack = this.getProjectile(weapon);
        if (!(weapon.getItem() instanceof ItemSlingshot slingshot)) return;

        Projectile projectile = slingshot.createProjectile(this.level(), this, weapon, itemstack, true);
        if (projectile instanceof AbstractArrow arrow) {
            arrow.setBaseDamageFromMob(power);
            projectile = slingshot.customArrow(arrow, itemstack, weapon);
        }

        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333) - projectile.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        projectile.shoot(d0, d1 + d3 * 0.2F, d2, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(ModSoundEvents.INSTANCE.getSLINGSHOT().get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(projectile);

        ci.cancel();
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void lavafishing$populateSlingshotEquipment(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (random.nextDouble() < 0.01) {
            this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.INSTANCE.getIRON_SLINGSHOT().get()));
            if (random.nextDouble() < 0.01) {
                List<Item> supportedItems = Stream.concat(ItemSlingshot.Companion.getSUPPORTED_PROJECTILES().keySet().stream(), ModItems.INSTANCE.getEntries().stream().filter(item -> item instanceof ItemBullet)).toList();
                this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(supportedItems.get(random.nextInt(supportedItems.size()))));
            }
        }
    }
}
