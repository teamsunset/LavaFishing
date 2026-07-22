package club.redux.sunset.lavafishing.client.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SingleQuadParticle
import net.minecraft.client.particle.SpriteSet
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.util.RandomSource

class ParticleFirePunch(
    level: ClientLevel,
    xCoord: Double,
    yCoord: Double,
    zCoord: Double,
    spriteSet: SpriteSet,
    random: RandomSource,
    xd: Double,
    yd: Double,
    zd: Double,
) : SingleQuadParticle(level, xCoord, yCoord, zCoord, xd, yd, zd, spriteSet.get(random)) {
    init {
        this.gravity = 0.9f
        this.friction = 0.85f
        this.xd = 0.2
        this.yd = 0.35
        this.zd = 0.2
        this.quadSize *= 4f
        this.lifetime = 20
        this.setSpriteFromAge(spriteSet)

        this.rCol = 1f
        this.gCol = 1f
        this.bCol = 1f
    }

    override fun getLayer(): Layer = Layer.TRANSLUCENT

    class Provider(private val sprites: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(
            pType: SimpleParticleType,
            pLevel: ClientLevel,
            x: Double,
            y: Double,
            z: Double,
            dx: Double,
            dy: Double,
            dz: Double,
            random: RandomSource,
        ): Particle {
            return ParticleFirePunch(pLevel, x, y, z, this.sprites, random, dx, dy, dz)
        }
    }
}
