package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet;
import club.redux.sunset.lavafishing.item.bullet.ItemBullet;
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot;
import club.redux.sunset.lavafishing.registry.ModItems;
import club.redux.sunset.lavafishing.registry.ModSoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = AbstractSkeleton.class)
public abstract class MixinAbstractSkeleton extends Monster implements RangedAttackMob {
    protected MixinAbstractSkeleton(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "performRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/AbstractSkeleton;getArrow(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/projectile/AbstractArrow;"), cancellable = true)
    private void performRangedAttackWithSlingshot(LivingEntity target, float distanceFactor, CallbackInfo ci) {
        ItemStack weapon = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem));
        if (!(weapon.getItem() instanceof ItemSlingshot slingshot)) {
            return;
        }

        ItemStack ammo = this.getProjectile(weapon);
        ItemBullet bulletItem;
        if (ammo.isEmpty() || !(ammo.getItem() instanceof ItemBullet)) {
            ammo = new ItemStack(ModItems.STONE_BULLET.get());
            bulletItem = (ItemBullet) ammo.getItem();
        } else {
            bulletItem = (ItemBullet) ammo.getItem();
        }

        EntityBullet projectile = slingshot.customBullet(bulletItem.createBullet(this.level(), ammo, this));

        double dx = target.getX() - this.getX();
        double dy = target.getY(0.3333333333333333D) - projectile.getY();
        double dz = target.getZ() - this.getZ();
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        projectile.shoot(dx, dy + horizontalDistance * 0.2F, dz, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(ModSoundEvents.INSTANCE.getSLINGSHOT().get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(projectile);

        ci.cancel();
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void populateDefaultEquipmentSlotsWithSlingshot(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (random.nextDouble() >= 0.01D) {
            return;
        }

        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.IRON_SLINGSHOT.get()));

        if (random.nextDouble() >= 0.01D) {
            return;
        }

        List<Item> supportedItems = List.of(
            ModItems.STONE_BULLET.get(),
            ModItems.IRON_BULLET.get(),
            ModItems.NEPTUNIUM_BULLET.get(),
            ModItems.PROMETHIUM_BULLET.get()
        );
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(supportedItems.get(random.nextInt(supportedItems.size()))));
    }
}
