package club.redux.sunset.lavafishing.behavior

import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.core.dispenser.ProjectileDispenseBehavior
import net.minecraft.world.level.block.DispenserBlock
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent

class BehaviorDispenserBullet(item: ItemBullet) : ProjectileDispenseBehavior(item) {
    companion object {
        @JvmStatic
        fun onSetup(event: FMLCommonSetupEvent) {
            ModItems.getEntriesIsInstance<ItemBullet>().forEach {
                DispenserBlock.registerBehavior(it, BehaviorDispenserBullet(it))
            }
        }
    }
}