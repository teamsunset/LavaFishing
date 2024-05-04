package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.misc.ModTiers

class ItemNeptuniumSlingshot : ItemSlingshot(
    ModTiers.PROMETHIUM,
    Properties()
) {

    override fun customBullet(bullet: EntityBullet): EntityBullet {
        return super.customBullet(bullet).apply {
            waterInertia = 1.0F
        }
    }
}