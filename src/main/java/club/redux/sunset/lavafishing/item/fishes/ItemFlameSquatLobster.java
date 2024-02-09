package club.redux.sunset.lavafishing.item.fishes;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemFlameSquatLobster extends Item
{
    public ItemFlameSquatLobster() {
        super(GetProperties());
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, LivingEntity pLivingEntity) {
        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400));
        pLivingEntity.setRemainingFireTicks(100);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public static Properties GetProperties() {
        FoodProperties foodProperties = new FoodProperties.Builder()
                .nutrition(1)
                .saturationMod(0.8F)
                .build();
        return new Properties()
                .food(foodProperties)
                .fireResistant();
    }
}
