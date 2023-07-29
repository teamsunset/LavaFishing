package com.sunset.lavafishing.util.RegistryCollection;

import com.sunset.lavafishing.entity.EntityObsidianHook;
import com.sunset.lavafishing.util.Reference;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeCollection
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MOD_ID);

    public static final RegistryObject<EntityType<EntityObsidianHook>> ENTITY_OBSIDIAN_HOOK = ENTITY_TYPE_DEFERRED_REGISTER.register("obsidian_hook", EntityObsidianHook::BuildEntityType);

}
