package club.redux.sunset.lavafishing.item.bullet


import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.util.UtilLevel.getHolder
import club.redux.sunset.lavafishing.util.UtilProjectile.setShooter
import net.minecraft.core.Direction
import net.minecraft.core.Position
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ArrowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

open class ItemBullet(
    properties: Properties,
    val entityTypeProvider: () -> EntityType<out EntityBullet>,
) : ArrowItem(properties) {

    //TODO
    override fun isInfinite(stack: ItemStack, bow: ItemStack, livingEntity: LivingEntity): Boolean {
        return EnchantmentHelper.getTagEnchantmentLevel(
            livingEntity.level().getHolder(Enchantments.INFINITY).orElseThrow(), bow
        ) > 0
    }

    open fun attachBasePropertiesToBullet(bullet: EntityBullet) = Unit

    open fun customBullet(pLevel: Level): EntityBullet = entityTypeProvider().create(pLevel)!!

    override fun asProjectile(pLevel: Level, pPos: Position, pStack: ItemStack, pDirection: Direction): Projectile {
        return this.createBullet(pLevel).apply {
            setPos(Vec3(pPos.x(), pPos.y(), pPos.z()))
            pickup = AbstractArrow.Pickup.ALLOWED
        }
    }

    /**
     * # 第一步
     *
     * 从Item中获取createArrow
     *
     * 仅用于兼容原版调用
     */
    @Deprecated("不建议用", ReplaceWith("this.createBullet(pLevel, pStack, pShooter)"))
    override fun createArrow(
        pLevel: Level,
        pAmmo: ItemStack,
        pShooter: LivingEntity,
        pWeapon: ItemStack?,
    ): AbstractArrow = this.createBullet(pLevel, pAmmo, pShooter, pWeapon)

    fun createBullet(pLevel: Level): EntityBullet = this.createBullet(pLevel, ItemStack.EMPTY, null, null)

    open fun createBullet(pLevel: Level, pAmmo: ItemStack, pShooter: Entity?, pWeapon: ItemStack?): EntityBullet {
        val bullet = this.customBullet(pLevel)
        this.attachBasePropertiesToBullet(bullet)

        if (pWeapon != null && pLevel is ServerLevel) {
            require(!pWeapon.isEmpty) { "Invalid weapon firing an arrow" }

            bullet.firedFromWeapon = pWeapon.copy()
            bullet.pierceLevel = EnchantmentHelper.getPiercingCount(pLevel, pWeapon, pAmmo.copy()).toByte()
            EnchantmentHelper.onProjectileSpawned(pLevel, pWeapon, bullet) { bullet.firedFromWeapon = null }
        }
        return bullet.setShooter(pShooter)
    }
}