package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.entity.EntityPromethiumBowArrow
import club.redux.sunset.lavafishing.entity.EntitySpectralPromethiumBowArrow
import club.redux.sunset.lavafishing.registry.RegistryItem
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.BowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

class ItemPromethiumBow : BowItem(Properties().durability(3000)) {
    override fun customArrow(arrowEntity: AbstractArrow): AbstractArrow {
        if (arrowEntity.type === EntityType.ARROW) {
            if (arrowEntity.owner is LivingEntity) {
                return EntityPromethiumBowArrow(true, arrowEntity.level(), arrowEntity.owner as LivingEntity)
            }
        }

        if (arrowEntity.type === EntityType.SPECTRAL_ARROW) {
            if (arrowEntity.owner is LivingEntity) {
                return EntitySpectralPromethiumBowArrow(arrowEntity.level(), arrowEntity.owner as LivingEntity)
            }
        }

        return super.customArrow(arrowEntity)
    }

    override fun canApplyAtEnchantingTable(stack: ItemStack?, enchantment: Enchantment?): Boolean {
        return super.canApplyAtEnchantingTable(stack, enchantment)
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