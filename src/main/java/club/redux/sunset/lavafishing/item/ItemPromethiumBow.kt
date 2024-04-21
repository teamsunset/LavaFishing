package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.entity.EntityPromethiumBullet
import club.redux.sunset.lavafishing.registry.RegistryItem
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.BowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import java.util.function.Predicate

class ItemPromethiumBow : BowItem(Properties().durability(3000)) {
    override fun customArrow(arrowEntity: AbstractArrow): AbstractArrow {
        if (arrowEntity.owner is LivingEntity) {
            return EntityPromethiumBullet(true, arrowEntity.level(), arrowEntity.owner as LivingEntity)
        }

        return super.customArrow(arrowEntity)
    }

    override fun canApplyAtEnchantingTable(stack: ItemStack?, enchantment: Enchantment?): Boolean {
        return super.canApplyAtEnchantingTable(stack, enchantment)
    }

    override fun getAllSupportedProjectiles(): Predicate<ItemStack> {
        return Predicate { pStack -> pStack.`is`(RegistryItem.PROMETHIUM_BULLET.get()) }
    }

    companion object {
        @JvmStatic
        fun setupClient(event: FMLClientSetupEvent) {
            event.enqueueWork {
                ItemProperties.register(
                    RegistryItem.PROMETHIUM_BOW.get(),
                    ResourceLocation("pull")
                ) { pStack, pLevel, pEntity, pSeed ->
                    if (pEntity == null || pEntity.useItem != pStack) {
                        0f
                    } else {
                        (pStack.useDuration - pEntity.useItemRemainingTicks) / 20f
                    }
                }
                ItemProperties.register(
                    RegistryItem.PROMETHIUM_BOW.get(),
                    ResourceLocation("pulling")
                ) { pStack, pLevel, pEntity, pSeed ->
                    if (pEntity != null && pEntity.isUsingItem && pEntity.useItem === pStack) {
                        1f
                    } else {
                        0f
                    }
                }
            }
        }
    }
}