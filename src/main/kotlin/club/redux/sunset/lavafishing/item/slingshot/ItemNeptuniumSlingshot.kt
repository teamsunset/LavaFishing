package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import com.teammetallurgy.aquaculture.api.AquacultureAPI

class ItemNeptuniumSlingshot : ItemSlingshot(AquacultureAPI.MATS.NEPTUNIUM, Properties()) {
    override fun customBullet(bullet: EntityBullet) = super.customBullet(bullet).apply { waterInertia = 1.0F }
}