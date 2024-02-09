package club.redux.sunset.lavafishing.potion;

import club.redux.sunset.lavafishing.util.RegistryCollection.MobEffectCollection;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class PotionLavaWalker extends Potion
{
    public PotionLavaWalker() {
        super(new MobEffectInstance(MobEffectCollection.EFFECT_LAVA_WALKER.get(), 4800));
    }
}
