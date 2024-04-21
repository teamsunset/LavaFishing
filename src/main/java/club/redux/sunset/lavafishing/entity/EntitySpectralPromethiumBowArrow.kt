package club.redux.sunset.lavafishing.entity

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.SpectralArrow
import net.minecraft.world.level.Level
import net.minecraftforge.network.PlayMessages.SpawnEntity

class EntitySpectralPromethiumBowArrow : SpectralArrow {
    constructor(spawnPacket: SpawnEntity?, world: Level) : super(world, 0.0, 0.0, 0.0)

    constructor(arrow: EntityType<out SpectralArrow>, world: Level) : super(arrow, world)

    constructor(world: Level, livingEntity: LivingEntity) : super(world, livingEntity)
}