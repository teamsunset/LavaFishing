package com.sunset.lavafishing.util.RegistryCollection;

import com.sunset.lavafishing.block.BlockEntity.BlockEntityPrometheusBounty;
import com.sunset.lavafishing.util.Reference;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeCollection
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reference.MOD_ID);

    public static final RegistryObject<BlockEntityType<BlockEntityPrometheusBounty>> BLOCK_ENTITY_PROMETHEUS_BOUNTY = BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("prometheus_bounty", () -> BlockEntityType.Builder.of(BlockEntityPrometheusBounty::new, BlockCollection.BLOCK_PROMETHEUS_BOUNTY.get()).build(null));

}
