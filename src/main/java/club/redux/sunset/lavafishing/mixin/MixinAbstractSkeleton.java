package club.redux.sunset.lavafishing.mixin;

import club.redux.sunset.lavafishing.item.bullet.ItemBullet;
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot;
import club.redux.sunset.lavafishing.registry.ModItems;
import club.redux.sunset.lavafishing.registry.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
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

/**
 * 原版 AbstractSkeleton 负责骷髅类怪物的行为、远程攻击逻辑与默认装备生成。
 * 本 Mixin 在骷髅的远程攻击和装备初始化流程中扩展弹弓支持。
 * 效果是允许手持弹弓的骷髅发射弹弓弹丸，并在自然生成时有小概率配备铁弹弓及副手弹药。
 */
@Mixin(value = AbstractSkeleton.class, remap = false)
public abstract class MixinAbstractSkeleton extends Monster implements RangedAttackMob {
    /**
     * 构造器，签名与父类 Monster 保持兼容。
     */
    protected MixinAbstractSkeleton(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * 原版 performRangedAttack 负责执行骷髅的远程攻击行为，默认逻辑为装填并向目标发射箭矢。
     * 此 Inject 拦截调用 getArrow 处的逻辑：通过 getWeaponHoldingHand 检索骷髅当前持握的武器，
     * 当选中的武器为弹弓（ItemSlingshot）时，创建并配置对应弹丸、设置弹射伤害、播放音效并将弹丸加入世界，
     * 随后取消原版后续发射弓箭的逻辑。若持握的不是弹弓，则不拦截并正常继续原版攻击。
     * 效果是使骷髅能够正确使用所持握的弹弓进行远程攻击。
     */
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
        if (this.level() instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileUsingShoot(projectile, serverLevel, itemstack,
                    d0, d1 + d3 * 0.2F, d2, 1.6F, (float) (14 - serverLevel.getDifficulty().getId() * 4));
            this.playSound(ModSoundEvents.INSTANCE.getSLINGSHOT().get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        ci.cancel();
    }

    /**
     * 原版 populateDefaultEquipmentSlots 负责在生成骷髅时根据难度填充其默认手持武器与护甲。
     * 此 Inject 在原方法末尾执行追加判断：
     * 独立以 1%（0.01）概率将主手替换为铁弹弓；
     * 在主手成功装备铁弹弓的前提下，再以 1%（0.01）条件概率为副手随机装备一种支持的弹弓弹药。
     * 效果是使自然生成的骷髅有 1% 几率手持铁弹弓，且有 0.01%（联合概率）的几率同时配备副手弹药。
     */
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
