package com.sunset.util.RegistryCollections;

import com.sunset.entity.EntityObsidianHook;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class EntityTypeCollection
{
    public static final List<EntityType<?>> RegistryEntities = new ArrayList<>();

    public static final EntityType<EntityObsidianHook> ENTITY_OBSIDIAN_HOOK = register(EntityObsidianHook.BuildEntityType(), "obsidian_hook");

    public static EntityType register(EntityType<?> entityType, String registryName) {
        RegistryEntities.add(entityType);
//      entityType.setRegistryName(MOD_ID, registryName);
        entityType.setRegistryName(registryName);
        return entityType;
    }
}
