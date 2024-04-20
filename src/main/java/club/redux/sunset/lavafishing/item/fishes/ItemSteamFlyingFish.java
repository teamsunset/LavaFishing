package club.redux.sunset.lavafishing.item.fishes;

import club.redux.sunset.lavafishing.registry.RegistryMobEffect;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemSteamFlyingFish extends Item {
    public ItemSteamFlyingFish() {
        super(new Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(3)
                        .saturationMod(0.6F)
                        .build())
                .fireResistant()
        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        pLivingEntity.addEffect(new MobEffectInstance(RegistryMobEffect.LAVA_WALKER.get(), 300));
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

}
