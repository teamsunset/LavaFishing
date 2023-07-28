package com.sunset.lavafishing.util.RegistryCollections;

import com.sunset.lavafishing.util.Reference;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleTypeCollection
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MOD_ID);

    public static final RegistryObject<SimpleParticleType> PARTICLE_FIRE_PUNCH = PARTICLE_TYPE_DEFERRED_REGISTER.register("fire_punch", () -> new SimpleParticleType(false));


}
