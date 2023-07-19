package com.sunset.util.RegistryCollections;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.ArrayList;
import java.util.List;

public class ParticleTypeCollection
{
    public static final List<ParticleType<?>> RegistryParticles = new ArrayList<>();
    public static final SimpleParticleType PARTICLE_FIRE_PUNCH = (SimpleParticleType) register(new SimpleParticleType(true), "fire_punch");

    public static ParticleType register(ParticleType<?> particleType, String registryName) {
        particleType.setRegistryName(registryName);
        RegistryParticles.add(particleType);
        return particleType;
    }

}
