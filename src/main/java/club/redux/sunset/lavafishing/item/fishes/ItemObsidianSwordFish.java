package club.redux.sunset.lavafishing.item.fishes;

import club.redux.sunset.lavafishing.misc.ModTiers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemObsidianSwordFish extends SwordItem {

    public ItemObsidianSwordFish() {
        super(
                ModTiers.OBSIDIAN,
                2,
                2,
//                new Properties() {
//                    @Override
//                    public Item.Properties durability(int pMaxDamage) {
//                        super.durability(pMaxDamage);
//                        stacksTo(64);
//                        return this;
//                    }
//                }
                new Properties()
                        .stacksTo(64)
                        .food(new FoodProperties.Builder()
                                .nutrition(3)
                                .saturationMod(0.6F)
                                .build())
                        .fireResistant()
                        .setNoRepair()
        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300));
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
