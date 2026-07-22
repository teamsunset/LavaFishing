package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import com.teammetallurgy.aquaculture.api.AquacultureAPI

class ItemNeptuniumSlingshot(properties: Properties) : ItemSlingshot(AquacultureAPI.MATS.NEPTUNIUM, properties) {
    override fun customBullet(bullet: EntityBullet) = super.customBullet(bullet).apply { setWaterInertia(1.0F) }
}
