package com.sunset.util.RegistryCollections;

import com.sunset.potion.PotionLavaWalker;
import net.minecraft.world.item.alchemy.Potion;

import java.util.ArrayList;
import java.util.List;

public class PotionCollection
{
    public static final List<Potion> RegistryPotions = new ArrayList<>();
    public static final PotionLavaWalker POTION_LAVA_WALKER = register(new PotionLavaWalker(), "lava_walker");

    public static <T extends Potion> T register(T potion, String registryName) {
        potion.setRegistryName(registryName);
        RegistryPotions.add(potion);
        return potion;
    }

}
